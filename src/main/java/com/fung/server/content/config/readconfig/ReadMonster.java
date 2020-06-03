package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.monster.Monster;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 15:32
 */
@Component
public class ReadMonster extends AbstractJsonModelListManager<Monster> {
    public ReadMonster() {
        super("monster", "monster");
    }
}
