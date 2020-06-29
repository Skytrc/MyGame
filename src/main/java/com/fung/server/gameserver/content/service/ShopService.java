package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/18 11:31
 */
public interface ShopService {

    /**
     * 购买物品
     * @param channelId channel Id
     * @param goodId 需要购买的物品id
     * @param num 需要购买数量
     * @return 购买情况
     */
    String buyGood(String channelId, int goodId, int num);

    /**
     * 出售物品
     * @param channelId channel Id
     * @param backpackNum 需要卖物品的背包Id
     * @param quantity 数量
     * @return 售出情况
     */
    String shellGood(String channelId, int backpackNum, int quantity);
}
