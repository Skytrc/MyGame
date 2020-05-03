package com.fung.server.content.player;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 11:45
 */
public class Player {

    /**
     * 角色id
     */
    private int id;

    /**
     * 角色名
     */
    private String playerName;

    /**
     * 角色密码
     */
    private String password;

    /**
     * 角色最大血量
     */
    private int maxHitPoint;

    /**
     * 角色目前血量
     */
    private int hitPoint;

    /**
     * 角色状态 0死亡， 1存活
     */
    private int status;

    /**
     * 玩家当前所在地图的Id
     */
    private int inMapId;

    /**
     * 玩家当前地图坐标
     */
    private int[][] coordinate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String username) {
        this.playerName = username;
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

    public int[][] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int[][] coordinate) {
        this.coordinate = coordinate;
    }
}
