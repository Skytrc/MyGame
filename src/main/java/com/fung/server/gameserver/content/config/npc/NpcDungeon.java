package com.fung.server.gameserver.content.config.npc;

import com.fung.server.gameserver.excel2class.Model;

public class NpcDungeon implements Model {

    private int id;

    private int npcId;

    private int dungeonId;

    public int getId() {
        return id;
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

    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) {
        this.dungeonId = dungeonId;
    }
}
