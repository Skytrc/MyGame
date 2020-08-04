package com.fung.server.gameserver.content.entity.guild;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author skytrc@163.com
 * @date 2020/7/31 13:15
 */
@Entity(name = "guild_good")
public class GuildGood {

    @Id
    private String id;

    @Column(name = "guild_id")
    private String guildId;

    @Column(name = "good_id")
    private int goodId;

    @Column(name = "good_quantity")
    private int goodQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getGoodQuantity() {
        return goodQuantity;
    }

    public void setGoodQuantity(int goodQuantity) {
        this.goodQuantity = goodQuantity;
    }
}
