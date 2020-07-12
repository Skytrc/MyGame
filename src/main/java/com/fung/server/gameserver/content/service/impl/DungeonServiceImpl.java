package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.domain.Dungeon.DungeonManager;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 16:57
 **/
@Component
public class DungeonServiceImpl implements DungeonService {

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private DungeonManager dungeonManager;

    @Override
    public void enterDungeon(String channelId, int dungeonId) {
        Player player = playerInfo.getCurrentPlayer(channelId);

    }

    @Override
    public void leaveDungeon(String channelId) {

    }
}
