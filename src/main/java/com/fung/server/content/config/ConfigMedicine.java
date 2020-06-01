package com.fung.server.content.config;

import com.fung.server.content.domain.Medicine;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:40
 */
@Component
public class ConfigMedicine extends AbstractJsonModelListManager<Medicine> {
    public ConfigMedicine() {
        super("medicine", "good");
    }
}
