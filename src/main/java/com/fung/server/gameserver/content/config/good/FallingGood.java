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

    public String getFallingGoodBaseInfo() {
        return "\n物品: " + good.getName() + "  物品数量  " + good.getQuantity() +
                " 在地图: [ " + getInMapX() + "," + getInMapY() +"]";
    }
}
