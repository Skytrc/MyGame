package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 11:56
 */
@Entity
@Table(name = "player_config")
public class PlayerCommConfig {

    @Transient
    public static int playerBodyEquipmentNum = 5;

    @Id
    private String uuid;

    @Column(name = "max_backpack_grid")
    private int maxBackpackGrid;

    private int money;

    /**
     * 增加钱
     */
    public int addMoney(int addMoney) {
        money += addMoney;
        return money;
    }

    public int minusMoney(int minusMoney) {
        money -= minusMoney;
        return money;
    }

    public int getMaxBackpackGrid() {
        return maxBackpackGrid;
    }

    public void setMaxBackpackGrid(int maxBackpackGrid) {
        this.maxBackpackGrid = maxBackpackGrid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
