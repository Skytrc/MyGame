package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.dao.EmailDao;
import com.fung.server.gameserver.content.domain.email.MailBox;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.EmailService;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
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
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
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
            MailBox mailBox = player.getMailBox();
            email.setUuid(Uuid.createUuid());
            email.setSenderId(player.getUuid());
            email.setSenderName(player.getPlayerName());
            email.setCreatedTime(System.currentTimeMillis());
            // 更新草稿箱&数据库
            mailBox.putDraft2DraftBox(email);
            emailDao.createNewEmail(email);
            writeMessage2Client.writeMessage(channelId, " 创建新的邮件草稿 邮件编号为: " + email.getUuid());
        });
        return "";
    }

    @Override
    public String writeContent(String channelId, String emailId, String subject, String content) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            MailBox mailBox = player.getMailBox();
            // 从草稿箱中获取
            Email email = mailBox.getDraftBox().get(emailId);
            if (!senderEmailCheck(player, email, channelId)) {
                return;
            }
            email.setSubject(subject);
            email.setContent(content);
            // 更新草稿箱&数据库
            mailBox.putDraft2DraftBox(email);
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
            Email email = emailDao.getEmailById(emailId);
            if (!senderEmailCheck(playerActor.getPlayer(), email, channelId)) {
                return;
            }
            email.setSendTime(System.currentTimeMillis());
            String recChannelId = onlinePlayer.getChannelIdByPlayerId(email.getRecipientId());
            // 如果玩家在线，发送提示消息
            if (recChannelId != null) {
                writeMessage2Client.writeMessage(recChannelId, "\n你收到一封新的邮件，请及时查收");
            }
        });
        return "";
    }

    @Override
    public String checkMailBox(String channelId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            List<Email> emails = emailDao.getAllRecipientEmailByPlayerId(player.getUuid());
            MailBox mailBox = player.getMailBox();
            writeMessage2Client.writeMessage(channelId, checkMail(emails, mailBox));
        });
        return "邮箱更新完毕";
    }

    @Override
    public String checkAEmail(String channelId, String emailId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            MailBox mailBox = player.getMailBox();
            Email email = mailBox.getEmailFromMailBox(emailId);
            if (email == null) {
                writeMessage2Client.writeMessage(channelId, "\n不存在邮件， 请重新检查或更新邮箱");
                return;
            }
            if (!email.isRecipientHasRead()) {
                // 更新已读状态（mailBox&数据库）
                email.setRecipientHasRead(true);
                mailBox.putMail2MailBox(email);
                emailDao.updateEmail(email);
            }
            writeMessage2Client.writeMessage(channelId, mailInfo(email));
        });
        return null;
    }

    @Override
    public String checkDraftBox(String channelId) {
        return null;
    }

    @Override
    public String gotGoodFromEmail(String channelId, String emailId) {
        return null;
    }

    @Override
    public String deleteResEmail(String channelId, String emailId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            MailBox mailBox = player.getMailBox();
            Email email = mailBox.removeEmailFromMailBox(emailId);
            if (email == null) {
                writeMessage2Client.writeMessage(channelId, "邮箱不存在该邮件，请重新检查或更新邮件");
                return;
            }
            email.setRecipientDelete(true);
            emailDao.updateEmail(email);
        });
        return null;
    }

    public boolean senderEmailCheck(Player player, Email email, String channelId) {
        if (email == null) {
            writeMessage2Client.writeMessage(channelId, "\n没有该草稿邮件");
            return false;
        }
        if (email.isSend()) {
            writeMessage2Client.writeMessage(channelId, "\n邮件已发送");
            return false;
        }
        if (!email.getSenderId().equals(player.getUuid())) {
            writeMessage2Client.writeMessage(channelId, "\n该邮件并不属于你");
            return false;
        }
        if (email.isSenderDelete()) {
            writeMessage2Client.writeMessage(channelId, "\n没有该草稿邮件");
            return false;
        }
        return true;
    }

    public String mailInfo(Email email) {
        return "邮件Id: " + email.getUuid() + "\n邮件主题: " + nullHandle(email.getSubject())
                + "\n邮件收件玩家Id: " + nullHandle(email.getRecipientId())
                + "\n邮件发送人Id: " + nullHandle(email.getSenderId())
                + "\n邮件是否发送: " + (email.isSend() ? "是" : "否")
                + "\n邮件是否面向所有人发送: " + email.isToAllPlayer()
                + "\n邮件是否有物品: " + (email.getGoods() == null ? "不是" : "是");
    }

    public String checkMail(List<Email> emails, MailBox mailBox) {
        StringBuilder stringBuilder = new StringBuilder(" 邮箱有: ").append(emails.size()).append("  封信");
        for (Email email : emails) {
            stringBuilder.append(mailBaseInfo(email)).append("\n");
            mailBox.getMailBox().put(email.getUuid(), email);
        }
        return stringBuilder.toString();
    }

    public String mailContent(Email email) {
        return "\n邮件内容: " + email.getContent();
    }

    public String mailBaseInfo(Email email) {
        return "邮件Id: " + email.getUuid() + "  邮件主题: " + nullHandle(email.getSubject())
                + "  发送人: " + email.getSenderName() + "  发送时间: " + email.getSendTime();
    }

    public String nullHandle(String s) {
        return s == null ? " 空 " : s;
    }
}
