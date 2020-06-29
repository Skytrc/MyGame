package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/28 9:52
 */
@Component
public class ReadMap extends AbstractJsonModelListManager<GameMap> {
    public ReadMap() {
        super("map", "map");
    }
}
