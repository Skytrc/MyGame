package com.fung.server.content.config.monster;

import com.fung.server.content.entity.Player;
import com.fung.server.content.entity.Skill;
import com.fung.server.content.entity.base.BaseElement;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 11:33
 */
public abstract class BaseMonster extends BaseElement {

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
     * 怪物所在地图
     */
    private int inMapId;

    /**
     * 怪物所在地图X轴
     */
    private int inMapX;

    /**
     * 怪物所在地图Y轴
     */
    private int inMapY;

    /**
     * 怪物攻击距离
     */
    private int attackDistance;

    /**
     * 怪物技能
     */
    private List<Skill> monsterSkill;

    /**
     * 当前攻击目标（玩家）
     */
    private Player currentAttackPlayer;

    /**
     * 是否正在攻击
     */
    private volatile boolean isAttacking;

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

    public int getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(int attackDistance) {
        this.attackDistance = attackDistance;
    }

    public List<Skill> getMonsterSkill() {
        return monsterSkill;
    }

    public void setMonsterSkill(List<Skill> monsterSkill) {
        this.monsterSkill = monsterSkill;
    }

    public Player getCurrentAttackPlayer() {
        return currentAttackPlayer;
    }

    public void setCurrentAttackPlayer(Player currentAttackPlayer) {
        this.currentAttackPlayer = currentAttackPlayer;
    }

    public int getInMapId() {
        return inMapId;
    }

    public void setInMapId(int inMapId) {
        this.inMapId = inMapId;
    }

    public int getInMapX() {
        return inMapX;
    }

    public void setInMapX(int inMapX) {
        this.inMapX = inMapX;
    }

    public int getInMapY() {
        return inMapY;
    }

    public void setInMapY(int inMapY) {
        this.inMapY = inMapY;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
