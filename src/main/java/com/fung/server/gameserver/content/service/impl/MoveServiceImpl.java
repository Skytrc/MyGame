package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.domain.calculate.MoveCalculate;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
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
    private OnlinePlayer onlinePlayer;

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private MapInfo mapInfo;

    @Autowired
    private MoveCalculate moveCalculate;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

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
        GameMapActor gameMapActor = mapManager.getGameMapActor(player);
        gameMapActor.addMessage(gameMapActor1 -> {
            GameMap gameMap = gameMapActor.getGameMap();
            int newX = xy[0];
            int newY = xy[1];
            if (newX < 1 || newX > gameMap.getX() || newY < 1 || newY > gameMap.getY()) {
                writeMessage2Client.writeMessage(channelId, "移动超出地图限制");
            }
            moveCalculate.moveGrid(gameMapActor, player, channelId, xy[0], xy[1]);
        });
        return "";
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
