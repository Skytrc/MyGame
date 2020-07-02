package com.fung.client.newclient;

import com.fung.client.newclient.eventhandler.GameClientMessageHandler;
import com.fung.protobuf.protoclass.InstructionProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skytrc@163.com
 * @date 2020/7/2 11:55
 */
public class GameClientHandler extends ChannelInboundHandlerAdapter {

    private GameClientMessageHandler messageHandler;

    public GameClientHandler(GameClientMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        messageHandler.echoMessage((InstructionProto.Instruction) msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
