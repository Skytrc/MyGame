package com.fung.server.gameserver.content.entity.guild;

import com.fung.server.gameserver.content.domain.guild.GuildPosition;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公会
 * @author skytrc@163.com
 * @date 2020/7/31 11:37
 */
@Entity(name = "guild")
public class Guild {

    @Id
    private String uuid;

    @Column(name = "guild_name")
    private String guildName;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "guild_contribution")
    private int guildContribution;

    @Column(name = "guild_rank")
    private int guildRank;

    @Column(name = "guild_money")
    private long guildMoney;

    /**
     * 公会描述
     */
    @Column(name = "guild_description")
    private String guildDescription;

    /**
     * 申请人列表
     */
    @ElementCollection
    @CollectionTable(name = "guild_applicant", joinColumns = @JoinColumn(name = "guild_id"))
    @Column(name = "applicant_list")
    private List<String> applicantList;

    /**
     * key 物品id  value 公会物品信息
     */
    @OneToMany(mappedBy = "Guild", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "guild_good_id")
    private Map<Integer, GuildGood> guildGoodMap;

    /**
     * key 玩家id  value 玩家在公会的信息
     */
    @OneToMany(mappedBy = "Guild", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "guild_member_map")
    private Map<String, GuildMember> guildMemberMap;

    public Guild() {
        // 防止在人员调动/物品捐赠时出现线程问题
        guildGoodMap = new ConcurrentHashMap<>();
        guildMemberMap = new ConcurrentHashMap<>();
    }

    public void addGuildMember(GuildMember guildMember) {
        guildMemberMap.put(guildMember.getPlayerId(), guildMember);
        numberOfPeople++;
    }

    public void removeGuildMember(String playerId) {
        guildMemberMap.remove(playerId);
        numberOfPeople--;
    }

    public List<GuildMember> getAllGuildMember() {
        return new ArrayList<>(guildMemberMap.values());
    }

    /**
     * 获取公会管理员
     */
    public List<GuildMember> getGuildAdministrator() {
        List<GuildMember> list = new ArrayList<>();
        for (GuildMember guildMember : getAllGuildMember()) {
            if (guildMember.getGuildPosition().getPosition() <= GuildPosition.VICE_PRESIDENT.getPosition()) {
                list.add(guildMember);
            }
        }
        return list;
    }

    public GuildMember getGuildMember(String playerId) {
        return guildMemberMap.get(playerId);
    }

    public void addGuildGood(GuildGood guildGood) {
        if (guildGoodMap.containsKey(guildGood.getGoodId())) {
            GuildGood guildGood1 = guildGoodMap.get(guildGood.getGoodId());
            guildGood1.setGoodQuantity(guildGood.getGoodQuantity() + guildGood1.getGoodQuantity());
        } else {
            guildGoodMap.put(guildGood.getGoodId(), guildGood);
        }
    }

    /**
     * 获取公会仓库中的物品，没有或数量为0返回负数，否则返回物品剩余数量
     */
    public int getGuildGood(int guildGoodId, int guildGoodQuantity) {
        if (!guildGoodMap.containsKey(guildGoodId)) {
            return -1;
        }
        GuildGood guildGood = guildGoodMap.get(guildGoodId);
        if (guildGood.getGoodQuantity() - guildGoodQuantity < 0) {
            return -1;
        }
        guildGood.setGoodQuantity(guildGood.getGoodQuantity() - guildGoodQuantity);
        return guildGood.getGoodQuantity();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getGuildContribution() {
        return guildContribution;
    }

    public void setGuildContribution(int guildContribution) {
        this.guildContribution = guildContribution;
    }

    public int getGuildRank() {
        return guildRank;
    }

    public void setGuildRank(int guildRank) {
        this.guildRank = guildRank;
    }

    public long getGuildMoney() {
        return guildMoney;
    }

    public void setGuildMoney(long guildMoney) {
        this.guildMoney = guildMoney;
    }

    public String getGuildDescription() {
        return guildDescription;
    }

    public void setGuildDescription(String guildDescription) {
        this.guildDescription = guildDescription;
    }

    public Map<Integer, GuildGood> getGuildGoodMap() {
        return guildGoodMap;
    }

    public void setGuildGoodMap(Map<Integer, GuildGood> guildGoodMap) {
        this.guildGoodMap = guildGoodMap;
    }

    public List<String> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<String> applicantList) {
        this.applicantList = applicantList;
    }

    public Map<String, GuildMember> getGuildMemberMap() {
        return guildMemberMap;
    }

    public void setGuildMemberMap(Map<String, GuildMember> guildMemberMap) {
        this.guildMemberMap = guildMemberMap;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }
}
