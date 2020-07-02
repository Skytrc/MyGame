package com.fung.client.newclient;

import com.fung.client.newclient.eventhandler.ChatServerMessageHandler;
import com.fung.client.newclient.eventhandler.GameClientMessageHandler;
import com.fung.client.newclient.messagehandle.ChatClientWriteMessage;
import com.fung.client.newclient.messagehandle.GameClientWriteMessage;
import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.InstructionProto;
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
    private GameClientWriteMessage gameClientWriteMessage;

    @Autowired
    private ChatServerMessageHandler chatServerMessageHandler;

    @Autowired
    private GameClientMessageHandler gameClientMessageHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public void chatServerConnect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufDecoder(
                                    ChatMessage.ChatServerMessage.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new ChatClientHandler(chatServerMessageHandler));
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            guiClientStart(f.channel());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void gameServerConnect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufDecoder(
                                    InstructionProto.Instruction.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new GameClientHandler(gameClientMessageHandler));
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            gameClientWriteMessage.setChannel(f.channel());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void guiClientStart(Channel channel) {
        login.loginInit();
        chatClientWriteMessage.setChannel(channel);
    }

    public static void main(String[] args) throws Exception {
        int port = 8090;
        String host = "127.0.0.1";
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Spring 初始化");
        Client client = context.getBean(Client.class);
        client.chatServerConnect(port, host);
    }
}
