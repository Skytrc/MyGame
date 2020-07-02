package com.fung.client.newclient;

import com.fung.client.newclient.eventhandler.ChatServerMessageHandler;
import com.fung.protobuf.protoclass.ChatMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 10:25
 */
public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    private ChatServerMessageHandler chatServerMessageHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClientHandler.class);

    public ChatClientHandler(ChatServerMessageHandler chatServerMessageHandler) {
        this.chatServerMessageHandler = chatServerMessageHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        chatServerMessageHandler.handleServerMessage((ChatMessage.ChatServerMessage) msg);
        LOGGER.info(msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
