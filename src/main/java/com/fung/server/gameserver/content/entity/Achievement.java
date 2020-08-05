package com.fung.server.gameserver.content.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 17:57
 */
@Entity
@Table(name = "achievement")
public class Achievement {

    private int uuid;

    @Column(name = "player_id")
    private int playerId;
}
