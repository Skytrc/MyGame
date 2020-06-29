package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.good.GoodNumber;
import com.fung.server.gameserver.content.config.good.Medicine;
import com.fung.server.gameserver.content.config.good.equipment.EquipmentCreated;
import com.fung.server.gameserver.content.domain.good.GoodBaseInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 11:09
 */
@Component
public class GoodManager {

    @Autowired
    MedicineManager medicineManager;

    @Autowired
    EquipmentCreatedManager equipmentCreatedManager;

    public static final int GOOD_ID = 0;

    public static final int GOOD_NAME = 1;

    public static final int GOOD_DESCRIPTION = 2;

    public static final int EQUIPMENT_TYPE = 3;

    public void goodInit() throws IOException, InvalidFormatException {
        medicineManager.medicineInit();
        equipmentCreatedManager.equipmentCreatedInit();
    }

    /**
     * 获取物品模板信息
     * 物品id 物品名字 物品描述
     */
    public String[] getGoodInfoById(int goodId) {
        String[] res = new String[3];
        GoodBaseInfo goodBaseInfo;
        goodBaseInfo = getGoodInfoImplByGoodId(goodId);
        if (goodBaseInfo == null) {
            return null;
        }
        res[GOOD_ID] = String.valueOf(goodBaseInfo.getGoodId());
        res[GOOD_NAME] = goodBaseInfo.getName();
        res[GOOD_DESCRIPTION] = goodBaseInfo.getDescription();
        return res;
    }

    public int getGoodMaxStack(int goodId) {
        return getGoodInfoImplByGoodId(goodId).getMaxStacks();
    }

    /**
     * 判断物品是否为装备
     */
    public boolean isEquipment(int goodId) {
        return goodId >= GoodNumber.EQUIPMENT_START.getPosition() && goodId < GoodNumber.EQUIPMENT_END.getPosition();
    }

    /**
     * 获取物品价值
     */
    public int getGoodValue(int goodId) {
        return getGoodInfoImplByGoodId(goodId).getValue();
    }


    /**
     * 通过ID获取对应的物品接口,在通过操作接口来获取对应的信息
     */
    public GoodBaseInfo getGoodInfoImplByGoodId(int goodId) {
        GoodBaseInfo goodBaseInfo;
        if (goodId > GoodNumber.MEDICINE_START.getPosition() && goodId < GoodNumber.MEDICINE_END.getPosition()) {
            Map<Integer, Medicine> medicineMap = medicineManager.getMedicineMap();
            goodBaseInfo = medicineMap.get(goodId);
        } else if (isEquipment(goodId)) {
            Map<Integer, EquipmentCreated> createdMap = equipmentCreatedManager.getEquipmentCreatedMap();
            goodBaseInfo = createdMap.get(goodId);
        } else {
            return null;
        }
        return goodBaseInfo;
    }

    /**
     * 根据装备ID获取装备最基础的描述
     */
    public String[] getEquipmentInfoById(int goodId) {
        if (isEquipment(goodId)) {
            String[] res = new String[4];
            Map<Integer, EquipmentCreated> createdMap = equipmentCreatedManager.getEquipmentCreatedMap();
            EquipmentCreated equipmentCreated = createdMap.get(goodId);
            res[GOOD_ID] = String.valueOf(equipmentCreated.getGoodId());
            res[GOOD_NAME] = equipmentCreated.getName();
            res[GOOD_DESCRIPTION] = equipmentCreated.getDescription();
            res[EQUIPMENT_TYPE] = equipmentCreated.getType();
            return res;
        }
        return null;
    }
}
