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

    List<Equipment> findEquipmentsByPlayerId(String PlayerId);
}
