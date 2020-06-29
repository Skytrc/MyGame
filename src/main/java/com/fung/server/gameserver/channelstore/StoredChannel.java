package com.fung.server.gameserver.channelstore;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 12:16
 */
@Component
public class StoredChannel {

    private Map<String, Channel> channelMap;

    private List<Channel> allChannel;

    public StoredChannel() {
        this.channelMap = new HashMap<>();
        this.allChannel = new ArrayList<>();
    }

    public Channel getChannelById(String id) {
        return channelMap.get(id);
    }

    public void putChannelAndId(String id, Channel channel) {
        channelMap.put(id, channel);
        allChannel.add(channel);
    }

    public void removeChannelById(String id) {
        allChannel.remove(channelMap.remove(id));
    }

    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public List<Channel> getAllChannel() {
        return allChannel;
    }
}
