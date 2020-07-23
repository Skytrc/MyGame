package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.readconfig.ReadTreatmentSkill;
import com.fung.server.gameserver.content.config.skill.TreatmentSkill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/7/23 11:54
 */
@Component
public class TreatmentSkillManager {

    private Map<Integer, TreatmentSkill> treatmentSkillMap;

    @Autowired
    private ReadTreatmentSkill readTreatmentSkill;

    public void treatmentSkillInit() throws IOException, InvalidFormatException {
        readTreatmentSkill.init();
        treatmentSkillMap = readTreatmentSkill.getModelMap();
    }

    public Map<Integer, TreatmentSkill> getTreatmentSkillMap() {
        return treatmentSkillMap;
    }
}
