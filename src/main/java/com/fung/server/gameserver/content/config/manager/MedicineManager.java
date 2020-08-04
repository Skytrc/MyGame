package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.readconfig.ReadMedicine;
import com.fung.server.gameserver.content.config.good.Medicine;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.util.Uuid;
import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:38
 */
@Component
public class MedicineManager{

    /**
     * 该类物品名字，用于在策略模式中，在GoodMap中映射对应的Map
     */
    private String goodName;

    private Map<Integer, Medicine> medicineMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineManager.class);

    @Autowired
    private ReadMedicine readMedicine;

    private Gson gson;

    public void medicineInit(Gson gson) throws IOException, InvalidFormatException {
        // 从配置中读取药品
        readMedicine.init();
        medicineMap = readMedicine.getModelMap();
        this.gson = gson;
    }

    public Good createNoBeingNewMedicine(int medicineId, int goodQuantity) {
        Medicine templateMedicine = medicineMap.get(medicineId);
        // 使用Gson序列化
        Good newGood = gson.fromJson(gson.toJson(templateMedicine), Good.class);
        newGood.setUuid(Uuid.createUuid());
        newGood.setQuantity(goodQuantity);
        newGood.setGetTime(System.currentTimeMillis());
        return newGood;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Map<Integer, Medicine> getMedicineMap() {
        return medicineMap;
    }

    public void setMedicineMap(Map<Integer, Medicine> medicineMap) {
        this.medicineMap = medicineMap;
    }
}
