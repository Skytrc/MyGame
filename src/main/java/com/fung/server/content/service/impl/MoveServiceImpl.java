package com.fung.server.content.service.impl;

import com.fung.server.cache.mycache.PlayerCache;
import com.fung.server.content.config.GameMap;
import com.fung.server.content.entity.Player;
import com.fung.server.content.service.MoveService;
import com.fung.server.content.util.maputil.MapInfoUtil;
import com.fung.server.content.util.playerutil.OnlinePlayer;
import com.fung.server.content.util.playerutil.PlayerUtil;
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
    PlayerUtil playerUtil;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    MapInfoUtil mapInfoUtil;

    @Override
    public String move(int x, int y, String channelId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMap gameMap = playerUtil.getCurrentPlayerMap(channelId);
        int newX = player.getInMapX() + x;
        int newY = player.getInMapY() + y;
        if (newX < 1 || newX > gameMap.getX() || newY < 1 || newY > gameMap.getY()) {
            return "移动超出地图限制";
        }
        String res = "移动前坐标：[ " + player.getInMapX() + " , " + player.getInMapY()
                + " ]  ";
        player.setInMapX(newX);
        player.setInMapY(newY);
        return res + "移动后坐标：[ " + newX + " , " + newY + "]";
    }

    @Override
    public void move(int[] xy, String channelId) {

    }

    @Override
    public String mapTransfer(String channelId) {
        GameMap currentGameMap = playerUtil.getCurrentPlayerMap(channelId);
        Player player = playerUtil.getCurrentPlayer(channelId);
        int nextMapId = currentGameMap.hasGate(player.getInMapX(), player.getInMapY());
        if (nextMapId == -1) {
            return "当前坐标没有传送门";
        }
        player.setInMapId(nextMapId);
        player.setInMapX(1);
        player.setInMapY(1);
        // 更新数据库
        playerCache.updatePlayer(player);
        GameMap nextGameMap = playerUtil.getGameMapById(nextMapId);
        currentGameMap.removePlayer(player.getUuid());
        nextGameMap.addPlayer(player);
        return "转移成功\n" + mapInfoUtil.showMapInfo(nextGameMap);
    }
}
