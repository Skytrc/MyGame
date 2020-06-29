package com.fung.server.chatserver.writemessage;

import com.fung.protobuf.protoclass.TipsMessages;
import com.fung.server.chatserver.stored.StoreChannel;
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
    private StoreChannel storeChannel;

    public void writeMessage2Client(String channelId, Object message) {
        Channel channel = storeChannel.getChannelById(channelId);
        channel.writeAndFlush(message);
    }

    /**
     * 返回客户端提示信息码
     */
    public void writeTipsMessage(String channelId, int tipsCode) {
        Channel channel = storeChannel.getChannelById(channelId);
        TipsMessages.TipsMessage.Builder builder = TipsMessages.TipsMessage.newBuilder();
        builder.setMessageCode(tipsCode);
        channel.writeAndFlush(builder.build());
    }

}
