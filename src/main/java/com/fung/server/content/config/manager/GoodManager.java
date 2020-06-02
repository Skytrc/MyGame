package com.fung.server.content.config.manager;

import com.fung.server.content.config.GoodNumber;
import com.fung.server.content.config.Medicine;
import com.fung.server.content.config.equipment.EquipmentCreated;
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

    public void goodInit() throws IOException, InvalidFormatException {
        medicineManager.medicineInit();
    }

    @SuppressWarnings({"rawtypes"})
    @Deprecated
    public Map getGood(String goodName) {
        if ("medicine".equals(goodName)) {
            return medicineManager.getMedicineMap();
        }
        else {
            return null;
        }
    }

    /**
     * TODO 可以优化，重复代码过多
     * 获取物品模板信息
     */
    public String[] getGoodInfoById(int id) {
        if (id > GoodNumber.MEDICINE_START.getPosition() && id < GoodNumber.MEDICINE_END.getPosition()) {
            Map<Integer, Medicine> medicineMap = medicineManager.getMedicineMap();
            Medicine medicine = medicineMap.get(id);
            String[] res = new String[2];
            res[0] = medicine.getName();
            res[1] = medicine.getDescription();
            return res;
        } else if (id > GoodNumber.EQUIPMENT_START.getPosition() && id < GoodNumber.EQUIPMENT_END.getPosition()) {
            Map<Integer, EquipmentCreated> createdMap = equipmentCreatedManager.getEquipmentCreatedMap();
            EquipmentCreated equipmentCreated = createdMap.get(id);
            String[] res = new String[2];
            res[0] = equipmentCreated.getName();
            res[1] = equipmentCreated.getDescription();
            return res;
        } else {
            return null;
        }
    }

    public int getGoodMaxStack(int id) {
        if (id > GoodNumber.MEDICINE_START.getPosition() && id < GoodNumber.MEDICINE_END.getPosition()) {
            Map<Integer, Medicine> medicineMap = medicineManager.getMedicineMap();
            return medicineMap.get(id).getMaxStacks();
        } else if (id > GoodNumber.EQUIPMENT_START.getPosition() && id < GoodNumber.EQUIPMENT_END.getPosition()) {
            return 1;
        } else {
            return 0;
        }
    }
}
