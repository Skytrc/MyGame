package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * 记录邮件状态，是否已读
 * @author skytrc@163.com
 * @date 2020/7/19 14:54
 */
@Entity
@Table(name = "email_status")
public class EmailStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "sender_id")
    private String senderId;

    /**
     * 物品是否收取
     */
    @Column(name = "good_is_recipient")
    private boolean goodIsRecipient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public boolean isGoodIsRecipient() {
        return goodIsRecipient;
    }

    public void setGoodIsRecipient(boolean goodIsRecipient) {
        this.goodIsRecipient = goodIsRecipient;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
