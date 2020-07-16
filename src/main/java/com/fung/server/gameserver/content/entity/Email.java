package com.fung.server.gameserver.content.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/16 12:36
 */
@Entity
@Table(name = "email")
public class Email {

    /**
     * 信件唯一id
     */
    @Id
    private String uuid;

    /**
     * 接受人id
     */
    @Column(name = "recipient_id")
    private String recipientId;

    /**
     * 接收人名称
     */
    @Column(name = "recipient_name")
    private String recipientName;

    /**
     * 发送人id
     */
    @Column(name = "sender_id")
    private String senderId;

    /**
     * 发送人名称
     */
    @Column(name = "sender_name")
    private String senderName;

    /**
     * 内容
     */
    private String content;

    /**
     * 邮件物品
     */
    @Transient
    private List<Good> goods;

    /**
     * 物品uuid
     */
    @Column(name = "goods_id")
    private List<String> goodsId;

    /**
     * 是否已读
     */
    @Column(name = "has_read")
    private boolean hasRead;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public List<String> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(List<String> goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }
}
