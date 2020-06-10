package com.fung.server.content.domain.skill;

import com.fung.server.content.config.manager.SkillManager;
import com.fung.server.content.entity.Skill;

import java.util.ArrayList;
import java.util.List;

import com.fung.server.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/5 11:38
 */
@Component
public class SkillLoad {

    @Autowired
    SkillManager skillManager;

    public List<Skill> newPlayerSkillCreated(String playerId) {
        List<Skill> skills = new ArrayList<>();
        skills.add(createdNewDamageSkill(playerId, 1));
        skills.add(createdNewDamageSkill(playerId, 2));
        return skills;
    }

    public Skill createdNewDamageSkill(String playerId,int skillId) {
        Skill skill = new Skill();
        skill.setUuid(Uuid.createUuid());
        skill.setId(skillId);
        skill.setLevel(0);
        skill.setPlayerId(playerId);
        return skill;
    }
}
