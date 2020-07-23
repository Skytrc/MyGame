package com.fung.server.gameserver.content.config.buff;

import com.fung.server.gameserver.content.domain.buff.BuffSpecies;
import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/7/22 10:25
 */
public class Buff implements Model {

    private int id;

    private String name;

    /**
     * 最大持续时间
     */
    private int maxLastTime;

    /**
     * 当前持续时间
     */
    private int lastTime;

    private BuffSpecies buffSpecies;

    /**
     * 描述
     */
    private String description;

    /**
     * 每秒伤害
     */
    private int damagePerSecond;

    /**
     * 最大层数
     */
    private int maxLayer;

    /**
     * 层数
     */
    private int layer;

    /**
     * 能否进行其他动作（特指其他的攻击动作/移动动作）
     */
    private String canAction;

    /**
     * 能否移动
     */
    private String canMove;

    /**
     * 护盾
     */
    private int shield;

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLastTime() {
        return maxLastTime;
    }

    public void setMaxLastTime(int maxLastTime) {
        this.maxLastTime = maxLastTime;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDamagePerSecond() {
        return damagePerSecond;
    }

    public void setDamagePerSecond(int damagePerSecond) {
        this.damagePerSecond = damagePerSecond;
    }

    public int getMaxLayer() {
        return maxLayer;
    }

    public void setMaxLayer(int maxLayer) {
        this.maxLayer = maxLayer;
    }

    public boolean isCanAction() {
        return "TRUE".equals(canAction);
    }

    public String getCanAction() {
        return this.canAction;
    }

    public void setCanAction(String canAction) {
        this.canAction = canAction;
    }

    public boolean isCanMove() {
        return "TRUE".equals(canMove);
    }

    public void setCanMove(String canMove) {
        this.canMove = canMove;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public BuffSpecies getBuffSpecies() {
        return buffSpecies;
    }

    public void setBuffSpecies(BuffSpecies buffSpecies) {
        this.buffSpecies = buffSpecies;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getCanMove() {
        return this.canMove;
    }
}
