package com.fung.server.chatserver.service.impl;

import com.fung.client.newclient.MainPage;
import com.fung.server.chatserver.code.TipsCode;
import com.fung.server.chatserver.entity.ChatPlayer;
import com.fung.server.chatserver.service.ChatMessageServer;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import com.fung.server.chatserver.writemessage.WriteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/30 15:17
 */
@Component
public class ChatMessageServerImpl implements ChatMessageServer {

    @Autowired
    private ChatStoredChannel storedChannel;

    @Autowired
    private WriteMessage writeMessage;

    @Autowired
    private MainPage mainPage;

    @Override
    public void publicChat(String channelId, String content) {
        int publicChatCd = 10000;
        if (checkChatCd(storedChannel.getPlayerByChannelId(channelId), publicChatCd)) {
            writeMessage.writeMessage2AllChannel(content, channelId);
        }
    }

    @Override
    public void privateChat(String playerName, String channelId, String content) {
        if (playerName == null || !storedChannel.getPlayerNameChannelMap().containsKey(playerName)) {
            writeMessage.writeTipsMessage(TipsCode.PLAYER_NOT_EXISTS_OR_NOT_ONLINE, channelId);
            return;
        }
        int privateChatCd = 1000;
        if (checkChatCd(storedChannel.getPlayerByChannelId(channelId), privateChatCd)) {
            writeMessage.writeMessage2Player(content, channelId, playerName);
        }
    }

    @Override
    public void echoTip(int code) {
        if (code == TipsCode.PLAYER_NOT_EXISTS_OR_NOT_ONLINE) {
            mainPage.echoChatMessage("玩家不存在或不在线上");
        }
    }

    public boolean checkChatCd(ChatPlayer chatPlayer, long cd) {
        if (chatPlayer.getChatCd() == 0) {
            chatPlayer.setChatCd(System.currentTimeMillis());
            return false;
        }else if (chatPlayer.getChatCd() + cd > System.currentTimeMillis()) {
            chatPlayer.setChatCd(System.currentTimeMillis());
            return false;
        }
        return true;
    }
}
