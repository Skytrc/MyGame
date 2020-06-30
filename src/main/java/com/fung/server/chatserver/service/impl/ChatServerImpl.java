package com.fung.server.chatserver.service.impl;

import com.fung.server.chatserver.service.ChatServer;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import com.fung.server.chatserver.writemessage.WriteMessage;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/30 15:17
 */
@Component
public class ChatServerImpl implements ChatServer {

    @Autowired
    private ChatStoredChannel storedChannel;

    @Autowired
    private WriteMessage writeMessage;

    @Override
    public void publicChat(String playerId, String channelId, String content) {

    }

    @Override
    public void privateChat(String playerId, String playerName, String channelId, String content) {

    }

    public void checkChatCd() {

    }
}
