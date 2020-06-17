package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.npc.NonPlayerCharacter;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 10:54
 */
@Component
public class ReadNpc extends AbstractJsonModelListManager<NonPlayerCharacter> {
    public ReadNpc() {
        super("npc", "npc");
    }
}
