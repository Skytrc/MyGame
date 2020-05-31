package com.fung.server.content.dao;

import com.fung.server.content.entity.Player;

/**
 * @author skytrc@163.com
 * @date 2020/5/12 16:59
 */
public interface LocationDao {

    /**
     * 修改玩家位置信息
     * @param player 玩家信息
     */
    void updatePlayerLocation(Player player);
}
