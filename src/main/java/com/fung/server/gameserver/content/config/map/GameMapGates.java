package com.fung.server.gameserver.content.config.map;

import com.fung.server.gameserver.excel2class.Model;

/**
 * 地图对应传送门
 * @author skytrc@163.com
 * @date 2020/5/28 11:47
 */
public class GameMapGates implements Model {

    private int id;

    private int thisMap;

    private int nextMap;

    private int location;

    @Override
    public int getId() {
        return id;
    }

    public int getThisMap() {
        return thisMap;
    }

    public int getNextMap() {
        return nextMap;
    }

    public int getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThisMap(int thisMap) {
        this.thisMap = thisMap;
    }

    public void setNextMap(int nextMap) {
        this.nextMap = nextMap;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
