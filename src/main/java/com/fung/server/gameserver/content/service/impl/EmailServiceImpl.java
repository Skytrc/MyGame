package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.dao.EmailDao;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.email.MailBox;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.EmailService;
import com.fung.server.gameserver.content.util.Uuid;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO 玩家登录提示新邮件
 * @author skytrc@163.com
 * @date 2020/7/16 14:47
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    private Gson gson = new Gson();

    @Override
    public String systemWriteEmail(String recipientId, String content, String title) {
        Email email = systemCreateEmail(recipientId, content, title);
        String recChannelId = onlinePlayer.getChannelIdByPlayerId(email.getRecipientId());
        emailDao.updateEmail(email);
        // 如果玩家在线，发送提示消息
        if (recChannelId != null) {
            writeMessage2Client.writeMessage(recChannelId, "\n你收到一封新的邮件，请及时查收");
        }
        return "";
    }

    @Override
    public String systemWriteEmailWithGood(String recipientId, String content, String title, Good good) {
        Email email = systemCreateEmail(recipientId, content, title);
        email.setGoodsId(good.getUuid());

        good.setPlayerId(recipientId);
        good.setPosition(PersonalBackpack.IN_EMAIL);
        goodDao.insertOrUpdateGood(good);

        String recChannelId = onlinePlayer.getChannelIdByPlayerId(email.getRecipientId());
        // 如果玩家在线，发送提示消息
        if (recChannelId != null) {
            writeMessage2Client.writeMessage(recChannelId, "\n你收到一封新的邮件，请及时查收");
        }
        return "";
    }

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
            email.setRecipientId(recipientId);
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
    public String addGood(String channelId, String emailId, int backpackPosition, int goodQuantity) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();

            Email email = emailDao.getEmailById(emailId);
            if (!senderEmailCheck(player, email, channelId)) {
                return;
            }
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            if (!personalBackpack.positionHasGood(backpackPosition)) {
                writeMessage2Client.writeMessage(channelId, "\n该位置没有物品");
                return;
            }
            Good good = personalBackpack.useGood(backpackPosition, goodQuantity);
            if (good == null) {
                writeMessage2Client.writeMessage(channelId, "\n没有足够数量的物品");
                return;
            }
            // 从背包中操作，先更新数据库
            goodDao.insertOrUpdateGood(good);

            // 如果背包剩余物品等于零，直接转移所属权。否则new Good
            if (good.getQuantity() != 0) {
                good = gson.fromJson(gson.toJson(good), Good.class);
                good.setUuid(Uuid.createUuid());
            }
            good.setQuantity(goodQuantity);

            good.setPlayerId(email.getRecipientId());
            good.setPosition(PersonalBackpack.IN_EMAIL);

            goodDao.insertOrUpdateGood(good);
            // 设置物品id
            if (email.getGoodsId() == null) {
                email.setGoodsId(good.getUuid());
            } else {
                email.setGoodsId(email.getGoodsId() + "," + good.getUuid());
            }
            emailDao.updateEmail(email);
            writeMessage2Client.writeMessage(channelId, String.format("\n成功添加物品 名称: %s 数量: %s", good.getName(), good.getQuantity()));
        });
        return "";
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
            emailDao.updateEmail(email);
            writeMessage2Client.writeMessage(channelId, "\n邮件发送成功");
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
            String res = mailInfo(email);
            writeMessage2Client.writeMessage(channelId, res);
        });
        return "";
    }

    @Override
    public String checkDraftBox(String channelId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            List<Email> emails = emailDao.getAllSendEmailByPlayerId(player.getUuid());
            MailBox mailBox = player.getMailBox();
            writeMessage2Client.writeMessage(channelId, checkSenderBox(emails, mailBox));
        });
        return "\n发送邮箱更新完毕";
    }

    @Override
    public String gotGoodFromEmail(String channelId, String emailId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            MailBox mailBox = player.getMailBox();
            Email email = mailBox.getEmailFromMailBox(emailId);
            if (email == null) {
                writeMessage2Client.writeMessage(channelId, "\n不存在邮件， 请重新检查或更新邮箱");
                return;
            }
            if (email.getGoodsId() == null) {
                writeMessage2Client.writeMessage(channelId, "\n邮件没有附件， 请重新检查");
                return;
            }
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            List<String> goodsId = Arrays.asList(email.getGoodsId().split(","));
            Iterator<String> iterator = goodsId.iterator();
            while (iterator.hasNext()) {
                Good good = goodDao.findGoodByGoodUuid(iterator.next());
                String res = personalBackpack.checkAndAddGood(good, goodDao);
                if (res.equals(PersonalBackpack.BACKPACK_FULL)) {
                    writeMessage2Client.writeMessage(channelId, "\n" + PersonalBackpack.BACKPACK_FULL
                            + " 物品" + good.getName() + " 无法加入背包");
                    return;
                }
                if (res.equals(PersonalBackpack.REACH_MAX_STACKS)) {
                    writeMessage2Client.writeMessage(channelId, "\n" + PersonalBackpack.REACH_MAX_STACKS
                            + " 物品" + good.getName() + " 数量: "+ good.getQuantity() + " 达到最大堆叠数，无法加入背包");
                    return;
                }
                writeMessage2Client. writeMessage(channelId, "\n物品: " + goodManager.getGoodNameById(good.getGoodId())
                        + " 数量:" + good.getQuantity() + res);

                // 移除出邮件物品列表
                goodsId.remove(iterator.next());
            }

            // 拼接good id
            if (goodsId.size() != 0) {
                email.setGoodsId(String.join(",", goodsId));
                writeMessage2Client.writeMessage(channelId, "\n邮件还有物品没有接收");
            } else {
                email.setGoodsId(null);
            }
            emailDao.updateEmail(email);
        });
        return "";
    }

    @Override
    public String deleteResEmail(String channelId, String emailId) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            MailBox mailBox = player.getMailBox();
            Email email = mailBox.removeEmailFromMailBox(emailId);
            if (email == null) {
                writeMessage2Client.writeMessage(channelId, "\n邮箱不存在该邮件，请重新检查或更新邮件");
                return;
            }
            if (email.getGoodsId() != null) {
                writeMessage2Client.writeMessage(channelId, "\n邮件物品未取出");
                return;
            }
            email.setRecipientDelete(true);
            emailDao.updateEmail(email);
        });
        return null;
    }


    public Email systemCreateEmail(String recipientId, String content, String title) {
        Email email = new Email();
        email.setUuid(Uuid.createUuid());
        email.setSenderName("系统");
        email.setSenderId("0");
        email.setCreatedTime(System.currentTimeMillis());
        email.setRecipientId(recipientId);
        email.setContent(content);
        email.setSubject(title);
        return email;
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
        String res = "邮件Id: " + email.getUuid() + "\n邮件主题: " + nullHandle(email.getSubject())
                + "\n邮件收件玩家Id: " + nullHandle(email.getRecipientId())
                + "\n邮件发送人Id: " + nullHandle(email.getSenderId())
                + "\n邮件是否发送: " + (email.isSend() ? "是" : "否")
                + "\n邮件是否面向所有人发送: " + email.isToAllPlayer()
                + "\n邮件是否有物品: " + (email.getGoodsId() == null ? "不是" : "是");
        return res;
    }

    public String checkMail(List<Email> emails, MailBox mailBox) {
        StringBuilder stringBuilder = new StringBuilder("\n邮箱有: ").append(emails.size()).append("  封信");
        for (Email email : emails) {
            stringBuilder.append(mailBaseInfo(email)).append("\n");
            mailBox.getMailBox().put(email.getUuid(), email);
        }
        return stringBuilder.toString();
    }

    public String checkSenderBox(List<Email> emails, MailBox mailBox) {
        StringBuilder stringBuilder = new StringBuilder("\n发送邮箱有: ").append(emails.size()).append("  封信");
        for (Email email : emails) {
            stringBuilder.append(sendBoxInfo(email)).append("\n");
            mailBox.getDraftBox().put(email.getUuid(), email);
        }
        return stringBuilder.toString();
    }

    public String mailContent(Email email) {
        return "\n邮件内容: " + email.getContent();
    }

    public String mailBaseInfo(Email email) {
        return "\n邮件Id: " + email.getUuid() + "  邮件主题: " + nullHandle(email.getSubject())
                + "  发送人: " + email.getSenderName() + "  发送时间: " + email.getSendTime();
    }

    public String sendBoxInfo(Email email) {
        return String.format("\n邮件Id: %s  邮件主题: %s  邮件接收人: %s 是否已发送: %s", email.getUuid(), email.getSubject()
                , email.getRecipientId(), email.isSend());
    }

    public String nullHandle(String s) {
        return s == null ? " 空 " : s;
    }
}
