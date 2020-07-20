package com.fung.server.gameserver.content.config.good;

/**
 * @author skytrc@163.com
 * @date 2020/7/20 12:11
 */
public enum GoodSpecies {

    /**
     * 药品
     */
    MEDICINCE(0, 99),

    /**
     * 装备
     */
    EQUIPMENT(100, 300);

    private int start;

    private int end;

    GoodSpecies(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
