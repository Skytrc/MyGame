package com.fung.server.gameserver.content.config.skill;

import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:17
 */
public class DamageSkill extends Skill implements Model {

    private String name;

    /**
     * 基础物理攻击
     */
    private int physicalDamage;

    /**
     * 基础魔法攻击
     */
    private int magicDamage;

    /**
     * 技能cd
     */
    private int cd;

    /**
     * 技能描述
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
