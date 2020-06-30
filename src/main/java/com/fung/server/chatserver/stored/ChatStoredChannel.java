package com.fung.server.chatserver.stored;

import com.fung.server.chatserver.entity.ChatPlayer;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 存储四个容器
 * 第一个是根据channel id 查找对应的 channel
 * 第二个是根据channel id 查找对应的 chatPlayer实体
 * 第三个是根据chatPlayer名字 查找对应的 channel
 * 第四个是存储所有的ChatPlayerChannel
 * @author skytrc@163.com
 * @date 2020/6/24 15:45
 */
@Component
public class ChatStoredChannel {

    /**
     * key channel id  value player
     */
    private Map<String, ChatPlayer> channelPlayerMap;

    /**
     * key channel id  value channel
     */
    private Map<String, Channel> channelMap;

    /**
     * key playerName  value Channel
     */
    private Map<String, Channel> playerNameChannelMap;

    private List<Channel> allChannel;

    public void storeChannelInit(Map<String, ChatPlayer> channelPlayerMap, Map<String, Channel> channelMap, Map<String, Channel> playerNameChannelMap) {
        this.channelMap = channelMap;
        this.channelPlayerMap = channelPlayerMap;
        this.playerNameChannelMap = playerNameChannelMap;
    }

    public void putPlayer(String channelId, ChatPlayer chatPlayer) {
        channelPlayerMap.put(channelId, chatPlayer);
        playerNameChannelMap.put(chatPlayer.getPlayerName(), channelMap.get(channelId));
        allChannel.add(channelMap.get(channelId));
    }

    public void removePlayerByChannelId(String channelId) {
        ChatPlayer remove = channelPlayerMap.remove(channelId);
        playerNameChannelMap.remove(remove.getPlayerName());
        allChannel.remove(channelMap.get(channelId));
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
        removePlayerByChannelId(channelId);
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

    public Map<String, Channel> getPlayerNameChannelMap() {
        return playerNameChannelMap;
    }

    public void setPlayerNameChannelMap(Map<String, Channel> playerNameChannelMap) {
        this.playerNameChannelMap = playerNameChannelMap;
    }

    public List<Channel> getAllChannel() {
        return allChannel;
    }

    public void setAllChannel(List<Channel> allChannel) {
        this.allChannel = allChannel;
    }
}
