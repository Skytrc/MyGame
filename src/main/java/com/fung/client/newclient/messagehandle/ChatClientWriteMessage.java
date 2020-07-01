package com.fung.client.newclient.messagehandle;

import com.fung.client.newclient.code.ModelCode;
import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 20:22
 */
@Component
public class ChatClientWriteMessage {

    private Channel channel;

    /**
     * 发送聊天消息
     */
    public void sendChatMessage(int chatMode, String playerName,String content) {
        ChatMessage.ChatServerMessage.Builder builder = ChatMessage.ChatServerMessage.newBuilder();
        builder.setCode(ModelCode.CHAT);
        builder.setModel(message2ChatMessageReq(chatMode, playerName,content));
        channel.writeAndFlush(builder.build());
    }

    public ByteString message2ChatMessageReq(int chatMode, String playerName, String content) {
        ChatMessageRequest.ChatRequest.Builder builder = ChatMessageRequest.ChatRequest.newBuilder();
        builder.setContent(content);
        builder.setPlayerName(playerName);
        builder.setChatMode(chatMode);
        ChatMessageRequest.ChatRequest build = builder.build();
        return ByteString.copyFrom(build.toByteArray());
    }

    /**
     * 发送登录消息
     */
    public void sendLoginInfo(String playerName, String password) {
        ChatMessage.ChatServerMessage.Builder builder = ChatMessage.ChatServerMessage.newBuilder();
        builder.setCode(ModelCode.LOGIN);
        builder.setModel(message2PlayerLoginInfo(playerName, password));
        channel.writeAndFlush(builder.build());
    }

    public ByteString message2PlayerLoginInfo(String playerName, String password) {
        ChatMessageRequest.PlayerLoginInfo.Builder builder = ChatMessageRequest.PlayerLoginInfo.newBuilder();
        builder.setPlayerName(playerName);
        builder.setPassword(password);
        return ByteString.copyFrom(builder.build().toByteArray());
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
