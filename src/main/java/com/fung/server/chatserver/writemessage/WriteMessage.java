package com.fung.server.chatserver.writemessage;

import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.fung.protobuf.protoclass.TipsMessages;
import com.fung.server.chatserver.code.ChatCode;
import com.fung.server.chatserver.code.ModelCode;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 15:24
 */
@Component
public class WriteMessage {

    @Autowired
    private ChatStoredChannel chatStoredChannel;

    public void writeMessage2Client(String channelId, Object message) {
        Channel channel = chatStoredChannel.getChannelById(channelId);
        channel.writeAndFlush(message);
    }

    /**
     * 返回客户端提示信息码
     */
    public void writeLoginMessage(String channelId, int tipsCode) {
        Channel channel = chatStoredChannel.getChannelById(channelId);

        TipsMessages.TipsMessage.Builder builder = TipsMessages.TipsMessage.newBuilder();
        builder.setMessageCode(tipsCode);

        channel.writeAndFlush(packMessage(ModelCode.LOGIN, builder.build().toByteString()));
    }

    /**
     * 向所有的Channel发送消息
     */
    public void writeMessage2AllChannel(String content, String channelId) {
        List<Channel> allChannel = chatStoredChannel.getAllChannel();
        ChatMessageRequest.ChatRequest.Builder builder = ChatMessageRequest.ChatRequest.newBuilder();
        builder.setChatMode(ChatCode.PUBLIC_CHAT);
        builder.setPlayerName(chatStoredChannel.getPlayerByChannelId(channelId).getPlayerName());
        builder.setContent(content);
        ByteString bytes = builder.build().toByteString();
        for (Channel channel : allChannel) {
            channel.writeAndFlush(packMessage(ModelCode.CHAT, bytes));
        }
    }

    public void writeMessage2Player(String content, String channelId, String targetPlayer) {
        ChatPlayer sendPlayer = chatStoredChannel.getPlayerByChannelId(channelId);
        ChatMessageRequest.ChatRequest.Builder builder = ChatMessageRequest.ChatRequest.newBuilder();

        builder.setContent(content);
        builder.setPlayerName(sendPlayer.getPlayerName());
        builder.setChatMode(ChatCode.PRIVATE_CHAT);

        Channel channel = chatStoredChannel.getChannelByPlayerName(targetPlayer);

        channel.writeAndFlush(packMessage(ModelCode.CHAT, builder.build().toByteString()));
    }

    /**
     * 发送提示信息
     */
    public void writeTipsMessage(int tipCode, String channelId) {
        TipsMessages.TipsMessage.Builder builder = TipsMessages.TipsMessage.newBuilder();
        builder.setMessageCode(tipCode);
        ChatMessage.ChatServerMessage message = packMessage(ModelCode.TIPS_MESSAGE, builder.build().toByteString());
        chatStoredChannel.getChannelById(channelId).writeAndFlush(message);
    }

    /**
     * 总消息最外层包装 code 消息码   byteString 对应的已序列化的内容
     */
    public ChatMessage.ChatServerMessage packMessage(int code, ByteString bytes) {
        ChatMessage.ChatServerMessage.Builder builder = ChatMessage.ChatServerMessage.newBuilder();
        builder.setCode(code);
        builder.setModel(bytes);
        return builder.build();
    }

}
