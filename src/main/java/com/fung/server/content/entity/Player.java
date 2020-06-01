package com.fung.server.content.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 玩家
 * @author skytrc@163.com
 * @date 2020/4/30 11:45
 */
@Entity
@Table(name = "player")
public class Player{

    /**
     * 玩家uuid
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    /**
     * 玩家名字
     */
    @Column(name = "player_name")
    private String playerName;

    /**
     * 角色密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 角色状态 0死亡  1存货
     */
    private byte status;

    /**
     * 等级
     */
    private int level;

    /**
     * 最大血量
     */
    @Column(name = "max_heath_point")
    private int maxHealthPoint;

    /**
     * 当前血量
     */
    @Column(name = "health_point")
    private int healthPoint;

    /**
     * 最大魔法值
     */
    @Column(name = "max_magic_point")
    private int maxMagicPoint;

    /**
     * 当前魔法值
     */
    @Column(name = "magic_point")
    private int magicPoint;

    /**
     * 攻击力
     */
    @Column(name = "attack_power")
    private int attackPower;

    /**
     * 魔法力
     */
    @Column(name = "magic_power")
    private int magicPower;

    /**
     * 暴击率
     */
    @Column(name = "critical_rate")
    private float criticalRate;

    /**
     * 防御力
     */
    @Column(name = "defense")
    private int defense;

    /**
     * 人物经验
     */
    private int exp;

    /**
     * 人物所在地图id
     */
    @Column(name = "in_map_id")
    private int inMapId;

    /**
     * 人物所在地图x轴
     */
    @Column(name = "in_map_x")
    private int inMapX;

    /**
     * 人物所在地图y轴
     */
    @Column(name = "in_map_y")
    private int inMapY;

    /**
     * 角色创建日期
     */
    @Column(name = "create_date")
    private long createdDate;

    /**
     * 角色最后上线日期
     */
    @Column(name = "login_date")
    private long loginDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public void setMaxHealthPoint(int maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getMaxMagicPoint() {
        return maxMagicPoint;
    }

    public void setMaxMagicPoint(int maxMagicPoint) {
        this.maxMagicPoint = maxMagicPoint;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public float getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(float criticalRate) {
        this.criticalRate = criticalRate;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
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

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }
}
