package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.entity.Medicine;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:40
 */
@Component
public class ReadMedicine extends AbstractJsonModelListManager<Medicine> {

    public ReadMedicine() {
        super("medicine", "good");
    }
}
