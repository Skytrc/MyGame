package com.fung.server.gameserver.content.domain.buff;

import com.fung.server.gameserver.content.config.buff.Buff;

/**
 * @author skytrc@163.com
 * @date 2020/7/22 11:38
 */
public enum BuffSpecies {

    /**
     * 动作限制，例如眩晕
     */
    ActionLimit(0, 1, 10),

    /**
     * 移动限制，例如束缚
     */
    MoveLimit(1, 11, 20),

    /**
     * 持续伤害/治疗
     */
    Dot(2, 21, 50),

    /**
     * 护盾类
     */
    Shield(3, 51, 60),
    ;

    /**
     * buff种类id
     */
    private int bufferCategory;

    private int start;

    private int end;

    BuffSpecies(int bufferCategory, int start, int end) {
        this.bufferCategory = bufferCategory;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * 返回buff种类的编号
     * @param buffId buff id
     * @return buff种类编号
     */
    public static int getBufferCategoryByBuffId(int buffId) {
        for (BuffSpecies value : BuffSpecies.values()) {
            if (buffId >= value.getStart() && buffId <= value.getEnd()) {
                return value.getBufferCategory();
            }
        }
        return -1;
    }

    public int getBufferCategory() {
        return bufferCategory;
    }

    public void setBufferCategory(int bufferCategory) {
        this.bufferCategory = bufferCategory;
    }
}
