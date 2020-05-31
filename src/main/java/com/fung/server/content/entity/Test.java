package com.fung.server.content.entity;

import com.fung.server.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/5/26 11:18
 */
public class Test implements Model {

    private int id;

    private String name;

    private int power;

    private int defend;

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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }
}
