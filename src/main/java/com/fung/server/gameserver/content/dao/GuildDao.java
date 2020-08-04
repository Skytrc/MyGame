package com.fung.server.gameserver.content.dao;

import com.fung.server.gameserver.content.entity.guild.Guild;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 10:41
 */
public interface GuildDao {

    void insertOrUpdateGuild(Guild guild);

    void deleteGuild(Guild guild);

    Guild selectGuildById(String guildId);

    List<Guild> selectAllGuild();
}
