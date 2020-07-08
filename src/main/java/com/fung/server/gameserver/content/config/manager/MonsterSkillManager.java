package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.monster.MonsterSkill;
import com.fung.server.gameserver.content.config.readconfig.ReadMonsterSkill;
import com.fung.server.gameserver.content.entity.Skill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 12:13
 */
@Component
public class MonsterSkillManager {

    /**
     * key 怪物id   value 技能id
     */
    private Map<Integer, List<Skill>> monsterSkillMap = new HashMap<>();

    @Autowired
    private ReadMonsterSkill readMonsterSkill;

    @Autowired
    private SkillManager skillManager;

    public void monsterSkillInit() throws IOException, InvalidFormatException {
        readMonsterSkill.init();

        HashMap<Integer, MonsterSkill> modelMap = readMonsterSkill.getModelMap();
        for (Map.Entry<Integer, MonsterSkill> entry : modelMap.entrySet()) {
            MonsterSkill value = entry.getValue();
            String skillId = value.getSkillId();
            // 分割字符，获取技能id
            String[] list = skillId.split(",");
            List<Skill> skills = new ArrayList<>();
            for (String s : list) {
                skills.add(skillManager.getSkillById(Integer.parseInt(s)));
            }
            monsterSkillMap.put(value.getMonsterId(), skills);
        }

    }

    public List<Skill> getMonsterSkillByMonsterId(int monsterId) {
        return monsterSkillMap.get(monsterId);
    }
}
