package com.fung.server.gameserver.content.domain.email;

import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/7/16 15:05
 */
public class MailBox {

    /**
     * 邮箱
     */
    private Map<String, Email> mailBox;

    /**
     * 草稿箱
     */
    private Map<String, Email> draftBox;

    /**
     * 收取邮件
     */
    public void getEmail(Email email) {
        mailBox.put(email.getUuid(), email);
    }

    /**
     * 检查邮件接收人
     */
    public boolean checkEmailRecipientId(Email email) {
        return email.getRecipientId() == null;
    }

    /**
     * 检查邮件内容
     */
    public boolean checkEmailContent(Email email) {
        return email.getContent() == null;
    }

    /**
     * 检查邮件是否有物品
     */
    public boolean checkEmailGoods(Email email) {
        return email.getGoods().size() != 0;
    }


    /**
     * 创建新邮件
     * @return email
     */
    public String createNewEmail(Player player) {
        Email email = new Email();
        email.setUuid(Uuid.createUuid());
        email.setSenderId(player.getUuid());
        email.setSenderName(player.getPlayerName());
        return email.getUuid();
    }

    /**
     * 写邮件
     */
    public boolean writeEmailContent(String emailId, String content) {
        Email email = draftBox.get(emailId);
        if (email == null) {
            return false;
        }
        email.setContent(content);
        return true;
    }

    /**
     * 给邮件添加物品
     */
    public boolean addGood(String emailId, Good good) {
        Email email = draftBox.get(emailId);
        if (email == null) {
             return false;
        }
        List<Good> goods;
        if (email.getGoods() == null) {
            goods = new ArrayList<>();
        } else {
            goods = email.getGoods();
        }
        goods.add(good);
        return true;
    }

    /**
     * 获取草稿邮箱信息
     */
    public Email gotDratEmail(String emailId) {
        return draftBox.get(emailId);
    }

    /**
     * 获取邮件信息
     */
    public Email getEmailInfo(String emailId) {
        return mailBox.get(emailId);
    }

    /**
     * 检查邮件是否有物品
     */
    public boolean checkEmailGood(String emailId) {
        return mailBox.get(emailId).getGoods() != null;
    }

    /**
     * 获取邮件物品(List)
     */
    public List<Good> gotGoodWithEmail(String emailId) {
        return mailBox.get(emailId).getGoods();
    }


    public Map<String, Email> getDraftBox() {
        return draftBox;
    }

    public void setDraftBox(Map<String, Email> draftBox) {
        this.draftBox = draftBox;
    }

    public void setMailBox(Map<String, Email> mailBox) {
        this.mailBox = mailBox;
    }
}
