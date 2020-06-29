package com.fung.server.gameserver.content.domain.calculate;

import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/8 18:04
 */
@Component
public class PlayerValueCalculate {

    /**
     * 计算玩家所有的基础数值加成总和
     * @param player 玩家实体
     */
    public void calculatePlayerBaseValue(Player player) {
        int plusHp = 0;
        int plusMp = 0;
        int plusAttackPower = 0;
        int plusMagicPower = 0;
        float plusCriticalRate = 0;
        int plusDefense = 0;
        // 获取所有装备的数值
        List<Equipment> equipments = player.getEquipments();
        for (Equipment equipment : equipments) {
            if (equipment.getDurable() == 0) {
                continue;
            }
            plusHp += equipment.getPlusHp();
            plusMp += equipment.getPlusMp();
            plusAttackPower += equipment.getAttackPower();
            plusMagicPower += equipment.getMagicPower();
            plusDefense += equipment.getDefense();
            plusCriticalRate += equipment.getCriticalRate();
        }
        player.setTotalHealthPoint(plusHp + player.getHealthPoint());
        player.setTotalAttackPower(plusAttackPower + player.getAttackPower());
        player.setTotalMagicPower(plusMagicPower + player.getMagicPower());
        player.setTotalMagicPoint(plusMp + player.getMagicPoint());
        player.setTotalDefense(plusDefense + player.getDefense());
        player.setTotalCriticalRate(plusCriticalRate + player.getCriticalRate());
    }
}
