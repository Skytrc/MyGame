package com.fung.server.content.config;

import com.fung.server.content.domain.equipment.EquipmentCreated;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 15:17
 */
@Component
public class ConfigEquipmentCreated extends AbstractJsonModelListManager<EquipmentCreated> {

    public ConfigEquipmentCreated() {
        super("equipment", "good");
    }
}
