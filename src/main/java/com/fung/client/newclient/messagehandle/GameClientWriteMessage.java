package com.fung.client.newclient.messagehandle;

import com.fung.protobuf.protoclass.InstructionProto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/2 10:19
 */
@Component
public class GameClientWriteMessage {

    private Channel channel;

    public void sendInstruction2Server(String instruction) {
        InstructionProto.Instruction.Builder builder = InstructionProto.Instruction.newBuilder();
        builder.setInstruction(instruction);
        channel.writeAndFlush(builder.build());
    }

    public void closeChannel() {
        channel.close();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
