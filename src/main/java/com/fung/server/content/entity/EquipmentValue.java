package com.fung.server.content.entity;

import com.fung.server.content.domain.EquipmentType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 武器
 * @author skytrc@163.com
 * @date 2020/5/28 16:34
 */
@Entity
@Table(name = "equipment_value")
public class EquipmentValue {

    /**
     * 唯一id
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    /**
     * 强化等级
     */
    private int level;

    /**
     * 武器类型
     */
    @Transient
    private EquipmentType type;

    /**
     * 增加攻击力
     */
    @Column(name = "plus_health_point")
    private int plusHealthPoint;

    /**
     * 增加魔法值
     */
    @Column(name = "plus_magic_point")
    private int plusMagicPoint;

    /**
     * 增加攻击力
     */
    @Column(name = "plus_attack_power")
    private int plusAttackPower;

    /**
     * 增加魔法力
     */
    @Column(name = "plus_magic_power")
    private int plusMagicPower;

    /**
     * 增加暴击率
     */
    @Column(name = "plus_critical_rate")
    private int plusCriticalRate;

    /**
     * 增加防御力
     */
    @Column(name = "plus_defense")
    private int plusDefense;

    /**
     * 词条
     */
    @Transient
    private List<Integer> entries;

    /**
     * 词条对应的号码（转为Json格式）
     */
    @Column(name = "entries_num")
    private String entriesNum;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public int getPlusHealthPoint() {
        return plusHealthPoint;
    }

    public void setPlusHealthPoint(int plusHealthPoint) {
        this.plusHealthPoint = plusHealthPoint;
    }

    public int getPlusMagicPoint() {
        return plusMagicPoint;
    }

    public void setPlusMagicPoint(int plusMagicPoint) {
        this.plusMagicPoint = plusMagicPoint;
    }

    public int getPlusAttackPower() {
        return plusAttackPower;
    }

    public void setPlusAttackPower(int plusAttackPower) {
        this.plusAttackPower = plusAttackPower;
    }

    public int getPlusMagicPower() {
        return plusMagicPower;
    }

    public void setPlusMagicPower(int plusMagicPower) {
        this.plusMagicPower = plusMagicPower;
    }

    public int getPlusCriticalRate() {
        return plusCriticalRate;
    }

    public void setPlusCriticalRate(int plusCriticalRate) {
        this.plusCriticalRate = plusCriticalRate;
    }

    public int getPlusDefense() {
        return plusDefense;
    }

    public void setPlusDefense(int plusDefense) {
        this.plusDefense = plusDefense;
    }

    public List<Integer> getEntries() {
        return entries;
    }

    public void setEntries(List<Integer> entries) {
        this.entries = entries;
    }

    public String getEntriesNum() {
        return entriesNum;
    }

    public void setEntriesNum(String entriesNum) {
        this.entriesNum = entriesNum;
    }
}
