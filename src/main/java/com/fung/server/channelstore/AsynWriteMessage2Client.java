package com.fung.server.channelstore;

import com.fung.protobuf.InstructionPack;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 16:30
 */
@Component
public class AsynWriteMessage2Client {

    @Autowired
    private StoredChannel storedChannel;

    @Autowired
    private InstructionPack instructionPack;

    public void writeMessage(String channelId, String message) {
        Channel channel = storedChannel.getChannelById(channelId);
        channel.writeAndFlush(instructionPack.decode(message));
    }
}
