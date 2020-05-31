package com.fung.server.content.util.playerutil;

import com.fung.server.content.entity.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于记录服务器在线玩家
 * @author skytrc@163.com
 * @date 2020/5/13 15:08
 */
@Component
public class OnlinePlayer {

    /**
     * key channel id   value 玩家实体
     */
    private Map<String, Player> onlinePlayerMap;

    public void init() {
        onlinePlayerMap = new HashMap<>();
    }

    public Player getPlayerByChannelId(String channel){
        return onlinePlayerMap.get(channel);
    }

    public Map<String, Player> getPlayerMap() {
        return onlinePlayerMap;
    }

    public void setPlayerMap(Map<String, Player> playerMap) {
        this.onlinePlayerMap = playerMap;
    }
}
