package com.fung.server.gameserver.content.config.monster;

import com.fung.server.gameserver.content.domain.buff.UnitBuffManager;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.skill.UnitSkillManager;
import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.content.entity.base.BaseElement;

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
     * 怪物攻击距离
     */
    private int attackDistance;

    /**
     * 是否正在攻击
     */
    private volatile boolean isAttacking;

    /**
     * 移动后临时坐标x
     */
    private int tempX;

    /**
     * 移动后临时坐标y
     */
    private int tempY;

    /**
     * 管理怪兽身上的buff
     */
    private UnitBuffManager unitBuffManager;

    /**
     * 处理怪物事件地图线程
     */
    private GameMapActor gameMapActor;

    /**
     * 管理技能模块
     */
    private List<Skill> skills;

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

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public UnitBuffManager getUnitBuffManager() {
        return unitBuffManager;
    }

    public void setUnitBuffManager(UnitBuffManager unitBuffManager) {
        this.unitBuffManager = unitBuffManager;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public GameMapActor getGameMapActor() {
        return gameMapActor;
    }

    public void setGameMapActor(GameMapActor gameMapActor) {
        this.gameMapActor = gameMapActor;
    }

    public int getTempX() {
        return tempX;
    }

    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public void setTempY(int tempY) {
        this.tempY = tempY;
    }
}
