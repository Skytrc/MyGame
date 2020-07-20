package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * 记录邮件状态，是否已读
 * @author skytrc@163.com
 * @date 2020/7/19 14:54
 */
@Entity
@Table(name = "email_read")
public class EmailStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String emailId;

    private String recipientId;

    /**
     * 物品是否收取
     */
    private boolean goodHasRecipient;

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

    public boolean isGoodHasRecipient() {
        return goodHasRecipient;
    }

    public void setGoodHasRecipient(boolean goodHasRecipient) {
        this.goodHasRecipient = goodHasRecipient;
    }
}
