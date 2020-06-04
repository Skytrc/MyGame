package com.fung.server.content.service.impl;

import com.fung.server.content.dao.EquipmentDao;
import com.fung.server.content.config.good.equipment.EquipmentCreated;
import com.fung.server.content.config.good.equipment.EquipmentType;
import com.fung.server.content.entity.Equipment;
import com.fung.server.content.config.manager.EquipmentCreatedManager;
import com.fung.server.content.service.EquipmentService;
import com.fung.server.content.domain.player.PlayeInfo;
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
    PlayeInfo playeInfo;

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired

    @Override
    public void createNewEquipment(String channelId, int equipmentId) {
        EquipmentCreated created = equipmentCreatedManager.getEquipmentCreatedMap().get(equipmentId);
        Equipment equipment = new Equipment();
        // 装备基础信息
        equipment.setPlayerId(playeInfo.getCurrentPlayer(channelId).getUuid());
        equipment.setGoodId(equipmentId);
        equipment.setGetTime(System.currentTimeMillis());
        equipment.setType(getType(created.getType()));
        equipment.setQuantity(1);
        equipment.setName(created.getName());
        equipment.setRank(0);
        equipment.setMaxDurable(created.getDurable());
        equipment.setDurable(created.getDurable());
        equipment.setMinLevel(created.getMinLevel());
        equipment.setDescription(created.getDescription());
        // 生成装备随机数值
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

    /**
     * 获取装备对应类型（内含装备部位信息）
     * @param type 装备类型（String）
     * @return 装备类型（枚举类）
     */
    public EquipmentType getType(String type) {
        switch (type){
            case "hat" : return EquipmentType.HAT;
            case "weapon" : return EquipmentType.WEAPON;
            case "coat" : return EquipmentType.COAT;
            case "pants" : return EquipmentType.PANTS;
            case "shoe" :return EquipmentType.SHOE;
            default : return null;
        }
    }
}
