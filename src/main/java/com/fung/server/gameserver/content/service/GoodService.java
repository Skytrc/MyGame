package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/8 17:52
 */
public interface GoodService {

    /**
     * 使用物品
     * @param channelId channel Id
     * @param position 在背包中的位置
     * @return 是否使用成功，效果
     */
    String useGood(int position, String channelId);

    /**
     * 穿戴装备
     * @param channelId channel Id
     * @param position 装备在背包中的位置
     * @return 穿戴后的效果
     */
    String putOnEquipment(int position, String channelId);

    /**
     * 脱落装备
     * @param channelId channel Id
     * @param equipmentPosition 装备在玩家身上的位置
     * @return 装备脱落后效果
     */
    String takeOffEquipment(int equipmentPosition, String channelId);


}
