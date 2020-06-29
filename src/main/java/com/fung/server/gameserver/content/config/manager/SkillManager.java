package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.config.skill.SkillNumber;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:38
 */
@Component
public class SkillManager {

    @Autowired
    DamageSkillManager damageSkillManager;

    public void skillInit() throws IOException, InvalidFormatException {
        damageSkillManager.damageSkillInit();
    }

    public DamageSkill getSkillById(int skillId) {
        return damageSkillManager.getDamageSkillMap().get(skillId);
    }

    public String[] getSkillInfoById(int id) {
        String[] res = new String[5];
        if (id >= SkillNumber.DAMAGE_SKILL_START.getPosition() && id < SkillNumber.DAMAGE_SKILL_END.getPosition()) {
            Map<Integer, DamageSkill> skillMap = damageSkillManager.getDamageSkillMap();
            DamageSkill damageSkill = skillMap.get(id);
            res[0] = damageSkill.getName();
            res[1] = String.valueOf(damageSkill.getPhysicalDamage());
            res[2] = String.valueOf(damageSkill.getMagicDamage());
            res[3] = String.valueOf(damageSkill.getCd());
            res[4] = damageSkill.getDescription();
            return res;
        }
        return null;
    }
}
