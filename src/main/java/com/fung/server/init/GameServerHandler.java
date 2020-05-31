package com.fung.server.init;

import com.fung.protobuf.InstructionPack;
import com.fung.protobuf.InstructionProto;
import com.fung.server.content.controller.Controller;
import com.fung.server.content.util.playerutil.OnlinePlayer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/4/27 15:37
 */
public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServerHandler.class);

    private Controller controller;

    private InstructionPack instructionPack;

    private OnlinePlayer onlinePlayer;

    public GameServerHandler(Controller controller, InstructionPack instructionPack, OnlinePlayer onlinePlayer) {
        this.instructionPack = instructionPack;
        this.controller = controller;
        this.onlinePlayer = onlinePlayer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        onlinePlayer.getPlayerMap().put(String.valueOf(ctx.channel().id()), null);
//        LOGGER.info(String.valueOf(ctx.channel().id()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Service accept client message : " + instructionPack.encode((InstructionProto.Instruction) msg));
        String response = controller.handleMessage(instructionPack.encode((InstructionProto.Instruction) msg),
                String.valueOf(ctx.channel().id()));
        ctx.writeAndFlush(instructionPack.decode(response));
//        LOGGER.info(String.valueOf(ctx.channel().id()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        onlinePlayer.getPlayerMap().remove(String.valueOf(ctx.channel().id()));
    }
}
