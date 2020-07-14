package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.Dungeon;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.domain.Dungeon.DungeonManager;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
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
    private DungeonManager dungeonManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String enterDungeon(String channelId, int dungeonId) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        GameMapActor mapActor = dungeonManager.playerGotDungeon(dungeonId, player);
        GameMap currentMap = playerInfo.getCurrentPlayerMap(channelId);
        Dungeon nextMap = (Dungeon) mapActor.getGameMap();
        currentMap.removePlayer(player);
        // reset玩家位置
        player.setInMapId(dungeonId);
        player.setInMapX(1);
        player.setInMapY(1);
        nextMap.addPlayer(player);
        // 在临时状态中设置副本uuid
        player.getTempStatus().setDungeonId(nextMap.getUuid());
        return "玩家进入副本: " + nextMap.getName() + " 当前副本ID为: " + nextMap.getUuid();
    }

    @Override
    public String enterDungeon(String channelId, String dungeonUuid) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        GameMapActor mapActor = dungeonManager.getDungeonActorByUuid(dungeonUuid);
        if (mapActor == null) {
            return "";
        }
        GameMap currentMap = playerInfo.getCurrentPlayerMap(player);
        Dungeon dungeon = (Dungeon) mapActor.getGameMap();
        currentMap.removePlayer(player);
        dungeon.addPlayer(player);
        // 在临时状态中设置副本uuid
        player.getTempStatus().setDungeonId(dungeonUuid);
        return dungeon.getName();
    }

    @Override
    public String leaveDungeon(String channelId) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        if (!dungeonManager.playerLeaveDungeon(player)) {
            return "玩家不在副本内";
        }
        return "成功退出副本";
    }
}
