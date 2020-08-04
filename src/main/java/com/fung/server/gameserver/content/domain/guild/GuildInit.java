package com.fung.server.gameserver.content.domain.guild;

import com.fung.server.gameserver.content.dao.GuildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 11:30
 */
@Component
public class GuildInit {

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private GuildManager guildManager;

    public void guildInit() {
        guildManager.guildInit(guildDao.selectAllGuild());
    }
}
