package com.fung.server.content.util.maputil;

import com.fung.server.content.entity.base.BaseElement;
import com.fung.server.content.config.GameMap;
import com.fung.server.content.entity.Player;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/15 11:53
 */
@Component
public class MapInfoUtil {

    /** 展示地图上的元素
     * @param gameMap 游戏地图实体
     * @return 地图上的元素（String）
     */
    public String showMapInfo(GameMap gameMap) {
        Map<Integer, BaseElement> elements = gameMap.getElements();
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Integer, BaseElement> entry : elements.entrySet()) {
            Integer location = entry.getKey();
            String elementName = entry.getValue().getName();
            int[] xy = gameMap.location2xy(location);
            res.append("位置 [").append(xy[0]).append(",").append(xy[1]).append("]")
                    .append(" 拥有 ").append(elementName).append("\n");
        }
        return res.toString();
    }

    /**
     * 展示地图上在线的游戏玩家
     * @param gameMap 地图实体
     * @return 该地图上在线的游戏玩家
     */
    public String showMapOnlinePlayer(GameMap gameMap) {
        StringBuilder res = new StringBuilder("地图: " + gameMap.getName() + " 有玩家" );
        for (Map.Entry<String, Player> entry: gameMap.getMapPlayers().entrySet()) {
            res.append(entry.getValue().getUuid()).append(" 、");
        }
        return res.toString();
    }

    /**
     * 展示地图给出某一格的元素
     * @param gameMap 游戏地图实体
     * @param x 纵坐标
     * @param y 横坐标
     * @return 给出格数的元素
     */
    public String showThisGridInfo(GameMap gameMap, int x, int y) {
        return "[" + x + "," + y + "]" + " 拥有 " + gameMap.getElements().get(gameMap.xy2Location(x, y));
    }
}
