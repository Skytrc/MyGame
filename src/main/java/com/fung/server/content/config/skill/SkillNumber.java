package com.fung.server.content.config.skill;

/**
 * @author skytrc@163.com
 * @date 2020/6/8 17:18
 */
public enum SkillNumber {

    // 各类技能ID范围

    DAMAGE_SKILL_START(0),

    DAMAGE_SKILL_END(100)
    ;

    private int position;

    SkillNumber(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
