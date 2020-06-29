package com.fung.server.chatserver.dao;

import com.fung.server.chatserver.entity.ChatPlayer;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 12:04
 */
public interface ChatPlayerDao {

    /**
     * 根据玩家姓名获取玩家信息
     * @param playerName 玩家姓名
     * @return 玩家消息
     */
    ChatPlayer getPlayerByPlayerName(String playerName);
}
