package com.fung.server.content.entity;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
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
}
