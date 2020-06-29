package com.fung.server.gameserver.content.config.good;

import com.fung.server.gameserver.content.domain.good.GoodBaseInfo;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:30
 */
public class Medicine extends Good implements Model, GoodBaseInfo {

    private String name;

    private int plusHp;

    private int plusMp;

    private int value;

    /**
     * 最大堆叠数量
     */
    private int maxStacks;

    /**
     * 药物持续时间
     */
    private int time;

    private String description;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlusHp() {
        return plusHp;
    }

    public void setPlusHp(int plusHp) {
        this.plusHp = plusHp;
    }

    public int getPlusMp() {
        return plusMp;
    }

    public void setPlusMp(int plusMp) {
        this.plusMp = plusMp;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int getMaxStacks() {
        return maxStacks;
    }

    public void setMaxStacks(int maxStacks) {
        this.maxStacks = maxStacks;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getId() {
        return getGoodId();
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
