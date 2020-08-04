package com.fung.server.gameserver.content.service.guild;

/**
 * @author skytrc@163.com
 * @date 2020/7/31 17:24
 */
public interface GuildService {

    /**
     * 创建公会
     * @param channelId channel id
     * @param guildName 公会名字
     * @return 消息
     */
    String createGuild(String channelId, String guildName);

    /**
     * 加入公会
     * @param channelId channelId
     * @param guildId 公会编号
     * @return
     */
    String joinGuild(String channelId, String guildId);

    /**
     * 检查申请人
     * @param channelId channel id
     * @return
     */
    String checkApplicant(String channelId);

    /**
     * 允许加入
     * @param channelId channel id
     * @param applicantId 申请人id
     * @return
     */
    String allowJoinInGuild(String channelId, String applicantId);

    /**
     * 拒绝加入
     * @param channelId channel id
     * @param applicantId 申请人id
     * @return
     */
    String denyApplicant(String channelId, String applicantId);

    /**
     * 找寻公会信息
     * @param channelId channel id
     * @param guildId 公会id
     * @return
     */
    String findGuildInfo(String channelId, String guildId);

    /**
     * 获取前十公会信息(不够则有多少获取多少)
     * @param channelId channel id
     * @return
     */
    String getTopTenGuildInfo(String channelId);

    /**
     * 离开公会
     * @param channelId channel id
     * @param guildId 公会id
     * @return
     */
    String leaveGuild(String channelId, String guildId);

    /**
     * 解散公会
     * @param channelId channel id
     * @param guildId 公会id
     * @return
     */
    String disbandGuild(String channelId, String guildId);

    /**
     * 升降职
     * @param channelId channel id
     * @param playerId 玩家Id
     * @param guildPosition 需要变动的位置
     * @return
     */
    String updateMemberPosition(String channelId, String playerId, String guildPosition);

    /**
     * 踢人
     * @param channelId channel id
     * @param playerId 玩家Id
     * @return
     */
    String kickMember(String channelId, String playerId);

    /**
     * 转移主席
     * @param channelId channel Id
     * @param playerId 玩家Id
     * @return
     */
    String transferPresident(String channelId, String playerId);

}
