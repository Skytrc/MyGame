package com.fung.server.content.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 11:45
 */
@Entity
@Table(name = "player")
public class Player {

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 角色名
     */
    @Column(name = "player_name")
    private String playerName;

    /**
     * 角色密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 角色创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * 角色最后上线日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    /**
     * 角色最大血量
     */
    @Column(name = "max_hit_point")
    private int maxHitPoint;

    /**
     * 角色目前血量
     */
    @Column(name = "hit_point")
    private int hitPoint;

    /**
     * 角色状态 0死亡， 1存活
     */
    @Column(name = "status")
    private int status;

    /**
     * 玩家当前所在地图的Id
     */
    @Column(name = "in_map_id")
    private int inMapId;

    /**
     * x坐標
     */
    @Column(name = "in_map_x")
    private int inMapX;

    /**
     * y坐標
     */
    @Column(name = "in_map_y")
    private int inMapY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public void setMaxHitPoint(int maxHitPoint) {
        this.maxHitPoint = maxHitPoint;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInMapId() {
        return inMapId;
    }

    public void setInMapId(int inMapId) {
        this.inMapId = inMapId;
    }

    public int getInMapX() {
        return inMapX;
    }

    public void setInMapX(int inMapX) {
        this.inMapX = inMapX;
    }

    public int getInMapY() {
        return inMapY;
    }

    public void setInMapY(int inMapY) {
        this.inMapY = inMapY;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
