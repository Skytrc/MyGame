package com.fung.client.newclient;

import com.fung.client.newclient.eventhandler.ServerMessageHandler;
import com.fung.client.newclient.messagehandle.ChatClientWriteMessage;
import com.fung.protobuf.protoclass.ChatMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 20:14
 */

@Component
public class Client {

    @Autowired
    private Client client;

    @Autowired
    private Login login;

    @Autowired
    private ChatClientWriteMessage chatClientWriteMessage;

    @Autowired
    private ServerMessageHandler serverMessageHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

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
                            ch.pipeline().addLast(new ClientHandler(serverMessageHandler));
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            guiClientStart(f.channel());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void guiClientStart(Channel channel) {
        login.loginInit();
        chatClientWriteMessage.setChannel(channel);
    }

    public void clientInit(int port, String host) {

    }

    public static void main(String[] args) throws Exception {
        int port = 8090;
        String host = "127.0.0.1";
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Spring 初始化");
        Client client = context.getBean(Client.class);
        client.connect(port, host);
    }
}
