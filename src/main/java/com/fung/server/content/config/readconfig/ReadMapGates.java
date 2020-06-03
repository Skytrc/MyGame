package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.map.GameMapGates;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/28 11:57
 */
@Component
public class ReadMapGates extends AbstractJsonModelListManager<GameMapGates> {
    public ReadMapGates() {
        super("mapGates", "map");
    }
}
