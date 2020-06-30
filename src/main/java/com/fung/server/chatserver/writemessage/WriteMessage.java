package com.fung.server.chatserver.writemessage;

import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.TipsMessages;
import com.fung.server.chatserver.code.ModelCode;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        ChatMessage.ChatServerMessage.Builder builder1 = ChatMessage.ChatServerMessage.newBuilder();
        builder1.setCode(ModelCode.LOGIN);

        TipsMessages.TipsMessage.Builder builder = TipsMessages.TipsMessage.newBuilder();
        builder.setMessageCode(tipsCode);

        builder1.setModel(builder.build().toByteString());
        channel.writeAndFlush(builder1.build());
    }

}
