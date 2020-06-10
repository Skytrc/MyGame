package com.fung.server.content.entity;

import com.fung.server.content.config.good.equipment.EquipmentType;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 15:05
 */
@Entity
@PrimaryKeyJoinColumn()
@Table(name = "equipment")
public class Equipment extends Good {

    /**
     * 强化等级
     */
    private int level;

    /**
     * 装备名称
     */
    @Transient
    private String name;

    /**
     * 装备类型
     */
    @Transient
    private EquipmentType type;

    /**
     * 装备最大耐久度
     */
    @Transient
    private int maxDurable;

    /**
     * 装备目前耐久度
     */
    private int durable;

    /**
     * 装备穿戴的最低等级
     */
    @Transient
    private int minLevel;

    /**
     * 装备增加血量
     */
    private int plusHp;

    /**
     * 装备增加魔法值
     */
    private int plusMp;

    /**
     * 装备增加攻击力
     */
    private int attackPower;

    /**
     * 装备增加魔法力
     */
    private int magicPower;

    /**
     * 装备增加暴击率
     */
    private float criticalRate;

    /**
     * 装备增加防御力
     */
    private int defense;

    /**
     * 装备描述
     */
    @Transient
    private String description;

    /**
     * 词条
     */
    private String entriesNum;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurable() {
        return durable;
    }

    public void setDurable(int durable) {
        this.durable = durable;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getPlusHp() {
        return plusHp;
    }

    public void setPlusHp(int plusHp) {
        this.plusHp = plusHp;
    }

    public int getPlusMp() {
        return plusMp;
    }

    public void setPlusMp(int plusMp) {
        this.plusMp = plusMp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public float getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(float criticalRate) {
        this.criticalRate = criticalRate;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntriesNum() {
        return entriesNum;
    }

    public void setEntriesNum(String entriesNum) {
        this.entriesNum = entriesNum;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public int getMaxDurable() {
        return maxDurable;
    }

    public void setMaxDurable(int maxDurable) {
        this.maxDurable = maxDurable;
    }
}
