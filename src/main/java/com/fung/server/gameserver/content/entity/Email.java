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
     * 接收人id
     */
    @Column(name = "recipient_id")
    private String recipientId;

    /**
     * 接收人是否已读
     */
    @Column(name = "recipient_has_read")
    private boolean recipientHasRead;

    /**
     * 接受人是否已删除
     */
    @Column(name = "recipient_delete")
    private boolean recipientDelete;

    /**
     * 发送人id
     */
    @Column(name = "sender_id")
    private String senderId;

    /**
     * 发送人是否已删除
     */
    @Column(name = "sender_delete")
    private boolean senderDelete;

    /**
     * 发送人名称
     */
    @Column(name = "sender_name")
    private String senderName;

    /**
     * 是否发给全服
     */
    @Column(name = "is_to_all_player")
    private boolean isToAllPlayer;

    /**
     * 邮件主题
     */
    private String subject;

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
    private String goodsId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private long createdTime;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private long sendTime;

    /**
     * 邮件是否过了7天期限
     */
    public boolean emailIsExpired() {
        return System.currentTimeMillis() - createdTime > 604800000;
    }

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isToAllPlayer() {
        return isToAllPlayer;
    }

    public void setToAllPlayer(boolean toAllPlayer) {
        isToAllPlayer = toAllPlayer;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isSend() {
        return sendTime == 0L;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isRecipientDelete() {
        return recipientDelete;
    }

    public void setRecipientDelete(boolean recipientDelete) {
        this.recipientDelete = recipientDelete;
    }

    public boolean isSenderDelete() {
        return senderDelete;
    }

    public void setSenderDelete(boolean senderDelete) {
        this.senderDelete = senderDelete;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isRecipientHasRead() {
        return recipientHasRead;
    }

    public void setRecipientHasRead(boolean recipientHasRead) {
        this.recipientHasRead = recipientHasRead;
    }
}
