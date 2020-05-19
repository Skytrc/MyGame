package com.fung.server.content.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 11:45
 */
@Entity
@Table(name = "player")
@AttributeOverrides(
        @AttributeOverride(name = "name", column = @Column(name = "player_name"))
)
public class Player extends BaseCharacter{

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
