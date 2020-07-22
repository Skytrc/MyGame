package com.fung.server.gameserver.content.domain.skill;

import com.fung.server.gameserver.content.config.manager.DamageSkillManager;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:44
 * TODO 只是简单的返回技能伤害，还没有加上治疗技能。需要配置到玩家身上
 */
public class PersonalSkill {

    /**
     * 技能cd
     */
    private Map<Integer,Long> skillCd;

    /**
     * 个人技能
     */
    private List<Integer> skill;

    private static final int ALL_SKILL_CD = 1000;

    private static volatile long lastUseSkillTime = 0;

    @Autowired
    private DamageSkillManager damageSkillManager;

    /**
     * 返回物理基础伤害
     * 还在cd中就返回0
     */
    public int physicalDamageValue(int skillId) {
        long nowTime = System.currentTimeMillis();
        DamageSkill skill = damageSkillManager.getDamageSkillMap().get(skillId);
        if (inCd(skill, nowTime)) {
            // 更新对应技能的cd
            skillCd.put(skill.getId(), nowTime);
            // 更新公共cd戳,
            lastUseSkillTime = nowTime;
            return skill.getPhysicalDamage();
        }
        return 0;
    }

    public boolean inCd(DamageSkill skill, long nowTime) {
        // 检查公共cd
        if (lastUseSkillTime == 0 || lastUseSkillTime + ALL_SKILL_CD < nowTime) {
            // 检查cd
            return skill.getCd() + nowTime <= skillCd.get(skill.getId());
        }
        return true;
    }

    public Map<Integer, Long> getSkillCd() {
        return skillCd;
    }

    public void setSkillCd(Map<Integer, Long> skillCd) {
        this.skillCd = skillCd;
    }

    public List<Integer> getSkill() {
        return skill;
    }

    public void setSkill(List<Integer> skill) {
        this.skill = skill;
    }
}
