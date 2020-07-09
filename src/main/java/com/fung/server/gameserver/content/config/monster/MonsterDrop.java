package com.fung.server.gameserver.content.config.monster;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/7/9 16:33
 */
public class MonsterDrop implements Model {

    private int id;

    private int monsterId;

    private int goodId;

    private int minQuantity;

    private int maxQuantity;

    private float dropProbability;

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

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public float getDropProbability() {
        return dropProbability;
    }

    public void setDropProbability(float dropProbability) {
        this.dropProbability = dropProbability;
    }
}
