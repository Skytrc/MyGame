package com.fung.server.content.config;

import com.fung.server.content.domain.GameMapGates;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/28 11:57
 */
@Component
public class ConfigMapGates extends AbstractJsonModelListManager<GameMapGates> {
    public ConfigMapGates() {
        super("mapGates", "map");
    }
}
