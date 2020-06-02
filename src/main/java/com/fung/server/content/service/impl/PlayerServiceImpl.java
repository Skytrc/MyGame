package com.fung.server.content.service.impl;

import com.fung.server.cache.mycache.PlayerCache;
import com.fung.server.content.config.manager.MapManager;
import com.fung.server.content.entity.Player;
import com.fung.server.content.service.PlayerService;
import com.fung.server.content.util.playerutil.OnlinePlayer;
import com.fung.server.content.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/18 21:05
 */
@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerCache playerCache;

    @Autowired
    OnlinePlayer onlinePlayer;

    @Autowired
    MapManager mapManager;

    @Autowired
    PlayerUtil playerUtil;

    @Override
    public String register(String playerName, String password) {
        Player player = playerCache.getPlayerByPlayerName(playerName);
        if (player != null) {
            return "角色已存在，请重新注册";
        }
        Player newPlayer = new Player();

        newPlayer.setPlayerName(playerName);
        newPlayer.setPassword(password);
        newPlayer.setCreatedDate(System.currentTimeMillis());
        newPlayer.setMaxHealthPoint(100);
        newPlayer.setHealthPoint(100);
        newPlayer.setInMapId(1);
        newPlayer.setInMapX(1);
        newPlayer.setInMapY(1);
        playerCache.createPlayer(newPlayer);
        return "角色创建完毕";
    }

    @Override
    public String login(String playerName, String password, String channelId) {
        Player player = playerCache.getPlayerByPlayerName(playerName);
        if (player == null) {
            return "不存在该名玩家";
        }
        if (!player.getPassword().equals(password)) {
            return "角色名或密码不正确";
        }
        // 把登录的玩家放在onlinePlayerMap中，与channel捆绑，方便操作
        Player playerLogin = playerCache.getPlayerByPlayerName(playerName);
        // 更新登录日期
        playerLogin.setLoginDate(System.currentTimeMillis());
        // onlinePlayer -> 指向PlayerCache的玩家实体
        onlinePlayer.getPlayerMap().put(channelId, playerLogin);
        // 地图Map捆绑线上玩家
        mapManager.getMapByMapId(playerLogin.getInMapId()).addPlayer(playerLogin);
        return "登录成功：\n " + playerUtil.showPlayerInfo(player);
    }

    @Override
    public String logout(String channelId) {
        if (onlinePlayer.getPlayerMap().containsKey(channelId)) {
            Player player = onlinePlayer.getPlayerMap().remove(channelId);
            // 地图 在线Map 移除该玩家
            playerUtil.getCurrentPlayerMap(channelId).removePlayer(player.getUuid());
            return "登出成功";
        }
        return "没有角色登录";
    }
}
