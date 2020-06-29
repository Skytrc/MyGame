package com.fung.server.chatserver.controller;

import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 19:56
 */
@Component
public class ChatController {

    public void chat(byte[] model, String channelId) throws InvalidProtocolBufferException {
        ChatMessageRequest.ChatRequest chatRequest = ChatMessageRequest.ChatRequest.parseFrom(model);
        System.out.println(chatRequest.toString());
    }
}
