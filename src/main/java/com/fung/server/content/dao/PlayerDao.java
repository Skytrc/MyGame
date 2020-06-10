package com.fung.server.content.dao;

import com.fung.server.content.entity.Player;
import com.fung.server.content.entity.PlayerCommConfig;

/**
 * @author skytrc@163.com
 * @date 2020/5/12 16:58
 */
public interface PlayerDao {

    /**
     * 注册玩家
     * @param player 玩家信息
     */
    void playerRegister(Player player);

    /**
     * 查找玩家信息
     * @param player 玩家信息
     * @return 返回是否查找到
     */
    @Deprecated
    boolean findPlayerInfo(Player player);

    /**
     * 验证账号密码
     * @param playerName 玩家名
     * @param password 玩家密码
     * @return 验证是否正确
     */
    Player getPlayerByPlayerNamePassword(String playerName, String password);

    /**
     * 返回玩家所有信息
     * @param player 玩家账号信息
     * @return 玩家所有信息
     */
    @Deprecated
    Player getPlayerAllInfo(Player player);

    /**
     * 通过玩家名字获得玩家实体
     * @param playerName 玩家名字
     * @return 玩家实体
     */
    Player getPlayerByPlayerName(String playerName);

    /**
     * 更新玩家
     * @param player 玩家实体
     */
    void updatePlayer(Player player);

    /**
     * 获取玩家通常配置信息
     * @param playerId 玩家Id
     * @return 玩家通常配置信息
     */
    PlayerCommConfig getPlayerCommConfigByPlayerId(String playerId);

    /**
     * 插入玩家通常配置
     * @param playerCommConfig 玩家通常配置
     */
    void insertPlayerCommConfig(PlayerCommConfig playerCommConfig);
}
