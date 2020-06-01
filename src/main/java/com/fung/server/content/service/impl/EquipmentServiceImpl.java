package com.fung.server.content.service.impl;

import com.fung.server.content.dao.EquipmentDao;
import com.fung.server.content.domain.equipment.EquipmentCreated;
import com.fung.server.content.entity.Equipment;
import com.fung.server.content.manager.EquipmentCreatedManager;
import com.fung.server.content.service.EquipmentService;
import com.fung.server.content.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 16:36
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentCreatedManager equipmentCreatedManager;

    @Autowired
    PlayerUtil playerUtil;

    @Autowired
    EquipmentDao equipmentDao;

    @Override
    public void createNewEquipment(String channelId, int equipmentId) {
        EquipmentCreated created = equipmentCreatedManager.getEquipmentCreatedMap().get(equipmentId);
        Equipment equipment = new Equipment();

        equipment.setPlayerId(playerUtil.getCurrentPlayer(channelId).getUuid());
        equipment.setGoodId(equipmentId);
        equipment.setGetTime(System.currentTimeMillis());
        equipment.setQuantity(1);
        equipment.setName(created.getName());
        equipment.setRank(0);
        equipment.setMaxDurable(created.getDurable());
        equipment.setDurable(created.getDurable());
        equipment.setMinLevel(created.getMinLevel());
        equipment.setDescription(created.getDescription());
        // 生成随机数值
        equipment.setPlusHp(judgeMin(created.getMinHp(), created.getMaxHp()));
        equipment.setPlusMp(judgeMin(created.getMinMp(), created.getMaxMp()));
        equipment.setAttackPower(judgeMin(created.getMinPower(), created.getMaxPower()));
        equipment.setMagicPower(judgeMin(created.getMinMagicPower(), created.getMaxMagicPower()));
        equipment.setCriticalRate(judgeMin(created.getMinCriticalRate(), created.getMaxCriticalRate()));
        equipment.setDefense(judgeMin(created.getMinDefense(), created.getMaxDefense()));
        equipmentDao.updateEquipment(equipment);

    }

    /**
     * @param minValue 装备最低数值
     * @param maxValue 装备最高数值
     * @return 范围内随机值
     */
    public int judgeMin(int minValue, int maxValue) {
        if (minValue == 0) {
            return 0;
        }
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }
}
