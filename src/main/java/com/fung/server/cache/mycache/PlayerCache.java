package com.fung.server.cache.mycache;

import com.fung.server.content.entity.Player;
import com.fung.server.content.dao.PlayerDao;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 玩家缓存
 * @author skytrc@163.com
 * @date 2020/5/18 10:36
 */
@Component
public class PlayerCache {

    @Autowired
    private PlayerDao playerDao;

    /**
     * key 玩家名  value 玩家实体
     */
    private Cache<String, Player> playerCache = CacheBuilder.newBuilder().softValues().build();

    /**
     * 通过玩家名字从缓存中获取玩家实体，如果缓存中没有，则到数据库中获取。如果数据库也没有返回null
     * @param playerName 玩家名字
     * @return 玩家实体
     */
    public Player getPlayerByPlayerName(String playerName) {
        Player player = playerCache.getIfPresent(playerName);
        if (player == null) {
            player = playerDao.getPlayerByPlayerName(playerName);
            if (player != null) {
                playerCache.asMap().putIfAbsent(playerName, player);
            }
        }
        return player;
    }

    /**
     * 玩家注册，先写入数据库，再重新从数据库中获得数据
     * @param player 玩家实体
     */
    public void createPlayer(Player player) {
        playerDao.playerRegister(player);
        player = playerDao.getPlayerByPlayerName(player.getPlayerName());
        playerCache.asMap().putIfAbsent(player.getPlayerName(), player);
    }

    public void updatePlayer(Player player) {
        playerDao.updatePlayer(player);
    }
}
