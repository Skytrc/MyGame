package com.fung.server.gameserver.content.config.skill;

import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:17
 */
public class DamageSkill extends Skill implements Model {

    /**
     * 基础物理攻击
     */
    private int physicalDamage;

    /**
     * 基础魔法攻击
     */
    private int magicDamage;

    /**
     * 如果是Aoe技能，最大的影响范围（不是Aoe则为0）
     */
    private int scopeOfInfluence;

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public int getScopeOfInfluence() {
        return scopeOfInfluence;
    }

    public void setScopeOfInfluence(int scopeOfInfluence) {
        this.scopeOfInfluence = scopeOfInfluence;
    }
}
