package com.fung.server.gameserver.content.config.good;

/**
 * Id范围
 * @author skytrc@163.com
 * @date 2020/6/2 12:13
 * TODO 这里可以用配置取代
 */
public enum GoodNumber {

    // 各类物品Id范围

    MEDICINE_START(0),

    MEDICINE_END(99),

    EQUIPMENT_START(100),

    EQUIPMENT_END(200)
    ;

    private int position;

    GoodNumber(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
