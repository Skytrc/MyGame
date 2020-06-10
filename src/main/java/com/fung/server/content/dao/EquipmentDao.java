package com.fung.server.content.dao;

import com.fung.server.content.entity.Equipment;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 19:27
 */
public interface EquipmentDao {
    /**
     * 插入新增装备
     * @param equipment 新增装备
     */
    void insertEquipment(Equipment equipment);

    /**
     * 更新装备
     * @param equipment 需要更新的装备
     */
    void updateEquipment(Equipment equipment);

    /**
     * 获取玩家所有装备信息
     * @param playerId 玩家id
     * @return 玩家拥有的所有装备
     */
    List<Equipment> findEquipmentsByPlayerId(String playerId);
}
