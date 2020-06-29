package com.fung.server.chatserver.cache;

import com.fung.server.chatserver.dao.ChatPlayerDao;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 12:11
 */
@Component
public class ChatPlayerCache {

    @Autowired
    private ChatPlayerDao chatPlayerDao;

    private Cache<String, ChatPlayer> playerCache = CacheBuilder.newBuilder().softValues().build();

    public ChatPlayer getPlayerByPlayerName(String playerName) {
        ChatPlayer chatPlayer = playerCache.getIfPresent(playerName);
        if (chatPlayer == null) {
            chatPlayer = chatPlayerDao.getPlayerByPlayerName(playerName);
            if (chatPlayer != null) {
                playerCache.asMap().putIfAbsent(playerName, chatPlayer);
            }
        }
        return chatPlayer;
    }
}
