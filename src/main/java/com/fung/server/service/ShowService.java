package com.fung.server.service;

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
}
