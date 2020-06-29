package com.fung.server.chatserver.service.impl;

import com.fung.server.chatserver.cache.ChatPlayerCache;
import com.fung.server.chatserver.code.TipsCode;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.service.PlayerServer;
import com.fung.server.chatserver.stored.StoreChannel;
import com.fung.server.chatserver.writemessage.WriteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 15:15
 */
@Component
public class PlayerServerImpl implements PlayerServer {

    @Autowired
    private ChatPlayerCache chatPlayerCache;

    @Autowired
    private WriteMessage writeMessage;

    @Autowired
    private StoreChannel storeChannel;

    @Override
    public void login(String playerName, String password, String channelId) {
        ChatPlayer chatPlayer = chatPlayerCache.getPlayerByPlayerName(playerName);
        if (!playerName.equals(chatPlayer.getPlayerName())) {
            writeMessage.writeTipsMessage(channelId, TipsCode.PLAYER_NAME_NOT_EXISTS);
        }
        if (!password.equals(chatPlayer.getPassword())) {
            writeMessage.writeTipsMessage(channelId, TipsCode.PLAYER_PASSWORD_WRONG);
        }
        writeMessage.writeTipsMessage(channelId, TipsCode.LOGIN_SUCCESS);
        storeChannel.putPlayer(channelId, chatPlayer);
    }

    @Override
    public void register(String playerName, String password, String channelId) {

    }

    @Override
    public void logout(String channelId) {

    }
}
