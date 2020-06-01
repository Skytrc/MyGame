package com.fung.server.content.manager;

import com.fung.server.content.config.ConfigEquipmentCreated;
import com.fung.server.content.domain.equipment.EquipmentCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 15:19
 */
@Component
public class EquipmentCreatedManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentCreatedManager.class);

    @Autowired
    private ConfigEquipmentCreated configEquipmentCreated;

    /**
     * 存储 key 装备对应id  value EquipmentCreated
     */
    private Map<Integer, EquipmentCreated> equipmentCreatedMap;

    public void equipmentCreatedInit() {
        equipmentCreatedMap = configEquipmentCreated.getModelMap();
    }

    public Map<Integer, EquipmentCreated> getEquipmentCreatedMap() {
        return equipmentCreatedMap;
    }

    public void setEquipmentCreatedMap(Map<Integer, EquipmentCreated> equipmentCreatedMap) {
        this.equipmentCreatedMap = equipmentCreatedMap;
    }
}
