package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 17:13
 */
public interface NpcService {

    /**
     * 返回NPC所有的选项
     * @param channelId channel id
     * @return NPC所有的选项
     */
    String allChoose(String channelId);

    /**
     * 返回选项内容
     * @param channelId channel id
     * @param choose 选项
     * @return 选项内容
     */
    String oneChoose(String channelId, int choose);

    /**
     * 打开商店列表
     * @param channelId channel id
     * @return 商店列表
     */
    String openShop(String channelId);

    /**
     * 开启副本
     * @param channel channel id
     * @return 是否开启副本
     */
    String openDungeon(String channel);

    /**
     * 加入副本
     * @param channelId channel id
     * @param dungeonId 副本id
     * @return 是否加入副本
     */
    String joinDungeon(String channelId, String dungeonId);
}
