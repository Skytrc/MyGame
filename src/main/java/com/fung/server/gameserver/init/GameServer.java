package com.fung.server.gameserver.init;

import com.fung.protobuf.protoclass.InstructionPack;
import com.fung.protobuf.protoclass.InstructionProto;
import com.fung.server.gameserver.channelstore.StoredChannel;
import com.fung.server.gameserver.content.controller.Controller;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/4/27 15:33
 */
public class GameServer {

    private Controller controller;

    private InstructionPack instructionPack;

    private OnlinePlayer onlinePlayer;

    private StoredChannel storedChannel;

    public GameServer(Controller controller, InstructionPack instructionPack, OnlinePlayer onlinePlayer, StoredChannel storedChannel) {
        this.controller = controller;
        this.instructionPack = instructionPack;
        this.onlinePlayer = onlinePlayer;
        this.storedChannel = storedChannel;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);
    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup =  new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                // 有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                // 保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtobufDecoder(InstructionProto.Instruction.getDefaultInstance()));
                        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        ch.pipeline().addLast(new ProtobufEncoder());
                        ch.pipeline().addLast(new GameServerHandler(controller, instructionPack, onlinePlayer, storedChannel));
                    }
                });
        ChannelFuture f = b.bind(port).sync();
        if (f.isSuccess()) {
            LOGGER.info("服务器启动成功");
        }
    }
}
