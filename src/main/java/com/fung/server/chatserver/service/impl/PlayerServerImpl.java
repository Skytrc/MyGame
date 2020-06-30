package com.fung.server.chatserver.service.impl;

import com.fung.server.chatserver.cache.ChatPlayerCache;
import com.fung.server.chatserver.code.TipsCode;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.service.PlayerServer;
import com.fung.server.chatserver.stored.ChatStoredChannel;
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
    private ChatStoredChannel chatStoredChannel;

    @Override
    public void login(String playerName, String password, String channelId) {
        ChatPlayer chatPlayer = chatPlayerCache.getPlayerByPlayerName(playerName);
        if (chatPlayer == null) {
            writeMessage.writeLoginMessage(channelId, TipsCode.PLAYER_NAME_NOT_EXISTS);
        }else if (!password.equals(chatPlayer.getPassword())) {
            writeMessage.writeLoginMessage(channelId, TipsCode.PLAYER_PASSWORD_WRONG);
        } else {
            writeMessage.writeLoginMessage(channelId, TipsCode.LOGIN_SUCCESS);
            chatStoredChannel.putPlayer(channelId, chatPlayer);
        }
    }

    @Override
    public void register(String playerName, String password, String channelId) {

    }

    @Override
    public void logout(String channelId) {

    }
}
