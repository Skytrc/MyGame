package com.fung.server.gameserver.content.config.monster;

import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Unit;

/**
 * @author skytrc@163.com
 * @date 2020/7/10 17:35
 */
public class BaseHostileMonster extends BaseMonster implements Unit {

    /**
     * 当前攻击目标（玩家）
     */
    private Player currentAttackPlayer;

    /**
     * 感应范围（扫描敌人）
     */
    private int sensingRange;

    /**
     * 移动最大距离
     */
    private int maxMoveDistance;

    /**
     * 是否为重生状态
     */
    private boolean rebirth;

    /**
     * 是否自動攻擊
     */
    private int isAutoAttack;

    /**
     * 价值
     */
    private int value;

    private int exp;

    public boolean tooFar() {
        return Math.sqrt(Math.pow(Math.abs(getInMapX() - getTempX()), 2) + Math.pow(Math.abs(getInMapY() - getTempY()), 2)) > maxMoveDistance;
    }

    public Player getCurrentAttackPlayer() {
        return currentAttackPlayer;
    }

    public void setCurrentAttackPlayer(Player currentAttackPlayer) {
        this.currentAttackPlayer = currentAttackPlayer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getSensingRange() {
        return sensingRange;
    }

    public void setSensingRange(int sensingRange) {
        this.sensingRange = sensingRange;
    }

    public int getMaxMoveDistance() {
        return maxMoveDistance;
    }

    public void setMaxMoveDistance(int maxMoveDistance) {
        this.maxMoveDistance = maxMoveDistance;
    }

    public boolean isRebirth() {
        return rebirth;
    }

    public void setRebirth(boolean rebirth) {
        this.rebirth = rebirth;
    }

    public int getIsAutoAttack() {
        return isAutoAttack;
    }

    public void setIsAutoAttack(int isAutoAttack) {
        this.isAutoAttack = isAutoAttack;
    }
}
