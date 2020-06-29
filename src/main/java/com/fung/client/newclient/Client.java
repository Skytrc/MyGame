package com.fung.client.newclient;

import com.fung.client.newclient.messagehandle.WriteMessage;
import com.fung.protobuf.protoclass.ChatMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 20:14
 */
public class Client {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufDecoder(
                                    ChatMessage.ChatServerMessage.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            guiClient(f.channel());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void guiClient(Channel channel) {
        Login.getInstance();
        WriteMessage message = WriteMessage.getInstance();
        message.setChannel(channel);
    }

    public static void main(String[] args) throws Exception {
        int port = 8090;
        new Client().connect(port, "127.0.0.1");
    }
}
