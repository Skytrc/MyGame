package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.npc.NpcShop;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 10:56
 */
@Component
public class ReadNpcShop extends AbstractJsonModelListManager<NpcShop> {
    public ReadNpcShop() {
        super("npcShop", "npc");
    }
}
