package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 16:37
 */
public interface AttackService {
    /**
     * 攻击 2.0()
     * @param channelId 通道Id,获取玩家信息
     * @param x x轴
     * @param y y轴
     * @param skillId 技能编号
     * @return 攻击消息
     */
    String attack1(String channelId, int x, int y, int skillId);

    /**
     * AOE攻击
     * @param channelId channel id
     * @param skillId 技能 id
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 攻击消息
     */
    String attackAoe(String channelId, int skillId, int x, int y);

    /**
     * 攻击玩家
     * @param channelId channel Id
     * @param playerId 玩家id
     * @param skillId 技能id
     * @return 攻击消息
     */
    String attackPlayer(String channelId, String playerId, int skillId);

    /**
     * 使用治疗技能
     * @param channelId channel Id
     * @param playerId 玩家id
     * @param skillId 技能id
     * @return 治疗技能消息
     */
    String useTreatmentSkill(String channelId, String playerId, int skillId);
}
