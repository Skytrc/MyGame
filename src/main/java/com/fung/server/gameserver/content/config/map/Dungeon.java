package com.fung.server.gameserver.content.config.map;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 17:15
 **/
public class Dungeon extends GameMap{

    private String uuid;

    private int beforeMapId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBeforeMapId() {
        return beforeMapId;
    }

    public void setBeforeMapId(int beforeMapId) {
        this.beforeMapId = beforeMapId;
    }
}
