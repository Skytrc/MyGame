package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.GameMap;
import com.fung.server.excel2class.AbstractJsonModelListManager;
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
