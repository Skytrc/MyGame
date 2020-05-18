package com.fung.server.util.playerutil;

import com.fung.server.content.MapManager;
import com.fung.server.content.entity.GameMap;
import com.fung.server.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 20:59
 */
@Component
public class PlayerUtil {

    @Autowired
    private MapManager mapManager;

    @Autowired
    private OnlinePlayer onlinePlayer;

    /**
     * @param channelId 客户端channel id
     * @return 当前用户所在的地图
     */
    public GameMap getCurrentPlayerMap(String channelId) {
        return mapManager.getMapByMapId(onlinePlayer.getPlayerByChannelId(channelId).getInMapId());
    }

    public GameMap getGameMapById(int gameMapId) {
        return mapManager.getMapByMapId(gameMapId);
    }

    public Player getCurrentPlayer(String channelId) {
        return onlinePlayer.getPlayerByChannelId(channelId);
    }

    public String showPlayerInfo(Player player) {
        return "玩家姓名：" + player.getPlayerName() + "  玩家id：" + player.getId() + "  玩家目前血量："
                + player.getHitPoint() + "  玩家最大血量：" + player.getMaxHitPoint() + "  玩家所在坐标：["
                + player.getInMapY() + "," + player.getInMapY() + "]" + "  玩家所在地图："
                + getGameMapById(player.getInMapId()).getName();
    }
}
