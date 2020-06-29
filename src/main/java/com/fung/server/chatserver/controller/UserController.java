package com.fung.server.chatserver.controller;

import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.fung.server.chatserver.cache.ChatPlayerCache;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.service.PlayerServer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 19:56
 */
@Component
public class UserController {

    @Autowired
    private ChatPlayerCache chatPlayerCache;

    @Autowired
    private PlayerServer playerServer;

    public void login(byte[] model, String channelId) throws InvalidProtocolBufferException {
        ChatMessageRequest.PlayerLoginInfo playerLoginInfo = ChatMessageRequest.PlayerLoginInfo.parseFrom(model);
        playerServer.login(playerLoginInfo.getPlayerName(), playerLoginInfo.getPassword(), channelId);
    }

    public void register(byte[] model, String channelId) {

    }
}
