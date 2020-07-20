package com.fung.server.gameserver.cache.mycache;

import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.dao.PlayerDao;
import com.fung.server.gameserver.content.entity.PlayerCommConfig;
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

    @Autowired
    private OnlinePlayer onlinePlayer;

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

    // TODO 每隔几分钟/下线时，把玩家身上挂载的物品or东西导入数据库

    /**
     * 玩家注册，先写入数据库，再重新从数据库中获得数据
     * @param player 玩家实体
     */
    public void createPlayer(Player player) {
        playerDao.playerRegister(player);
        player = playerDao.getPlayerByPlayerName(player.getPlayerName());
        playerCache.asMap().putIfAbsent(player.getPlayerName(), player);
        onlinePlayer.addPlayerId(player.getUuid());
    }

    public void insertPlayerCommConfig(PlayerCommConfig playerCommConfig) {
        playerDao.insertPlayerCommConfig(playerCommConfig);
    }

    public void updatePlayer(Player player) {
        playerDao.updatePlayer(player);
    }
}
