package com.fung.server.gameserver.content.config.npc;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 10:51
 */
public class NpcShop implements Model {

    private int id;

    private int npcId;

    private String goodsId;

    @Override
    public int getId() {
        return id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

}
