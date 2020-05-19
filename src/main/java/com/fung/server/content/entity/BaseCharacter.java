package com.fung.server.content.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 基础人物属性
 * @author skytrc@163.com
 * @date 2020/5/19 17:37
 */
@MappedSuperclass
public abstract class BaseCharacter extends BaseElement {

    /**
     * 角色状态 0死亡  1存货
     */
    private int status;

    /**
     * 最大血量
     */
    @Column(name = "max_heath_point")
    private int maxHealthPoint;

    /**
     * 目前血量
     */
    @Column(name = "health_point")
    private int healthPoint;

    /**
     * 最大魔法值
     */
    @Column(name = "max_magic_point")
    private int maxMagicPoint;

    /**
     * 目前魔法值
     */
    @Column(name = "magic_point")
    private int magicPoint;

    /**
     * 攻击力
     */
    @Column(name = "attack_power")
    private int attackPower;

    /**
     * 防御力
     */
    @Column(name = "defense")
    private int defense;

    /**
     * 位于哪张地图的Id
     */
    @Column(name = "in_map_id")
    private int inMapId;

    /**
     * 在X轴
     */
    @Column(name = "in_map_x")
    private int inMapX;

    /**
     * 在Y轴
     */
    @Column(name = "in_map_y")
    private int inMapY;


    public int getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public void setMaxHealthPoint(int maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getMaxMagicPoint() {
        return maxMagicPoint;
    }

    public void setMaxMagicPoint(int maxMagicPoint) {
        this.maxMagicPoint = maxMagicPoint;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInMapY() {
        return inMapY;
    }

    public void setInMapY(int inMapY) {
        this.inMapY = inMapY;
    }

    public int getInMapX() {
        return inMapX;
    }

    public void setInMapX(int inMapX) {
        this.inMapX = inMapX;
    }

    public int getInMapId() {
        return inMapId;
    }

    public void setInMapId(int inMapId) {
        this.inMapId = inMapId;
    }
}
