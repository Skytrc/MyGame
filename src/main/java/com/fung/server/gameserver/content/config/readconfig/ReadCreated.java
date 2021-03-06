package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.good.equipment.EquipmentCreated;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 15:17
 */
@Component
public class ReadCreated extends AbstractJsonModelListManager<EquipmentCreated> {

    public ReadCreated() {
        super("equipment", "good");
    }
}
