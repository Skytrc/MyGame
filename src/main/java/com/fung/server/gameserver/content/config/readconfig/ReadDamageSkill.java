package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:25
 */
@Component
public class ReadDamageSkill extends AbstractJsonModelListManager<DamageSkill> {
    public ReadDamageSkill() {
        super("damageSkill", "skill");
    }
}
