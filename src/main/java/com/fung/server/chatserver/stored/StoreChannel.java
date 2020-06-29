package com.fung.server.chatserver.stored;

import com.fung.server.chatserver.entity.ChatPlayer;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 15:45
 */
@Component
public class StoreChannel {

    /**
     * key channel id  value player
     */
    private Map<String, ChatPlayer> channelPlayerMap;

    /**
     * key channel id  value channel
     */
    private Map<String, Channel> channelMap;

    public void storeChannelInit(Map<String, ChatPlayer> channelPlayerMap, Map<String, Channel> channelMap) {
        this.channelMap = channelMap;
        this.channelPlayerMap = channelPlayerMap;
    }

    public void putPlayer(String channelId, ChatPlayer chatPlayer) {
        channelPlayerMap.put(channelId, chatPlayer);
    }

    public void removePlayerByChannelId(String channelId) {

    }

    public ChatPlayer getPlayerByChannelId(String channelId) {
        return channelPlayerMap.get(channelId);
    }

    public Channel getChannelById(String channelId) {
        return channelMap.get(channelId);
    }

    public void addChannel(String channelId, Channel channel) {
        channelMap.put(channelId, channel);
    }

    public void removeChannelById(String channelId) {
        channelMap.remove(channelId);
    }


    public Map<String, ChatPlayer> getChannelPlayerMap() {
        return channelPlayerMap;
    }

    public void setChannelPlayerMap(Map<String, ChatPlayer> channelPlayerMap) {
        this.channelPlayerMap = channelPlayerMap;
    }

    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, Channel> channelMap) {
        this.channelMap = channelMap;
    }
}
