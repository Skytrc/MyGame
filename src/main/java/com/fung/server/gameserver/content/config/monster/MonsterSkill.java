package com.fung.server.gameserver.content.config.monster;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 12:03
 */
public class MonsterSkill implements Model {

    private int id;

    private int monsterId;

    private String skillId;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }
}
