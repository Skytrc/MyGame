package com.fung.server.gameserver.content.config.good;

import com.fung.server.gameserver.content.domain.good.GoodBaseInfo;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 10:30
 */
public class Medicine extends Good implements Model, GoodBaseInfo {

    /**
     * 增加血量
     */
    private int plusHp;

    /**
     * 增加蓝量
     */
    private int plusMp;

    /**
     * 增加攻击力
     */
    private int plusAttack;

    /**
     * 增加防御力
     */
    private int plusDefense;

    /**
     * 持续buff时间
     * 持续X秒，每秒增加
     */
    private int lastTime;

    /**
     * 结束buff时间
     * 结束时间
     */
    private int endTime;

    /**
     * 价值
     */
    private int value;

    /**
     * 最大堆叠数量
     */
    private int maxStacks;

    /**
     * 药物持续时间
     */
    private int time;

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

    public int getPlusAttack() {
        return plusAttack;
    }

    public void setPlusAttack(int plusAttack) {
        this.plusAttack = plusAttack;
    }

    public int getPlusDefense() {
        return plusDefense;
    }

    public void setPlusDefense(int plusDefense) {
        this.plusDefense = plusDefense;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
