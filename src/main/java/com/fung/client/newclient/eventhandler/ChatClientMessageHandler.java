package com.fung.client.newclient.eventhandler;

import com.fung.client.newclient.MainPage;
import com.fung.client.newclient.code.ChatCode;
import com.fung.client.newclient.entity.ClientChatPlayer;
import com.fung.client.newclient.messagehandle.ChatClientWriteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ChatClient 发送消息
 * @author skytrc@163.com
 * @date 2020/7/1 12:49
 */
@Component
public class ChatClientMessageHandler {

    @Autowired
    private ChatClientWriteMessage chatClientWriteMessage;

    @Autowired
    private MainPage mainPage;

    @Autowired
    private ClientChatPlayer clientChatPlayer;

    public void handler(int chatMode, String message) {
        long publicChatCd = 10000;
        long privateChatCd = 1000;
        if (chatMode == ChatCode.PUBLIC_CHAT) {
            if (clientChatPlayer.getChatCds().get(ClientChatPlayer.PUBLIC_CHAT_CD) + publicChatCd < System.currentTimeMillis()) {
                clientChatPlayer.getChatCds().add(ClientChatPlayer.PUBLIC_CHAT_CD, System.currentTimeMillis());
                chatClientWriteMessage.sendChatMessage(chatMode, "", message);
                return;
            }
        } else if (chatMode == ChatCode.PRIVATE_CHAT) {
            if (clientChatPlayer.getChatCds().get(ClientChatPlayer.PRIVATE_CHAT_CD) + privateChatCd < System.currentTimeMillis()) {
                clientChatPlayer.getChatCds().add(ClientChatPlayer.PRIVATE_CHAT_CD, System.currentTimeMillis());
                String[] s = message.split(" ", 2);
                if (s.length == 1) {
                    mainPage.echoChatMessage("请输入正确的用户名");
                }
                chatClientWriteMessage.sendChatMessage(chatMode, s[0], s[1]);
                return;
            }
        }
        mainPage.echoChatMessage("发言冷却中，请稍后在发言");
    }

    public void closeChannel() {
        chatClientWriteMessage.closeChannel();
    }
}
