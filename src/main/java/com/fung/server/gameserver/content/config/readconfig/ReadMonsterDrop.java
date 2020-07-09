package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.monster.MonsterDrop;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/9 16:37
 */
@Component
public class ReadMonsterDrop extends AbstractJsonModelListManager<MonsterDrop> {
    public ReadMonsterDrop() {
        super("monsterDrop", "monster");
    }
}
