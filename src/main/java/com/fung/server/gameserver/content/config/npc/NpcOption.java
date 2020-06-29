package com.fung.server.gameserver.content.config.npc;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/5/28 16:14
 */
public class NpcOption implements Model {

    private int id;

    private String name;

    private int optionLocation;

    private int npcId;

    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getOptionLocation() {
        return optionLocation;
    }

    public void setOptionLocation(int optionLocation) {
        this.optionLocation = optionLocation;
    }

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
}
