package com.fung.server.gameserver.content.config.map;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 17:15
 **/
public class Dungeon extends GameMap{

    private String uuid;

    private int beforeMap;

    private int beforeX;

    private int beforeY;

    public int getBeforeMap() {
        return beforeMap;
    }

    public void setBeforeMap(int beforeMap) {
        this.beforeMap = beforeMap;
    }

    public int getBeforeX() {
        return beforeX;
    }

    public void setBeforeX(int beforeX) {
        this.beforeX = beforeX;
    }

    public int getBeforeY() {
        return beforeY;
    }

    public void setBeforeY(int beforeY) {
        this.beforeY = beforeY;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
