package com.fung.server.gameserver.content.entity.guild;

import com.fung.server.gameserver.content.domain.guild.GuildPosition;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 玩家在公会的个人信息
 * @author skytrc@163.com
 * @date 2020/7/31 12:14
 */
@Entity(name = "guild_member")
public class GuildMember {

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "guild_id")
    private String guildId;

    @Column(name = "personal_contribution")
    private int personalContribution;

    @Column(name = "current_contribution")
    private int currentContribution;

    @Column(name = "join_in_time")
    private long joinInTime;

    @Column(name = "guild_position")
    private GuildPosition guildPosition;

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

    public int getPersonalContribution() {
        return personalContribution;
    }

    public void setPersonalContribution(int personalContribution) {
        this.personalContribution = personalContribution;
    }

    public int getCurrentContribution() {
        return currentContribution;
    }

    public void setCurrentContribution(int currentContribution) {
        this.currentContribution = currentContribution;
    }

    public GuildPosition getGuildPosition() {
        return guildPosition;
    }

    public void setGuildPosition(GuildPosition guildPosition) {
        this.guildPosition = guildPosition;
    }

    public long getJoinInTime() {
        return joinInTime;
    }

    public void setJoinInTime(long joinInTime) {
        this.joinInTime = joinInTime;
    }
}
