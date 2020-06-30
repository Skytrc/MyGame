package com.fung.server.chatserver;

import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.server.chatserver.controller.Distribution;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/6/19 11:10
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServerHandler.class);

    private Distribution distribution;

    private ChatStoredChannel chatStoredChannel;

    public ChatServerHandler(Distribution distribution, ChatStoredChannel chatStoredChannel) {
        this.distribution = distribution;
        this.chatStoredChannel = chatStoredChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        chatStoredChannel.addChannel(ctx.channel().id().asLongText(), ctx.channel());
        LOGGER.info("Succeed connected client");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatMessage.ChatServerMessage chatServerMessage = (ChatMessage.ChatServerMessage) msg;
        distribution.distributionById(chatServerMessage.getCode(), chatServerMessage.getModel().toByteArray(), ctx.channel().id().asLongText());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatStoredChannel.removeChannelById(ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
