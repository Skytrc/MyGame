package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.readconfig.ReadDamageSkill;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:28
 */
@Component
public class DamageSkillManager {

    private Map<Integer, DamageSkill> damageSkillMap;

    @Autowired
    private ReadDamageSkill readDamageSkill;

    public void damageSkillInit() throws IOException, InvalidFormatException {
        readDamageSkill.init();

        damageSkillMap = readDamageSkill.getModelMap();
    }

    public Map<Integer, DamageSkill> getDamageSkillMap() {
        return damageSkillMap;
    }

    public void setDamageSkillMap(Map<Integer, DamageSkill> damageSkillMap) {
        this.damageSkillMap = damageSkillMap;
    }
}
