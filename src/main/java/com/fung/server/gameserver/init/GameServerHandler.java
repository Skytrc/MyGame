package com.fung.server.gameserver.init;

import com.fung.protobuf.protoclass.InstructionPack;
import com.fung.protobuf.protoclass.InstructionProto;
import com.fung.server.gameserver.channelstore.StoredChannel;
import com.fung.server.gameserver.content.controller.Controller;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
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

    Controller controller;

    InstructionPack instructionPack;

    OnlinePlayer onlinePlayer;

    StoredChannel storedChannel;

    public GameServerHandler(Controller controller, InstructionPack instructionPack, OnlinePlayer onlinePlayer, StoredChannel storedChannel) {
        this.instructionPack = instructionPack;
        this.controller = controller;
        this.onlinePlayer = onlinePlayer;
        this.storedChannel = storedChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        storedChannel.putChannelAndId(ctx.channel().id().asLongText(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Service accept client message : " + instructionPack.encode((InstructionProto.Instruction) msg));
        String response = controller.handleMessage(instructionPack.encode((InstructionProto.Instruction) msg),
                ctx.channel().id().asLongText());
        ctx.writeAndFlush(instructionPack.decode(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        onlinePlayer.getPlayerMap().remove(ctx.channel().id().asLongText());
        storedChannel.removeChannelById(ctx.channel().id().asLongText());
    }
}
