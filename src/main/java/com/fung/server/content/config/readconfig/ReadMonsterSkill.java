package com.fung.server.content.config.readconfig;

import com.fung.server.content.config.monster.MonsterSkill;
import com.fung.server.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 12:02
 */
@Component
public class ReadMonsterSkill extends AbstractJsonModelListManager<MonsterSkill> {

    public ReadMonsterSkill() {
        super("monsterSkill", "monster");
    }
}
