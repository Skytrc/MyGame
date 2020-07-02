package com.fung.client.newclient.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/1 15:09
 */
@Component
public class ClientChatPlayer {

    public static final int PUBLIC_CHAT_CD = 0;

    public static final int PRIVATE_CHAT_CD = 1;

    private String playerName;

    private String password;

    private List<Long> chatCds;

    public ClientChatPlayer() {
        chatCds = new ArrayList<>();
        chatCds.add((long) 0);
        chatCds.add((long) 0);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Long> getChatCds() {
        return chatCds;
    }

    public void setChatCds(List<Long> chatCds) {
        this.chatCds = chatCds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
