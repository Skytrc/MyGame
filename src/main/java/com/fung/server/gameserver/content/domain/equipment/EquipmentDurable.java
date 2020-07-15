package com.fung.server.gameserver.content.domain.equipment;

import com.fung.server.gameserver.content.config.good.equipment.EquipmentType;
import com.fung.server.gameserver.content.domain.calculate.PlayerValueCalculate;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于计算用户装备耐久度，当耐久度为零时，装备加成效果为0
 * @author skytrc@163.com
 * @date 2020/6/9 17:39
 */
@Component
public class EquipmentDurable {

    @Autowired
    private PlayerValueCalculate playerValueCalculate;

    public void equipmentDurableMinus(Player player, boolean isDefense) {
        boolean isZero = false;
        List<Equipment> equipments = player.getEquipments();
        for (Equipment equipment : equipments) {
            // 判断位置是否有装备
            if (equipment.getName() == null) {
                continue;
            }
            // 判断是否为防御操作
            if (isDefense) {
                if (!EquipmentType.isDefenseType(equipment)) {
                    continue;
                }
            } else {
                if (EquipmentType.isDefenseType(equipment)) {
                    continue;
                }
            }
            // 判断原来装备的耐久是否为0
            if (equipment.getDurable() == 0) {
                continue;
            }
            int durable = equipment.getDurable() - 1;
            // 如果装备耐久掉到0，重新计算玩家数值
            if (durable == 0) {
                isZero = true;
            }
            equipment.setDurable(durable);
        }
        if (isZero) {
            playerValueCalculate.calculatePlayerBaseValue(player);
        }
    }
}
