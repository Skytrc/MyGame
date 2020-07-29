package com.fung.server.gameserver.content.config.good.equipment;

import com.fung.server.gameserver.content.domain.good.GoodBaseInfo;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.excel2class.Model;

/**
 * 记录生成装备属
 * @author skytrc@163.com
 * @date 2020/6/1 13:34
 */
public class EquipmentCreated extends Good implements Model, GoodBaseInfo {

    private int id;
    /**
     * 装备类型
     */
    private String type;

    /**
     * 耐久度
     */
    private int durable;

    /**
     * 使用物品最低等级
     */
    private int minLevel;

    private int minHp;

    private int maxHp;

    private int minMp;

    private int maxMp;

    /**
     * 最小攻击力
     */
    private int minPower;

    /**
     * 最大攻击力
     */
    private int maxPower;

    private int minMagicPower;

    private int maxMagicPower;

    /**
     * 装备最小暴击率
     */
    private float minCriticalRate;

    /**
     * 装备最大暴击率
     */
    private float maxCriticalRate;

    private int minDefense;

    private int maxDefense;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinHp() {
        return minHp;
    }

    public void setMinHp(int minHp) {
        this.minHp = minHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMinMp() {
        return minMp;
    }

    public void setMinMp(int minMp) {
        this.minMp = minMp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getMinPower() {
        return minPower;
    }

    public void setMinPower(int minPower) {
        this.minPower = minPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getMinMagicPower() {
        return minMagicPower;
    }

    public void setMinMagicPower(int minMagicPower) {
        this.minMagicPower = minMagicPower;
    }

    public int getMaxMagicPower() {
        return maxMagicPower;
    }

    public void setMaxMagicPower(int maxMagicPower) {
        this.maxMagicPower = maxMagicPower;
    }

    public float getMinCriticalRate() {
        return minCriticalRate;
    }

    public void setMinCriticalRate(float minCriticalRate) {
        this.minCriticalRate = minCriticalRate;
    }

    public float getMaxCriticalRate() {
        return maxCriticalRate;
    }

    public void setMaxCriticalRate(float maxCriticalRate) {
        this.maxCriticalRate = maxCriticalRate;
    }

    public int getMinDefense() {
        return minDefense;
    }

    public void setMinDefense(int minDefense) {
        this.minDefense = minDefense;
    }

    public int getMaxDefense() {
        return maxDefense;
    }

    public void setMaxDefense(int maxDefense) {
        this.maxDefense = maxDefense;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    @Override
    public int getQuantity() {
        return 1;
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    public int getDurable() {
        return durable;
    }

    public void setDurable(int durable) {
        this.durable = durable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
