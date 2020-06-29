package com.fung.server.gameserver.content.dao;

import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 10:32
 */
public interface GoodDao {

    /**
     * 玩家所有物品信息
     * @param playerId 玩家id
     * @return 玩家所有物品信息
     */
    List<Good> getGoodByPlayerId(String playerId);

    /**
     * 找所有玩家背包中所有的装备信息
     * @param playerId 玩家id
     * @return 玩家背包的所有装备信息
     */
    List<Equipment> findBackpackEquipment(String playerId);

    /**
     * 插入物品信息
     * @param good 物品信息
     */
    void insertGood(Good good);

    /**
     * 更新物品信息
     * @param good 物品信息
     */
    void updateGood(Good good);
}
