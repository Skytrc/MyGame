package com.fung.server.content.util.playerutil;

import com.fung.server.content.config.manager.MapManager;
import com.fung.server.content.config.map.GameMap;
import com.fung.server.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

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
        return showPlayerBase(player) + showPlayerValue(player) + showPlayerLocation(player);
    }

    public String showPlayerBase(Player player) {
        return "玩家姓名: " + player.getPlayerName() + " 玩家id: " + player.getUuid() + "\n";
    }

    public String showPlayerValue(Player player) {
        return " 等级: " + player.getLevel() + " 当前血量: " + player.getHealthPoint() + " 最大血量: " +player.getMaxHealthPoint()
                + " 当前魔法值: " + player.getMagicPoint() + " 最大魔法值: " + player.getMaxMagicPoint() + " 攻击力: "
                + player.getAttackPower() + " 魔法力: " + player.getMagicPower() + " 暴击率: " + player.getCriticalRate()
                + " 防御力: " + player.getDefense() + "\n";
    }

    public String showPlayerLocation(Player player) {
        return " 所在地图：" + getGameMapById(player.getInMapId()).getName() + "  所在坐标：["
                + player.getInMapY() + "," + player.getInMapY() + "]" + "\n";
    }

    public String showPlayerDate(Player player) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return " 玩家创建时间: " + dateFormat.format(player.getCreatedDate()) + " 玩家最后一次登录: "
                + dateFormat.format(player.getLoginDate());
    }
}
