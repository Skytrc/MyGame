package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.monster.MonsterDistribution;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 15:56
 */
@Component
public class ReadMonsterDistribution extends AbstractJsonModelListManager<MonsterDistribution> {
    public ReadMonsterDistribution() {
        super("monster", "monsterDistribution");
    }
}
