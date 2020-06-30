package com.fung.server.chatserver;

import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.server.chatserver.controller.Distribution;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/6/19 10:45
 */
public class ChatServer {

    private Distribution distribution;

    private ChatStoredChannel chatStoredChannel;

    public ChatServer(Distribution distribution, ChatStoredChannel chatStoredChannel) {
        this.distribution = distribution;
        this.chatStoredChannel = chatStoredChannel;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_BACKLOG, 128);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtobufDecoder(ChatMessage.ChatServerMessage.getDefaultInstance()));
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                ch.pipeline().addLast(new ProtobufEncoder());
                ch.pipeline().addLast(new ChatServerHandler(distribution, chatStoredChannel));
            }
        });
        ChannelFuture f = b.bind(port).sync();
        if (f.isSuccess()) {
            LOGGER.info("聊天服务器启动成功");
        }
    }
}
