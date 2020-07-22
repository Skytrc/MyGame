package com.fung.server.gameserver.channelstore;

import com.fung.protobuf.protoclass.InstructionPack;
import com.fung.server.gameserver.content.config.map.GameMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 16:30
 */
@Component
public class WriteMessage2Client {

    @Autowired
    private StoredChannel storedChannel;

    @Autowired
    private InstructionPack instructionPack;

    public void writeMessage(String channelId, String message) {
        Channel channel = storedChannel.getChannelById(channelId);
        channel.writeAndFlush(instructionPack.decode(message));
    }

    /**
     * 给地图中的所有人发消息
     */
    public void writeMessage2MapPlayer(GameMap gameMap, String message) {
        List<String> playChannel = gameMap.getPlayChannel();
        for (String s : playChannel) {
            writeMessage(s, message);
        }
    }
}
