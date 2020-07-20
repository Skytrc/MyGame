package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.dao.EmailDao;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.EmailStatus;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.EmailService;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO 使用缓存?
 * @author skytrc@163.com
 * @date 2020/7/16 14:47
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String createEmail(String channelId, String recipientId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        Player player = playerActor.getPlayer();
        playerActor.addMessage(playerActor1 -> {
            String sendAll = "sendAll";
            if (sendAll.equals(recipientId) && !player.isGm()) {
                writeMessage2Client.writeMessage(channelId, "\n只有游戏管理人员才有资格发送给所有人的邮件");
            } else if (!playerInfo.hasPlayerId(player.getUuid())) {
                writeMessage2Client.writeMessage(channelId, "\n接收人不存在 ");
            }
            Email email = new Email();
            if (sendAll.equals(recipientId) && player.isGm()) {
                email.setToAllPlayer(true);
            }
            email.setUuid(Uuid.createUuid());
            email.setSenderId(player.getUuid());
            email.setSenderName(player.getPlayerName());
            email.setSend(false);
            email.setCreatedTime(System.currentTimeMillis());
            emailDao.createNewEmail(email);
            writeMessage2Client.writeMessage(channelId, " 创建新的邮件草稿 邮件编号为: " + email.getUuid());
        });
        return "";
    }

    @Override
    public String writeContent(String channelId, String emailId, String subject, String content) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Email email = emailDao.getEmailById(emailId);
            if (email == null) {
                writeMessage2Client.writeMessage(channelId, "\n没有该草稿邮件");
            }
            email.setSubject(subject);
            email.setContent(content);
            emailDao.updateEmail(email);
            writeMessage2Client.writeMessage(channelId, "\n添加主题和内容成功");
        });
        return "";
    }

    @Override
    public String addGood(String channelId, String emailId, int backpackPosition) {
        return null;
    }

    @Override
    public String send(String channelId, String emailId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            EmailStatus emailStatus = new EmailStatus();
            Email email = emailDao.getEmailById(emailId);
            email.setSend(true);
            String recChannelId = onlinePlayer.getChannelIdByPlayerId(email.getRecipientId());
            // 如果玩家在线，发送提示消息
            if (recChannelId != null) {
                writeMessage2Client.writeMessage(recChannelId, "\n你收到一封新的邮件，请及时查收");
            }
        });

        return "";
    }

    @Override
    public String checkEmail(String channelId) {
        return null;
    }

    @Override
    public String gotGoodFromEmail(String channelId, String emailId) {
        return null;
    }

    @Override
    public String deleteEmail(String channelId, String emailId) {

        return null;
    }

    public String mailBaseInfo(Email email) {
        return "邮件Id: " + email.getUuid() + "\n邮件主题: " + nullHandle(email.getSubject())
                + "\n邮件收件玩家Id: " + nullHandle(email.getRecipientId())
                + "\n邮件发送人Id: " + nullHandle(email.getSenderId())
                + "\n邮件是否发送: " + email.isSend()
                + "\n邮件是否面向所有人发送: " + email.isToAllPlayer()
                + "\n邮件是否有物品: " + (email.getGoods() == null ? "不是" : "是");
    }

    public String mailContent(Email email) {
        return "邮件Id: " + email.getUuid() + "\n邮件主题: " + nullHandle(email.getSubject())
                + "\n邮件内容: " + email.getContent();
    }

    public String nullHandle(String s) {
        return s == null ? " 空 " : s;
    }
}
