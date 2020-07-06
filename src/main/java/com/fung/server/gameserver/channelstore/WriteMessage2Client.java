package com.fung.server.gameserver.channelstore;

import com.fung.protobuf.protoclass.InstructionPack;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 16:30
 */
@Component
public class WriteMessage2Client {

    @Autowired
    private StoredChannel storedChannel;

    @Autowired
    private InstructionPack instructionPack;

    public void writeMessage(String channelId, String message) {
        Channel channel = storedChannel.getChannelById(channelId);
        channel.writeAndFlush(instructionPack.decode(message));
    }
}
