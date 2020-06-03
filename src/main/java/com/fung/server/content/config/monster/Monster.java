package com.fung.server.content.config.monster;

import com.fung.server.content.entity.base.BaseElement;
import com.fung.server.excel2class.Model;

/**
 * 怪兽
 * @author skytrc@163.com
 * @date 2020/5/28 17:18
 */
public class Monster extends BaseElement implements Model {

    private int id;

    /**
     * 怪物等级
     */
    private int level;

    /**
     * 最大生命值
     */
    private int maxHealthPoint;

    /**
     * 目前生命值
     */
    private int healthPoint;

    /**
     * 攻击力
     */
    private int attackPower;

    /**
     * 魔法力
     */
    private int magicPower;

    /**
     * 防御
     */
    private int defend;

    /**
     * 经验值
     */
    private int exp;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

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

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
