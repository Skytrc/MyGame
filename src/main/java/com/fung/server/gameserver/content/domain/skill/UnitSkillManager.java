package com.fung.server.gameserver.content.domain.skill;

import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.content.entity.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:44
 * TODO 只是简单的返回技能伤害，还没有加上治疗技能。需要配置到玩家身上
 */
public class UnitSkillManager {

    private Map<Integer, Skill> skillMap;

    private static final int ALL_SKILL_CD = 1000;

    private static volatile long lastUseSkillTime = 0;

    public UnitSkillManager() {
        skillMap = new HashMap<>();
    }

    /**
     * 使用技能，返回技能(用于计算)
     * @return 技能
     */
    public DamageSkill useDamageSkill(int skillId) {
        Skill skill = skillMap.get(skillId);
        long nowTime = System.currentTimeMillis();
        if (skill == null) {
            return null;
        }
        if (inCd(skill, nowTime)) {
            return null;
        }
        lastUseSkillTime = nowTime;
        skill.setLastUseTime(nowTime);
        return (DamageSkill) skill;
    }

    public boolean inCd(Skill skill, long nowTime) {
        // 检查公共cd
        if (lastUseSkillTime == 0 || lastUseSkillTime + ALL_SKILL_CD < nowTime) {
            // 检查cd
            return (skill.getLastUseTime() != 0 || skill.getCd() + nowTime < skill.getLastUseTime());
        }
        return true;
    }

    public void updateSkill(List<Skill> skills) {
        skills.forEach(skill -> skillMap.put(skill.getId(), skill));
    }

    public List<Skill> getAllSkill() {
        return new ArrayList<>(skillMap.values());
    }
}
