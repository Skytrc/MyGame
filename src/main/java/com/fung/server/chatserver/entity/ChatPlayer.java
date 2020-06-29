package com.fung.server.chatserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 12:12
 */
@Entity
@Table(name = "player")
public class ChatPlayer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String uuid;

    /**
     * 玩家名字
     */
    @Column(name = "player_name")
    private String playerName;

    private String password;

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
}
