package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.config.skill.SkillNumber;
import com.fung.server.gameserver.content.config.skill.TreatmentSkill;
import com.fung.server.gameserver.content.domain.skill.SkillRange;
import com.fung.server.gameserver.content.entity.Skill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:38
 */
@Component
public class SkillManager {

    /**
     * 该Map存储所有技能
     */
    private Map<Integer, Skill> skillMap;

    @Autowired
    private DamageSkillManager damageSkillManager;

    @Autowired
    private TreatmentSkillManager treatmentSkillManager;

    @Autowired
    private BuffManager buffManager;

    public void skillInit() throws IOException, InvalidFormatException {
        damageSkillManager.damageSkillInit();
        treatmentSkillManager.treatmentSkillInit();
        skillMap = new HashMap<>();
        skillMap.putAll(damageSkillManager.getDamageSkillMap());
        skillMap.putAll(treatmentSkillManager.getTreatmentSkillMap());
        // 初始化技能中的buff 特效
        skillMap.values().forEach(this::skillBuffInit);
    }

    public DamageSkill getSkillById(int skillId) {
        return damageSkillManager.getDamageSkillMap().get(skillId);
    }

    /**
     * 获取skill
     */
    public Skill getSkill(int skillId) {
        return skillMap.get(skillId);
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

    public Map<Integer, Skill> getSkillMap() {
        return skillMap;
    }

    /**
     * 初始化技能的Buff
     */
    public void skillBuffInit(Skill skill) {
        int buffId = skill.getBuffId();
        if (buffId != 0) {
            skill.setBuff(buffManager.createNewBuff(buffId));
        }
    }


    /**
     * TODO 深复制
     */
    public Skill personalSkillInit(Skill skill) {
        int id = skill.getId();
        if (isInRange(SkillRange.damageSkill, id)){
            DamageSkill damageSkill = new DamageSkill();
            DamageSkill templateSkill = (DamageSkill)skillMap.get(id);
            copySkillBaseInfo(skill, damageSkill);
            copySkill(templateSkill, damageSkill);
            damageSkill.setMagicDamage(templateSkill.getMagicDamage());
            damageSkill.setPhysicalDamage(templateSkill.getPhysicalDamage());
            damageSkill.setScopeOfInfluence(templateSkill.getScopeOfInfluence());
            return damageSkill;
        } else if (isInRange(SkillRange.treatmentSkill, id)) {
            TreatmentSkill treatmentSkill = new TreatmentSkill();
            TreatmentSkill templateSkill = (TreatmentSkill) skillMap.get(id);
            copySkillBaseInfo(skill, treatmentSkill);
            copySkill(templateSkill, treatmentSkill);
            templateSkill.setRangeTreatment(treatmentSkill.getIsRangeTreatment());
            templateSkill.setTreatmentAmount(templateSkill.getTreatmentAmount());
            return treatmentSkill;
        }
        return null;
    }

    public void copySkillBaseInfo(Skill template, Skill need2Copy) {
        need2Copy.setId(template.getId());
        need2Copy.setPlayerId(template.getPlayerId());
        need2Copy.setUuid(template.getUuid());
        need2Copy.setLevel(template.getLevel());
        need2Copy.setLastUseTime(template.getLastUseTime());
    }

    public void copySkill(Skill template, Skill need2Copy) {
        need2Copy.setBuff(template.getBuff());
        need2Copy.setName(template.getName());
        need2Copy.setAttackAnimation(template.getAttackAnimation());
        need2Copy.setBuffId(template.getBuffId());
        need2Copy.setCd(template.getCd());
        need2Copy.setDescription(template.getDescription());
        need2Copy.setRequireMagicPoint(template.getRequireMagicPoint());
        need2Copy.setSkillDistance(template.getSkillDistance());
        if(template.getBuff() != null) {
            need2Copy.setBuff(template.getBuff());
            need2Copy.setBuffId(template.getId());
        }
    }

    public boolean isInRange(SkillRange skillRange, int skillId) {
        return (skillId >= skillRange.getStart() && skillId <= skillRange.getEnd());
    }
}
