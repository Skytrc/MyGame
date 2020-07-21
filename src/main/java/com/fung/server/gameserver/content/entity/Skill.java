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
     * 技能名称
     */
    @Transient
    private String name;

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

    /**
     * 释放技能最大距离
     */
    @Transient
    private int skillDistance;

    /**
     * 技能cd
     */
    @Transient
    private int cd;

    /**
     * 技能描述
     */
    @Transient
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
