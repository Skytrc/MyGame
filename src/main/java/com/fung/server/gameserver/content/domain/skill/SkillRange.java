package com.fung.server.gameserver.content.domain.skill;

/**
 * @author skytrc@163.com
 * @date 2020/7/21 17:16
 */
public enum SkillRange {

    /**
     * 伤害技能范围
     */
    damageSkill(1, 100),

    /**
     * 治疗技能范围
     */
    treatmentSkill(101, 200);

    private int start;

    private int end;

    SkillRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
