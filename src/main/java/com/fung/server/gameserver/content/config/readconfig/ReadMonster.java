package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 15:32
 */
@Component
public class ReadMonster extends AbstractJsonModelListManager<NormalMonster> {
    public ReadMonster() {
        super("monster", "monster");
    }
}
