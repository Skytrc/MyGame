package com.fung.server.gameserver.content.domain.equipment;

import com.fung.server.gameserver.content.config.good.equipment.EquipmentCreated;
import com.fung.server.gameserver.content.config.good.equipment.EquipmentType;
import com.fung.server.gameserver.content.config.manager.EquipmentCreatedManager;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author skytrc@163.com
 * @date 2020/6/5 10:56
 */
@Component
public class EquipmentCreatedFactory {

    @Autowired
    private EquipmentCreatedManager equipmentCreatedManager;

    public Equipment createNewEquipment(String playerId, int equipmentId) {
        Equipment equipment = createNoBelongingEquipment(equipmentId);
        equipment.setPlayerId(playerId);
        return equipment;
    }

    public Equipment createNoBelongingEquipment(int equipmentId) {
        EquipmentCreated created = equipmentCreatedManager.getEquipmentCreatedMap().get(equipmentId);
        Equipment equipment = new Equipment();
        // 装备基础信息
        equipment.setUuid(Uuid.createUuid());
        equipment.setGoodId(equipmentId);
        equipment.setGetTime(System.currentTimeMillis());
        equipment.setType(getType(created.getType()));
        equipment.setQuantity(1);
        equipment.setName(created.getName());
        equipment.setLevel(0);
        equipment.setMaxDurable(created.getDurable());
        equipment.setDurable(created.getDurable());
        equipment.setMinLevel(created.getMinLevel());
        equipment.setDescription(created.getDescription());
        // 生成装备随机数值
        equipment.setPlusHp(judgeMin(created.getMinHp(), created.getMaxHp()));
        equipment.setPlusMp(judgeMin(created.getMinMp(), created.getMaxMp()));
        equipment.setAttackPower(judgeMin(created.getMinPower(), created.getMaxPower()));
        equipment.setMagicPower(judgeMin(created.getMinMagicPower(), created.getMaxMagicPower()));
        equipment.setCriticalRate(created.getMinCriticalRate());
        equipment.setDefense(judgeMin(created.getMinDefense(), created.getMaxDefense()));
        return equipment;
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
