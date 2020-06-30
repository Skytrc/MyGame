package com.fung.server.chatserver.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/30 15:17
 */
public interface ChatServer {

    /**
     * 公频聊天
     * @param playerId 发言玩家ID
     * @param channelId 发言玩家channel id
     * @param content 发言内容
     */
    void publicChat(String playerId, String channelId, String content);

    /**
     * @param playerId 发言玩家 id
     * @param playerName 私聊玩家名字
     * @param channelId 发言玩家Channel id
     * @param content 发言内容
     */
    void privateChat(String playerId, String playerName, String channelId, String content);
}
