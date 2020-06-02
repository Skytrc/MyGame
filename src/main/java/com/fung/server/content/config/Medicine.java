package com.fung.server.content.config;

import com.fung.server.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:30
 */
public class Medicine implements Model {

    private int id;

    private String name;

    private int plusHp;

    private int plusMp;

    /**
     * 最大堆叠数量
     */
    private int maxStacks;

    /**
     * 药物持续时间
     */
    private int time;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getMaxStacks() {
        return maxStacks;
    }

    public void setMaxStacks(int maxStacks) {
        this.maxStacks = maxStacks;
    }
}
