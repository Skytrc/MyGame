package com.fung.server.content.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基础物品属性
 * @author skytrc@163.com
 * @date 2020/5/20 11:07
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "good")
public class OriGood{
    /**
     * 物品独有Id
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    /**
     * 物品拥有者id
     */
    @Column(name = "player_id")
    private String playerId;

    /**
     * 物品配置id
     */
    @Column(name = "good_id")
    private int goodId;

    /**
     * 拥有数量
     */
    private int quantity;

    /**
     * 获取时间
     */
    @Column(name = "get_time")
    private long getTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerName) {
        this.playerId = playerName;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getGetTime() {
        return getTime;
    }

    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }
}
