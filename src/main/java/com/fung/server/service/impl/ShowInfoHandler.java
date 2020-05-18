package com.fung.server.service.impl;

import com.fung.server.content.entity.GameMap;
import com.fung.server.service.BaseInstructionHandler;
import com.fung.server.util.maputil.MapInfoUtil;
import com.fung.server.util.playerutil.OnlinePlayer;
import com.fung.server.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 18:19
 */
@Component
public class ShowInfoHandler extends BaseInstructionHandler {

    @Autowired
    OnlinePlayer onlinePlayer;

    @Autowired
    PlayerUtil playerUtil;

    @Autowired
    MapInfoUtil mapInfoUtil;

    @Override
    public String handler(List<String> ins) {
        if (onlinePlayer.getPlayerMap().get(getChannelId()) == null) {
            return "用户尚未登录";
        }
        String i = ins.remove(0);
        switch (i) {
            case "player" : return showPlayer();
            case "map" : return showMapElement();
            case "online" : return onlinePlayer();
            default: return "展示指令错误";
        }
    }

    /**
     * 展示玩家信息
     * @return 玩家信息（String）
     */
    public String showPlayer() {
        return playerUtil.showPlayerInfo(playerUtil.getCurrentPlayer(getChannelId()));
    }

    /**
     * 展现地图在线玩家信息
     * @return 地图在线玩家信息（String）
     */
    public String onlinePlayer() {
        GameMap gameMap = playerUtil.getCurrentPlayerMap(getChannelId());
        return mapInfoUtil.showMapOnlinePlayer(gameMap);
    }

    /**
     * 展示地图上的元素信息
     * @return 地图元素（String）
     */
    public String showMapElement() {
        GameMap gameMap = playerUtil.getCurrentPlayerMap(getChannelId());
        return mapInfoUtil.showMapInfo(gameMap);
    }
}
