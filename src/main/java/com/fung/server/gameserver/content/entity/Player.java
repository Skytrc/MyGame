package com.fung.server.gameserver.content.entity;

import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.buff.UnitBuffManager;
import com.fung.server.gameserver.content.domain.email.MailBox;
import com.fung.server.gameserver.content.domain.player.PlayerTempStatus;
import com.fung.server.gameserver.content.domain.skill.UnitSkillManager;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 玩家
 * @author skytrc@163.com
 * @date 2020/4/30 11:45
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "player")
public class Player implements Unit{

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
    private int status;

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
     * 计算所有加成后的血量
     */
    @Transient
    private int totalHealthPoint;

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
     * 计算所有加成后的魔法值
     */
    @Transient
    private int totalMagicPoint;

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
     * 计算所有加成后的攻击力
     */
    @Transient
    private int totalAttackPower;

    /**
     * 魔法力
     */
    @Column(name = "magic_power")
    private int magicPower;

    /**
     * 计算所有加成后的魔法力
     */
    @Transient
    private int totalMagicPower;

    /**
     * 暴击率
     */
    @Column(name = "critical_rate")
    private float criticalRate;

    /**
     * 计算所有加成后的暴击率
     */
    @Transient
    private float totalCriticalRate;

    /**
     * 防御力
     */
    @Column(name = "defense")
    private int defense;

    /**
     * 计算所有加成后的防御力
     */
    @Transient
    private int totalDefense;

    /**
     * 攻击距离 默认为1
     */
    @Transient
    private int attackDistance = 1;

    /**
     * 移动速度，默认一秒一格
     */
    @Transient
    private int moveSpeed = 1;

    /**
     * 人物经验
     */
    private int exp;

    /**
     * 人物装备
     */
    @Transient
    private List<Equipment> equipments;

    /**
     * 人物技能
     */
    @Transient
    private UnitSkillManager skillManager;

    /**
     * buff状态管理
     */
    @Transient
    private UnitBuffManager buffManager;

    /**
     * 背包挂钩，需要在Service层处理
     */
    @Transient
    private PersonalBackpack personalBackpack;

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
     * 是否为游戏管理员
     */
    @Column(name = "is_gm")
    private boolean isGm;

    /**
     * 角色最后上线日期
     */
    @Column(name = "login_date")
    private long loginDate;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "player_common_config")
    private PlayerCommConfig playerCommConfig;

    /**
     * 是否正在攻击
     */
    @Transient
    private volatile boolean isAttacking;

    @Transient
    private PlayerTempStatus tempStatus;

    /**
     * 个人邮箱
     */
    @Transient
    private MailBox mailBox;

    public int addExp(int addExp) {
        exp += addExp;
        return exp;
    }

    public Player() {
        tempStatus = new PlayerTempStatus();
    }

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

    public void setStatus(int status) {
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

    @Override
    public int getHealthPoint() {
        return healthPoint;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
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

    @Override
    public int getInMapId() {
        return inMapId;
    }

    public void setInMapId(int inMapId) {
        this.inMapId = inMapId;
    }

    @Override
    public int getInMapX() {
        return inMapX;
    }

    public void setInMapX(int inMapX) {
        this.inMapX = inMapX;
    }

    @Override
    public int getInMapY() {
        return inMapY;
    }

    @Override
    public int getTempX() {
        return inMapX;
    }

    @Override
    public int getTempY() {
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

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public PersonalBackpack getPersonalBackpack() {
        return personalBackpack;
    }

    public void setPersonalBackpack(PersonalBackpack personalBackpack) {
        this.personalBackpack = personalBackpack;
    }

    public int getTotalHealthPoint() {
        return totalHealthPoint;
    }

    public void setTotalHealthPoint(int totalHealthPoint) {
        this.totalHealthPoint = totalHealthPoint;
    }

    public int getTotalMagicPoint() {
        return totalMagicPoint;
    }

    public void setTotalMagicPoint(int totalMagicPoint) {
        this.totalMagicPoint = totalMagicPoint;
    }

    public int getTotalAttackPower() {
        return totalAttackPower;
    }

    public void setTotalAttackPower(int totalAttackPower) {
        this.totalAttackPower = totalAttackPower;
    }

    public int getTotalMagicPower() {
        return totalMagicPower;
    }

    public void setTotalMagicPower(int totalMagicPower) {
        this.totalMagicPower = totalMagicPower;
    }

    public float getTotalCriticalRate() {
        return totalCriticalRate;
    }

    public void setTotalCriticalRate(float totalCriticalRate) {
        this.totalCriticalRate = totalCriticalRate;
    }

    public int getTotalDefense() {
        return totalDefense;
    }

    public void setTotalDefense(int totalDefense) {
        this.totalDefense = totalDefense;
    }

    public PlayerCommConfig getPlayerCommConfig() {
        return playerCommConfig;
    }

    public void setPlayerCommConfig(PlayerCommConfig playerCommConfig) {
        this.playerCommConfig = playerCommConfig;
    }

    @Override
    public int getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(int attackDistance) {
        this.attackDistance = attackDistance;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public PlayerTempStatus getTempStatus() {
        return tempStatus;
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    public boolean isGm() {
        return isGm;
    }

    public void setGm(boolean gm) {
        isGm = gm;
    }

    public UnitSkillManager getSkillManager() {
        return skillManager;
    }

    public void setSkillManager(UnitSkillManager skillManager) {
        this.skillManager = skillManager;
    }

    public UnitBuffManager getBuffManager() {
        return buffManager;
    }

    public void setBuffManager(UnitBuffManager buffManager) {
        this.buffManager = buffManager;
    }
}
