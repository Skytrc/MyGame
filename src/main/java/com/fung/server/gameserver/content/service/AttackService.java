package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 16:37
 */
public interface AttackService {

    /**
     * 攻击
     * @throws InterruptedException
     * @param channelId 通道Id,获取玩家信息
     * @param x x轴
     * @param y y轴
     * @param skillId 技能编号
     * @return 攻击消息
     */
    String attack(String channelId, int x, int y, int skillId) throws InterruptedException;
}
