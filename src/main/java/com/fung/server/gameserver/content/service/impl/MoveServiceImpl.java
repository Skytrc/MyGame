package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.domain.calculate.MoveCalculate;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.MoveService;
import com.fung.server.gameserver.content.domain.map.MapInfo;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 11:47
 */
@Component
public class MoveServiceImpl implements MoveService {

    @Autowired
    OnlinePlayer onlinePlayer;

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    MapInfo mapInfo;

    @Autowired
    MoveCalculate moveCalculate;

    @Override
    public String move(int x, int y, String channelId) throws InterruptedException {
        Player player = playerInfo.getCurrentPlayer(channelId);
        GameMap gameMap = playerInfo.getCurrentPlayerMap(channelId);
        int newX = player.getInMapX() + x;
        int newY = player.getInMapY() + y;
        if (newX < 1 || newX > gameMap.getX() || newY < 1 || newY > gameMap.getY()) {
            return "移动超出地图限制";
        }
        moveCalculate.moveLimited(player, channelId, x, y);
        return "移动完成";
    }

    @Override
    public String move(int[] xy, String channelId) throws InterruptedException {
        Player player = playerInfo.getCurrentPlayer(channelId);
        GameMap gameMap = playerInfo.getCurrentPlayerMap(channelId);
        int newX = xy[0];
        int newY = xy[1];
        if (newX < 1 || newX > gameMap.getX() || newY < 1 || newY > gameMap.getY()) {
            return "移动超出地图限制";
        }
        moveCalculate.moveGrid(player, channelId, xy[0], xy[1]);
        return "移动完成";
    }

    @Override
    public String mapTransfer(String channelId) {
        GameMap currentGameMap = playerInfo.getCurrentPlayerMap(channelId);
        Player player = playerInfo.getCurrentPlayer(channelId);
        int nextMapId = currentGameMap.hasGate(player.getInMapX(), player.getInMapY());
        if (nextMapId == -1) {
            return "当前坐标没有传送门";
        }
        player.setInMapId(nextMapId);
        player.setInMapX(1);
        player.setInMapY(1);
        // 更新数据库
        playerCache.updatePlayer(player);
        GameMap nextGameMap = playerInfo.getGameMapById(nextMapId);
        currentGameMap.removePlayer(player);
        nextGameMap.addPlayer(player);
        return "转移成功\n" + mapInfo.showMapInfo(nextGameMap);
    }
}
