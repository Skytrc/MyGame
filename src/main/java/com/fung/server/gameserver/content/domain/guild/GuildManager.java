package com.fung.server.gameserver.content.domain.guild;

import com.fung.server.gameserver.content.entity.guild.Guild;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO 需要定时排序公会
 * @author skytrc@163.com
 * @date 2020/7/31 12:06
 */
@Component
public class GuildManager {


    /**
     * key guild uuid -> value guild
     */
    private Map<String, Guild> guildMap;

    /**
     * 已排列好的公会顺序
     */
    private List<Guild> arrayedGuild;

    private GuildActor guildActor;

    public GuildManager() {
        guildMap = new HashMap<>();
        guildActor = new GuildActor();
    }

    public List<Guild> getArrayedGuild() {
        if (arrayedGuild == null) {
            newArrayedGuild();
            sortGuild();
        }
        return arrayedGuild;
    }

    public void newArrayedGuild() {
        arrayedGuild = new ArrayList<>(guildMap.values());
    }

    /**
     * 公会排序 根据公会等级、经验排序
     */
    public void sortGuild() {
        arrayedGuild.sort((o1, o2) -> {
            if (o1.getGuildRank() == o2.getGuildRank()) {
                return o1.getGuildContribution() - o2.getGuildContribution();
            }
            return o1.getGuildRank() - o2.getGuildRank();
        });
    }

    public Guild getGuildById(String uuid) {
        return guildMap.get(uuid);
    }

    /**
     * 公会初始化，一般用于服务器启动时读取数据库中的公会信息
     */
    public void guildInit(List<Guild> guilds) {
        for (Guild guild : guilds) {
            guildMap.put(guild.getUuid(), guild);
        }
    }

    /**
     * 新增公会（重新排序）
     */
    public void insertNewGuild(Guild guild) {
        guildMap.put(guild.getUuid(), guild);
        sortGuild();
    }

    public Map<String, Guild> getGuildMap() {
        return guildMap;
    }

    public void setGuildMap(Map<String, Guild> guildMap) {
        this.guildMap = guildMap;
    }

    public GuildActor getGuildActor() {
        return guildActor;
    }
}
