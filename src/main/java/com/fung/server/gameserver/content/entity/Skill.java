package com.fung.server.gameserver.content.entity;

import com.fung.server.gameserver.content.config.buff.Buff;
import org.springframework.beans.factory.annotation.Autowired;

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
     * 上一次使用技能的时间
     */
    private long lastUseTime;

    /**
     * 攻击前摇
     */
    @Transient
    private int attackAnimation;

    /**
     * 蓝量需求
     */
    @Transient
    private int requireMagicPoint;

    /**
     * 技能描述
     */
    @Transient
    private String description;

    /**
     * 技能带有的Buff
     */
    @Transient
    private Buff buff;

    /**
     * 带有的buff id
     */
    @Transient
    private int buffId;

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

    public int getSkillDistance() {
        return skillDistance;
    }

    public void setSkillDistance(int skillDistance) {
        this.skillDistance = skillDistance;
    }

    public int getAttackAnimation() {
        return attackAnimation;
    }

    public void setAttackAnimation(int attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    public int getRequireMagicPoint() {
        return requireMagicPoint;
    }

    public void setRequireMagicPoint(int requireMagicPoint) {
        this.requireMagicPoint = requireMagicPoint;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public long getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(long lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
