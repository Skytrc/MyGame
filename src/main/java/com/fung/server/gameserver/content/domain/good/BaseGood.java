package com.fung.server.gameserver.content.domain.good;

/**
 * 基础物品资料
 * @author skytrc@163.com
 * @date 2020/8/4 11:27
 */
public abstract class BaseGood {

    private String name;

    private int goodId;

    private int value;

    private int maxStack;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
}
