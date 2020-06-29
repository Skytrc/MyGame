package com.fung.server.gameserver.content.config.monster;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 15:54
 */
public class MonsterDistribution implements Model {

    private int id;

    private int monsterId;

    private int inMapId;

    private int inMapX;

    private int inMapY;

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

    public int getInMapId() {
        return inMapId;
    }

    public void setInMapId(int inMapId) {
        this.inMapId = inMapId;
    }

    public int getInMapX() {
        return inMapX;
    }

    public void setInMapX(int inMapX) {
        this.inMapX = inMapX;
    }

    public int getInMapY() {
        return inMapY;
    }

    public void setInMapY(int inMapY) {
        this.inMapY = inMapY;
    }
}
