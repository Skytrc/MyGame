package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.dao.GuildDao;
import com.fung.server.gameserver.content.dao.PlayerDao;
import com.fung.server.gameserver.content.domain.guild.GuildActor;
import com.fung.server.gameserver.content.domain.guild.GuildPosition;
import com.fung.server.gameserver.content.domain.guild.GuildManager;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.guild.Guild;
import com.fung.server.gameserver.content.entity.guild.GuildMember;
import com.fung.server.gameserver.content.service.guild.GuildService;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 10:08
 */
@Component
public class GuildServiceImpl implements GuildService {

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private GuildManager guildManager;

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String createGuild(String channelId, String guildName) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            if (player.getPlayerCommConfig().getGuildId() != null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家已有公会无法创建");
                return;
            }
            // 看是否有同名公会
            List<Guild> arrayedGuild = guildManager.getArrayedGuild();
            for (Guild guild : arrayedGuild) {
                if (guild.getGuildName().equals(guildName)) {
                    writeMessage2Client.writeMessage(channelId, String.format("\n %s 已存在", guildName));
                    return;
                }
            }
            // 新建公会以及公会人员（会长）
            Guild newGuild = createNewGuild(guildName);
            guildManager.insertNewGuild(newGuild);
            GuildMember guildMember = createNewGuildMember(player, newGuild.getUuid(), GuildPosition.PRESIDENT);
            newGuild.addGuildMember(guildMember);
            player.getPlayerCommConfig().setGuildId(newGuild.getUuid());

            playerDao.insertOrUpdatePlayerCommConfig(player.getPlayerCommConfig());
            writeMessage2Client.writeMessage(channelId, String.format("\n成功创建公会，当前公会Id为: %s 当前公会名为: %s ,你当前职位为: %s"
                    , newGuild.getUuid(), guildName, guildMember.getGuildPosition().getName()));
        });
        return "";
    }

    @Override
    public String joinGuild(String channelId, String guildId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            if (player.getPlayerCommConfig().getGuildId() != null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家已有公会无法加入");
                return;
            }
            Guild guild = guildManager.getGuildById(guildId);
            if (guild == null) {
                writeMessage2Client.writeMessage(channelId, "\n公会编号错误，请重新输入");
                return;
            }
            List<String> applicantList = guild.getApplicantList();
            if (applicantList.contains(player.getUuid())) {
                writeMessage2Client.writeMessage(channelId, "\n您已申请，请勿多次申请");
                return;
            }
            applicantList.add(player.getUuid());
            guildDao.insertOrUpdateGuild(guild);
            writeMessage2Client.writeMessage(channelId, "\n您的申请已发送，请等待审核");
        });
        return "";
    }

    @Override
    public String checkApplicant(String channelId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            if (guild == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有公会");
                return;
            }
            GuildMember guildMember = guild.getGuildMember(player.getUuid());
            if (guildMember.getGuildPosition().getPosition() > GuildPosition.VICE_PRESIDENT.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有权限");
                return;
            }
            for (String playerId : guild.getApplicantList()) {
                PlayerActor actor = onlinePlayer.getPlayerActorByPlayerId(playerId);
                Player applicationPlayer;
                if (actor == null) {
                    applicationPlayer = playerDao.getPlayerById(playerId);
                } else {
                    applicationPlayer = actor.getPlayer();
                }
                writeMessage2Client.writeMessage(channelId, "\n申请人有: " + playerInfo.showPlayerBase(applicationPlayer));
            }
        });
        return "";
    }

    @Override
    public String allowJoinInGuild(String channelId, String applicantId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            if (guild == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有公会");
                return;
            }
            GuildMember guildMember = guild.getGuildMember(player.getUuid());
            if (guildMember.getGuildPosition().getPosition() > GuildPosition.VICE_PRESIDENT.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有权限");
                return;
            }
            List<String> applicantList = guild.getApplicantList();
            if (!applicantList.contains(applicantId)) {
                writeMessage2Client.writeMessage(channelId, "\n玩家并不在申请列表上");
                return;
            }
            applicantList.remove(applicantId);
            Player applicantPlayer;
            // 获取申请人信息
            if (onlinePlayer.getPlayerActorByPlayerId(applicantId) == null) {
                applicantPlayer = playerDao.getPlayerById(applicantId);

            } else {
                PlayerActor playerActorByPlayerId = onlinePlayer.getPlayerActorByPlayerId(applicantId);
                applicantPlayer = playerActorByPlayerId.getPlayer();
            }
            applicantPlayer.getPlayerCommConfig().setGuildId(guild.getUuid());
            playerDao.insertOrUpdatePlayerCommConfig(applicantPlayer.getPlayerCommConfig());
            guild.addGuildMember(createNewGuildMember(applicantPlayer, guild.getUuid(), GuildPosition.ORDINARY_MEMBER));
            guildDao.insertOrUpdateGuild(guild);
            // 如果申请人在线发送消息通知
            String applicantChannelId = onlinePlayer.getChannelIdByPlayerId(applicantPlayer.getUuid());
            if (applicantChannelId != null) {
                writeMessage2Client.writeMessage(applicantChannelId, "\n您已批准进入公会");
            }
            // 批准人发送消息
            writeMessage2Client.writeMessage(channelId, "\n批准成功");
        });
        return "";
    }

    @Override
    public String denyApplicant(String channelId, String applicantId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            if (guild == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有公会");
                return;
            }
            GuildMember guildMember = guild.getGuildMember(player.getUuid());
            if (guildMember.getGuildPosition().getPosition() > GuildPosition.VICE_PRESIDENT.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有权限");
                return;
            }
            List<String> applicantList = guild.getApplicantList();
            if (!applicantList.contains(applicantId)) {
                writeMessage2Client.writeMessage(channelId, "\n玩家并不在申请列表上");
                return;
            }
            applicantList.remove(applicantId);
            guildDao.insertOrUpdateGuild(guild);
            writeMessage2Client.writeMessage(channelId, "\n拒绝成功");
        });
        return null;
    }

    @Override
    public String findGuildInfo(String channelId, String guildId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Guild guild = guildManager.getGuildById(guildId);
            if (guild == null) {
                writeMessage2Client.writeMessage(channelId, "\n不存在该公会");
                return;
            }
            writeMessage2Client.writeMessage(channelId, guildBaseInfo(guild));
        });
        return "";
    }

    @Override
    public String getTopTenGuildInfo(String channelId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            int i = 10;
            List<Guild> arrayedGuild = guildManager.getArrayedGuild();
            if (arrayedGuild.size() < i) {
                i = arrayedGuild.size();
            }
            for (int i1 = 0; i1 < i; i1++) {
                Guild guild = arrayedGuild.get(i1);
                writeMessage2Client.writeMessage(channelId, "\n排名 " + i1+1 + guildBaseInfo(guild));
            }
        });
        return "";
    }

    @Override
    public String leaveGuild(String channelId, String guildId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);;
            String guildId1 = player.getPlayerCommConfig().getGuildId();
            if (player.getPlayerCommConfig().getGuildId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有加入公会");
                return;
            }
            if (!guildId1.equals(guildId)) {
                writeMessage2Client.writeMessage(channelId, "\n请输入正确的公会编号");
                return;
            }
            // 更新个人信息
            player.getPlayerCommConfig().setGuildId(null);
            playerDao.insertOrUpdatePlayerCommConfig(player.getPlayerCommConfig());

            // 更新公会信息
            Guild guild = guildManager.getGuildById(guildId);
            guild.removeGuildMember(player.getUuid());
            writeMessage2Client.writeMessage(channelId, "\n成功退出公会");
            if (guild.getNumberOfPeople() == 0) {
                // TODO 解散公会

            }
            guildDao.insertOrUpdateGuild(guild);
        });
        return "";
    }

    @Override
    public String disbandGuild(String channelId, String guildId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            if (player.getPlayerCommConfig().getGuildId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有加入公会");
                return;
            }
            if (!player.getPlayerCommConfig().getGuildId().equals(guildId)) {
                writeMessage2Client.writeMessage(channelId, "\n输入公会号错误，请重新输入");
                return;
            }
            Guild guild = guildManager.getGuildById(guildId);
            GuildMember guildMember = guild.getGuildMemberMap().get(player.getUuid());
            if (guildMember.getGuildPosition() != GuildPosition.PRESIDENT) {
                writeMessage2Client.writeMessage(channelId, "\n您不是会长，不能解散公会");
                return;
            }
            Collection<GuildMember> values = guild.getGuildMemberMap().values();
            // TODO 比较麻烦，先不写
        });
        return "";
    }

    @Override
    public String updateMemberPosition(String channelId, String playerId, String guildPosition) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            if (player.getPlayerCommConfig().getGuildId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有加入公会");
                return;
            }
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            if (guild.getGuildMember(playerId) == null) {
                writeMessage2Client.writeMessage(channelId, "\n公会中没有该人");
                return;
            }
            // 判断审核人资格
            GuildMember guildMember = guild.getGuildMember(player.getUuid());
            GuildPosition reviewerGuildPosition = guildMember.getGuildPosition();
            if (reviewerGuildPosition.getPosition() > GuildPosition.VICE_PRESIDENT.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n您没有权限升级他人职位");
                return;
            }
            GuildPosition need2Update = GuildPosition.getGuildPositionByName(guildPosition);
            if (need2Update == null) {
                writeMessage2Client.writeMessage(channelId, "\n没有该职位");
                return;
            }
            if (need2Update.getPosition() >= reviewerGuildPosition.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n需要升职职位不能大于或等于您的职位");
                return;
            }
            // 升降职操作
            GuildMember need2UpdateMember = guild.getGuildMember(playerId);
            GuildPosition oldPosition = need2UpdateMember.getGuildPosition();
            need2UpdateMember.setGuildPosition(need2Update);
            guildDao.insertOrUpdateGuild(guild);
            writeMessage2Client.writeMessage(channelId, String.format("\n编号: %s 成功从: %s 变为 %s", playerId
                    , oldPosition.getName(), need2Update.getName()));
        });
        return "";
    }

    @Override
    public String kickMember(String channelId, String playerId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            String guildId = player.getPlayerCommConfig().getGuildId();
            if (player.getPlayerCommConfig().getGuildId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有加入公会");
                return;
            }
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            if (guild.getGuildMember(playerId) == null) {
                writeMessage2Client.writeMessage(channelId, "\n公会中没有该人");
                return;
            }
            // 判断审核人资格
            GuildMember guildMember = guild.getGuildMember(player.getUuid());
            GuildPosition reviewerGuildPosition = guildMember.getGuildPosition();
            if (reviewerGuildPosition.getPosition() > GuildPosition.VICE_PRESIDENT.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n您没有权限踢人");
                return;
            }
            // 判断被踢人的职位
            GuildMember need2Kick = guild.getGuildMember(playerId);
            if (need2Kick.getGuildPosition().getPosition() <= reviewerGuildPosition.getPosition()) {
                writeMessage2Client.writeMessage(channelId, "\n无法踢比自己职位高或与自己职位相同的人");
                return;
            }

            Player kickPlayer;
            // 获取被踢人的信息
            if (onlinePlayer.getPlayerActorByPlayerId(playerId) == null) {
                kickPlayer = playerDao.getPlayerById(playerId);

            } else {
                PlayerActor playerActorByPlayerId = onlinePlayer.getPlayerActorByPlayerId(playerId);
                kickPlayer = playerActorByPlayerId.getPlayer();
            }
            kickPlayer.getPlayerCommConfig().setGuildId(null);
            playerDao.insertOrUpdatePlayerCommConfig(player.getPlayerCommConfig());

            guildManager.getGuildMap().remove(kickPlayer.getUuid());
            guildDao.insertOrUpdateGuild(guild);
            writeMessage2Client.writeMessage(channelId, String.format("\n成功把 %s 编号: %s 踢出公会", kickPlayer.getName() , kickPlayer.getUuid()));
        });
        return "";
    }

    @Override
    public String transferPresident(String channelId, String playerId) {
        GuildActor guildActor = guildManager.getGuildActor();
        guildActor.addMessage(guildActor1 -> {
            Player player = onlinePlayer.getPlayerByChannelId(channelId);
            if (player.getPlayerCommConfig().getGuildId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家没有加入公会");
                return;
            }
            Guild guild = guildManager.getGuildById(player.getPlayerCommConfig().getGuildId());
            GuildMember transferMember = guild.getGuildMember(player.getUuid());
            if (transferMember.getGuildPosition() != GuildPosition.PRESIDENT) {
                writeMessage2Client.writeMessage(channelId, "\n您不是会长，无法转移");
                return;
            }
            if (guild.getGuildMember(playerId) == null) {
                writeMessage2Client.writeMessage(channelId, "\n公会中没有该玩家");
                return;
            }
            GuildMember need2Transfer = guild.getGuildMember(playerId);
            need2Transfer.setGuildPosition(GuildPosition.PRESIDENT);
            transferMember.setGuildPosition(GuildPosition.ORDINARY_MEMBER);
            guildDao.insertOrUpdateGuild(guild);
            writeMessage2Client.writeMessage(channelId, "\n成功转移会长，您现在职位为 普通成员");
        });
        return "";
    }

    public Guild createNewGuild(String guildName) {
        Guild guild = new Guild();
        guild.setGuildName(guildName);
        guild.setGuildContribution(0);
        guild.setGuildDescription("Empty");
        guild.setGuildMoney(0);
        guild.setGuildRank(0);
        guild.setNumberOfPeople(1);
        guild.setUuid(Uuid.createUuid());
        return guild;
    }

    public GuildMember createNewGuildMember(Player player, String guildId, GuildPosition guildPosition) {
        GuildMember guildMember = new GuildMember();
        guildMember.setGuildId(guildId);
        guildMember.setJoinInTime(System.currentTimeMillis());
        guildMember.setPlayerId(player.getUuid());
        guildMember.setGuildPosition(guildPosition);
        guildMember.setCurrentContribution(0);
        guildMember.setCurrentContribution(0);
        return guildMember;
    }

    /**
     * 返回公会基本信息
     */
    public String guildBaseInfo(Guild guild) {
        return String.format("\n公会名称: %s, 公会编号: %s, 公会等级 %s , 公会人数: %s", guild.getGuildName(), guild.getUuid()
                , guild.getGuildRank(), guild.getNumberOfPeople());
    }
}
