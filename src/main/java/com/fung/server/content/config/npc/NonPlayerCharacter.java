package com.fung.server.content.config.npc;

import com.fung.server.content.entity.base.BaseElement;
import com.fung.server.excel2class.Model;

import java.util.Map;

/**
 * NPC
 * @author skytrc@163.com
 * @date 2020/5/28 15:40
 */
public class NonPlayerCharacter extends BaseElement implements Model {

    /**
     * npc等级
     */
    private int level;

    /**
     * npcHp
     */
    private int health;

    /**
     * npc魔法值
     */
    private int magic;

    /**
     * npc对话选项
     */
    private Map<Integer, NpcOption> npcOptionMap;

    public NonPlayerCharacter() {
        this.setFriendly(true);
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMagic() {
        return magic;
    }

    public Map<Integer, NpcOption> getNpcOptionMap() {
        return npcOptionMap;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setNpcOptionMap(Map<Integer, NpcOption> npcOptionMap) {
        this.npcOptionMap = npcOptionMap;
    }
}
