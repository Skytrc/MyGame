package com.fung.server.gameserver.content.entity.guild;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/7/31 13:16
 */
@Entity(name = "guild_share_good_record")
public class GuildShareGoodRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "guild_id")
    private String guildId;

    @Column(name = "good_id")
    private int goodId;

    @Column(name = "good_quantity")
    private int goodQuantity;

    @Column(name = "share_time")
    private long shareTime;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    public long getShareTime() {
        return shareTime;
    }

    public void setShareTime(long shareTime) {
        this.shareTime = shareTime;
    }
}
