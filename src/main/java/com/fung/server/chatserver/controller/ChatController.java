package com.fung.server.chatserver.controller;

import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.fung.protobuf.protoclass.TipsMessages;
import com.fung.server.chatserver.code.ChatCode;
import com.fung.server.chatserver.service.ChatMessageServer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 19:56
 */
@Component
public class ChatController {

    @Autowired
    ChatMessageServer chatMessageServer;

    public void chat(byte[] model, String channelId) throws InvalidProtocolBufferException {
        ChatMessageRequest.ChatRequest chatRequest = ChatMessageRequest.ChatRequest.parseFrom(model);
        if (chatRequest.getChatMode() == ChatCode.PUBLIC_CHAT) {
            chatMessageServer.publicChat(channelId, chatRequest.getContent());
        } else if (chatRequest.getChatMode() == ChatCode.PRIVATE_CHAT) {
            chatMessageServer.privateChat(chatRequest.getPlayerName(), channelId, chatRequest.getContent());
        }
    }

    public void tip(byte[] model) throws InvalidProtocolBufferException {
        TipsMessages.TipsMessage tipsMessage = TipsMessages.TipsMessage.parseFrom(model);
        chatMessageServer.echoTip(tipsMessage.getMessageCode());
    }
}
