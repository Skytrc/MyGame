package com.fung.server.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 12:51
 */
public interface ShowService {

    /**
     * 展示玩家信息
     * @param channelId channelId
     * @return 玩家信息
     */
    String showPlayer(String channelId);

    /**
     * 展示同一地图上在线玩家
     * @param channelId channel id
     * @return 地图玩家信息
     */
    String showMapOnlinePlayer(String channelId);

    /**
     * 展示地图上存在的元素
     * @param channelId channel id
     * @return 地图元素
     */
    String showMapElement(String channelId);

    /**
     * 展示背包元素
     * @param channelId channel id
     * @return 背包元素
     */
    String showBackpack(String channelId);

    /**
     * 展示技能
     * @param channelId  channel id
     * @return 技能元素
     */
    String showSkill(String channelId);

    /**
     * 穿在身上的装备信息
     * @param channelId channel Id
     * @return 穿在身上装备的信息
     */
    String showBodyEquipment(String channelId);

    /**
     * 返回怪物信息
     * @param channelId channelID
     * @param x x轴
     * @param y y轴
     * @return 怪物信息
     */
    String showMonster(String channelId, int x, int y);
}
