package com.fung.server;

import com.fung.protobuf.InstructionPack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skytrc@163.com
 * @date 2020/4/27 15:37
 */
public class GameServerHandler extends ChannelInboundHandlerAdapter {

    InstructionPack instructionPack = new InstructionPack();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Service accept client message : " + msg);

        ctx.writeAndFlush(instructionPack.decode("Service Got the message"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
