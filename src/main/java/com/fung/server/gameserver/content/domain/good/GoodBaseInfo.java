package com.fung.server.gameserver.content.domain.good;

/**
 * @author skytrc@163.com
 * @date 2020/6/18 17:34
 */
public interface GoodBaseInfo {

    /**
     * 获取物品id 信息
     * @return 物品id 信息
     */
    int getGoodId();

    /**
     * 获取物品名字信息
     * @return 物品名字
     */
    String getName();

    /**
     * 获取物品价值
     * @return 物品价值
     */
    int getValue();

    /**
     * 获取物品最大堆叠数
     * @return 物品最大堆叠数
     */
    int getMaxStacks();

    /**
     * 获取物品描述
     * @return 物品描述
     */
    String getDescription();
}
