package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * 技能
 * @author skytrc@163.com
 * @date 2020/5/28 17:32
 */
@Entity
@Table(name = "skill")
public class Skill {

    /**
     * 唯一id
     */
    @Id
    private String uuid;

    /**
     * 对应技能配置表id
     */
    private int id;

    /**
     * 玩家id
     */
    @Column(name = "player_id")
    private String playerId;

    /**
     * 技能等级
     */
    private int level;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
