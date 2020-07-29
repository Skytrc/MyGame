package com.fung.server.gameserver.content.domain.monster.monsterfsm;

/**
 * 怪物状态
 * @author skytrc@163.com
 * @date 2020/7/27 10:36
 */
public enum MonsterStatus {

    /**
     * 待命
     */
    STANDBY,

    /**
     * 移动并检测
     */
    MOVE_AND_CHECK,

    /**
     * 攻击
     */
    ATTACK,

    /**
     * 死亡&重生
     */
    DEATH_AND_REBIRTH
}
