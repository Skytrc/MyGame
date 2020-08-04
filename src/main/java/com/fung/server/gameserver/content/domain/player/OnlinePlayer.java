package com.fung.server.gameserver.content.domain.player;

import com.fung.server.gameserver.content.dao.PlayerDao;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO 有线程安全的问题
 * 用于记录服务器在线玩家
 * @author skytrc@163.com
 * @date 2020/5/13 15:08
 */
@Component
public class OnlinePlayer {

    @Autowired
    private PlayerDao playerDao;

    /**
     * key channel id   value 玩家实体
     */
    private Map<String, Player> onlinePlayerMap;

    /**
     * key playerId   value player
     */
    private Map<String, PlayerActor> playerActorMap;

    /**
     * key 玩家ID   value channel id
     */
    private Map<String, String> idChannelMap;

    /**
     * 用于发邮件, 所有玩家的ID
     */
    private Set<String> allPlayerId;

    public void init() {
        onlinePlayerMap = new HashMap<>();
        playerActorMap = new HashMap<>();
        allPlayerId = new HashSet<>();
        idChannelMap = new HashMap<>();
        getAllPlayerId();
    }

    /**
     * 获取所有的玩家id
     */
    public void getAllPlayerId() {
        List<String> allPlayerId = playerDao.getAllPlayerId();
        this.allPlayerId.addAll(allPlayerId);
    }

    /**
     * 检查是否存在某个玩家ID
     * @param playerId player id
     * @return 是否存在某个ID
     */
    public boolean hasPlayerId(String playerId) {
        return allPlayerId.contains(playerId);
    }

    public void addPlayerId(String playerId) {
        allPlayerId.add(playerId);
    }

    public void addPlayer(Player player, String channelId) {
        onlinePlayerMap.put(channelId, player);
        PlayerActor playerActor = new PlayerActor();
        playerActor.setPlayer(player);
        playerActorMap.put(player.getUuid(), playerActor);
        idChannelMap.put(player.getUuid(), channelId);
    }

    public Player removePlayer(String channelId) {
        Player player = onlinePlayerMap.remove(channelId);
        playerActorMap.remove(player.getUuid());
        idChannelMap.remove(player.getUuid(), channelId);
        return player;
    }

    public Player getPlayerByChannelId(String channel){
        return onlinePlayerMap.get(channel);
    }

    public PlayerActor getPlayerActorByChannelId(String channelId) {
        Player player = onlinePlayerMap.get(channelId);
        return playerActorMap.get(player.getUuid());
    }

    public PlayerActor getPlayerActorByPlayerId(String playerId) {
        return playerActorMap.get(playerId);
    }

    public Player getPlayer(String playerId) {
        if (playerActorMap.containsKey(playerId)) {
            return playerActorMap.get(playerId).getPlayer();
        }
        return playerDao.getPlayerById(playerId);
    }

    public String getChannelIdByPlayerId(String playerId) {
        return idChannelMap.get(playerId);
    }

    public Map<String, Player> getPlayerMap() {
        return onlinePlayerMap;
    }

    public void setPlayerMap(Map<String, Player> playerMap) {
        this.onlinePlayerMap = playerMap;
    }

    public Map<String, PlayerActor> getPlayerActorMap() {
        return playerActorMap;
    }

    public void setPlayerActorMap(Map<String, PlayerActor> playerActorMap) {
        this.playerActorMap = playerActorMap;
    }

    public Map<String, String> getIdChannelMap() {
        return idChannelMap;
    }
}
