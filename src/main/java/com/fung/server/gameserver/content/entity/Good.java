package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * 基础物品属性
 * @author skytrc@163.com
 * @date 2020/5/20 11:07
 */
@Entity(name = "good")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("good")
public class Good {
    /**
     * 物品独有Id
     */
    @Id
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
     * 在玩家背包的位置
     */
    private int position;

    /**
     * 拥有数量
     */
    private int quantity;

    /**
     * 获取时间
     */
    @Column(name = "get_time")
    private long getTime;

    /**
     * 物品名称
     */
    @Transient
    private String name;

    /**
     * 物品描述
     */
    @Transient
    private String description;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
