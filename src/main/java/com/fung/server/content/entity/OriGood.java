package com.fung.server.content.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基础物品属性
 * @author skytrc@163.com
 * @date 2020/5/20 11:07
 */
@Entity
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
    @Column(name = "player_name")
    private String playerName;

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

    /**
     * 如果是装备则关联外键
     */
    @OneToOne()
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentValue equipmentValue;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public EquipmentValue getEquipmentValue() {
        return equipmentValue;
    }

    public void setEquipmentValue(EquipmentValue equipmentValue) {
        this.equipmentValue = equipmentValue;
    }
}
