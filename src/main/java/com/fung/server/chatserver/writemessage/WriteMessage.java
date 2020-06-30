package com.fung.server.chatserver.writemessage;

import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.fung.protobuf.protoclass.TipsMessages;
import com.fung.server.chatserver.code.ModelCode;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
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

    public void writeMessage2AllChannel(String content, String channelId) {
        List<Channel> allChannel = chatStoredChannel.getAllChannel();
        ChatPlayer player = chatStoredChannel.getPlayerByChannelId(channelId);
        ChatMessageRequest.ChatRequest.Builder builder = ChatMessageRequest.ChatRequest.newBuilder();
        for (Channel channel : allChannel) {

        }
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
