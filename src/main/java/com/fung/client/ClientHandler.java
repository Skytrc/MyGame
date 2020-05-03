package com.fung.client;

import com.fung.protobuf.InstructionPack;
import com.fung.protobuf.InstructionProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skytrc@163.com
 * @date 2020/4/28 14:50
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    InstructionPack instructionPack = new InstructionPack();

    GUIClient guiClient = GUIClient.getInstace();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(instructionPack.decode("test"));
        System.out.println("开始连接服务器");
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response: " + msg);
        guiClient.response(instructionPack.encode((InstructionProto.Instruction) msg));
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
