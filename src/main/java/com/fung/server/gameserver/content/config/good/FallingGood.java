package com.fung.server.gameserver.content.config.good;

import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.base.BaseElement;

/**
 * 物品掉落到地图时，封装一层FallingGood
 * @author skytrc@163.com
 * @date 2020/7/9 15:15
 */
public class FallingGood extends BaseElement {

    private Good good;

    private Player beingToPlayer;

    private long gettingTime;

    public FallingGood(Builder builder) {
        this.good = builder.good;
        this.setName(this.good.getName());
        this.setInMapId(builder.inMapX);
        this.setInMapId(builder.inMapId);
        this.setInMapY(builder.inMapY);
        this.beingToPlayer = builder.beingToPlayer;
        this.gettingTime = System.currentTimeMillis();
        this.setFriendly(true);
    }

    public String getFallingGoodBaseInfo() {
        return "\n物品: " + good.getName() + "  物品数量  " + good.getQuantity() +
                " 在地图: [ " + getInMapX() + "," + getInMapY() +"]";
    }

    public static class Builder {

        private final Good good;

        private final int inMapX;

        private final int inMapY;

        private final int inMapId;

        private Player beingToPlayer;

        public Builder(Good good, int inMapX, int inMapY, int inMapId) {
            this.good = good;
            this.inMapX = inMapX;
            this.inMapY = inMapY;
            this.inMapId = inMapId;
        }

        public Builder beingToPlayer(Player beingToPlayer) {
            this.beingToPlayer = beingToPlayer;
            return this;
        }

        public FallingGood build(){
            return new FallingGood(this);
        }

    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Player getBeingToPlayer() {
        return beingToPlayer;
    }

    public void setBeingToPlayer(Player beingToPlayer) {
        this.beingToPlayer = beingToPlayer;
    }

    public long getGettingTime() {
        return gettingTime;
    }

    public void setGettingTime(long gettingTime) {
        this.gettingTime = gettingTime;
    }
}
