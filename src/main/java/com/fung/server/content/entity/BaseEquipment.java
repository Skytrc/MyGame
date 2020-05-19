package com.fung.server.content.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 基础装备属性
 * @author skytrc@163.com
 * @date 2020/5/19 18:23
 */
@MappedSuperclass
public abstract class BaseEquipment extends BaseElement {

    /**
     * 装备耐久度
     */
    @Column(name = "durable")
    private int durable;

    /**
     * 装备攻击力
     */
    @Column(name = "power")
    private int power;

    /**
     * 装备血量
     */
    @Column(name = "health_point")
    private int healthPoint;

    /**
     * 装备魔法值
     */
    @Column(name = "magic")
    private int magicPoint;

    public int getDurable() {
        return durable;
    }

    public void setDurable(int durable) {
        this.durable = durable;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }
}
