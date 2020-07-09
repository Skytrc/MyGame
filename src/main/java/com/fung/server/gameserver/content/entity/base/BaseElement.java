package com.fung.server.gameserver.content.entity.base;

/**
 * 基础元素属性
 * @author skytrc@163.com
 * @date 2020/5/14 10:39
 */
public abstract class BaseElement {

    /**
     * id
     */
    private int id;

    /**
     * 名字
     */
    private String name;

    private int inMapId;

    private int inMapX;

    private int inMapY;

    /**
     * 是否友善生物
     */
    private boolean friendly;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {

        this.friendly = friendly;
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
