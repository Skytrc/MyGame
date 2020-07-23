package com.fung.server.gameserver.content.config.readconfig;

import com.fung.server.gameserver.content.config.skill.TreatmentSkill;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/23 11:50
 */
@Component
public class ReadTreatmentSkill extends AbstractJsonModelListManager<TreatmentSkill> {
    public ReadTreatmentSkill() {
        super("treatmentSkill", "skill");
    }
}
