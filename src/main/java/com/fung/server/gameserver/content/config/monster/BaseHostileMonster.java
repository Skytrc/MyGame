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
     * 价值
     */
    private int value;

    private int exp;

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
}
