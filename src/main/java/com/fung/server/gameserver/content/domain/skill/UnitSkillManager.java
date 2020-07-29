package com.fung.server.gameserver.content.domain.skill;

import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.config.skill.TreatmentSkill;
import com.fung.server.gameserver.content.entity.Skill;

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
     * 使用伤害技能，返回技能(用于计算)
     * @return 技能
     */
    public DamageSkill useDamageSkill(int skillId) {
        return (DamageSkill) checkNullAndCd(skillId);
    }

    public TreatmentSkill useTreatmentSkill(int skillId) {
        return (TreatmentSkill) checkNullAndCd(skillId);
    }

    public boolean inCd(Skill skill, long nowTime) {
        // 检查公共cd
        if (lastUseSkillTime == 0 || lastUseSkillTime + ALL_SKILL_CD < nowTime) {
            // 检查cd
            return (nowTime < skill.getLastUseTime() + skill.getCd() * 1000);
        }
        return true;
    }

    public Skill checkNullAndCd(int skillId) {
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
        return skill;
    }

    public void updateSkill(List<Skill> skills) {
        skills.forEach(skill -> skillMap.put(skill.getId(), skill));
    }

    public List<Skill> getAllSkill() {
        return new ArrayList<>(skillMap.values());
    }
}
