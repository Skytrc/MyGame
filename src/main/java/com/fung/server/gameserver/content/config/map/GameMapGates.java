package com.fung.server.gameserver.content.config.map;

import com.fung.server.gameserver.excel2class.Model;

/**
 * 地图对应传送门
 * @author skytrc@163.com
 * @date 2020/5/28 11:47
 */
public class GameMapGates implements Model {

    private int id;

    private int thisMapId;

    private int nextMapId;

    private int location;

    @Override
    public int getId() {
        return id;
    }

    public int getThisMapId() {
        return thisMapId;
    }

    public int getNextMapId() {
        return nextMapId;
    }

    public int getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThisMapId(int thisMapId) {
        this.thisMapId = thisMapId;
    }

    public void setNextMapId(int nextMapId) {
        this.nextMapId = nextMapId;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
