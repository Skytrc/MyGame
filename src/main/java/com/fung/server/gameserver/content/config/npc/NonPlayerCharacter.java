package com.fung.server.gameserver.content.config.npc;

import com.fung.server.gameserver.content.entity.base.BaseElement;
import com.fung.server.gameserver.excel2class.Model;

import java.util.List;
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
    private int healthPoint;

    /**
     * npc魔法值
     */
    private int magicPoint;

    private int attackPower;

    private int magicPower;

    private List<Integer> goodsId;

    /**
     * npc对话选项
     */
    private Map<Integer, NpcOption> npcOptionMap;

    /**
     * @return 是否有商店功能
     */
    public boolean hasShop() {
        return goodsId != null;
    }

    /**
     * 是否出售某件商品
     */
    public boolean containGood(int goodId) {
        if (goodsId == null){
            return false;
        }
        return goodsId.contains(goodId);
    }

    public NonPlayerCharacter() {
        this.setFriendly(true);
    }

    public int getLevel() {
        return level;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public Map<Integer, NpcOption> getNpcOptionMap() {
        return npcOptionMap;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    public void setNpcOptionMap(Map<Integer, NpcOption> npcOptionMap) {
        this.npcOptionMap = npcOptionMap;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public List<Integer> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(List<Integer> goodsId) {
        this.goodsId = goodsId;
    }
}
