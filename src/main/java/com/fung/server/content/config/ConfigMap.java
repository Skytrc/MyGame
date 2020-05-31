package com.fung.server.content.config;

import com.fung.server.content.domain.GameMap;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/28 9:52
 */
@Component
public class ConfigMap extends AbstractJsonModelListManager<GameMap> {
    public ConfigMap() {
        super("map", "map");
    }
}
