package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/23 10:53
 */
@Component
public class ReadBuff extends AbstractJsonModelListManager<Buff> {

    public ReadBuff() {
        super("buff", "buff");
    }
}
