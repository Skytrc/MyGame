package com.fung.server.gameserver.content.domain.player;

/**
 * TODO 下线检测机制
 * 玩家临时转态，下线后消除
 * @author skytrc@163.com
 * @date 2020/7/3 14:58
 */
public class PlayerTempStatus {

    private String teamId;

    private String dungeonId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }
}
