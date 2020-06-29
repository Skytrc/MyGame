package com.fung.client.oldclient;

import com.fung.protobuf.protoclass.InstructionProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skytrc@163.com
 * @date 2020/4/28 14:50
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    GUIClient guiClient = GUIClient.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InstructionProto.Instruction message = (InstructionProto.Instruction) msg;
        guiClient.response(message.getInstruction());
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
