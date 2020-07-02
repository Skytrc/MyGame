package com.fung.client.newclient.eventhandler;

import com.fung.client.newclient.Login;
import com.fung.client.newclient.MainPage;
import com.fung.client.newclient.code.ChatCode;
import com.fung.client.newclient.code.ModelCode;
import com.fung.client.newclient.code.TipsCode;
import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ChatClient 接受 消息
 * @author skytrc@163.com
 * @date 2020/6/29 11:53
 */
@Component
public class ChatServerMessageHandler {

    @Autowired
    private Login login;

    @Autowired
    private MainPage mainPage;

    public void handleServerMessage(ChatMessage.ChatServerMessage message) throws InvalidProtocolBufferException, InterruptedException {
        switch (message.getCode()) {
            case ModelCode.LOGIN :
                login(message.getModel().toByteArray());
                break;
            case ModelCode.REGISTER :
                register(message.getModel().toByteArray());
                break;
            case ModelCode.CHAT :
                chat(message.getModel().toByteArray());
                break;
            default:
                break;
        }
    }

    public void login(byte[] model) throws InvalidProtocolBufferException, InterruptedException {
        ChatMessageRequest.PlayerLoginInfo playerLoginInfo = ChatMessageRequest.PlayerLoginInfo.parseFrom(model);
        switch (playerLoginInfo.getCode()) {
            case(TipsCode.PLAYER_NAME_NOT_EXISTS):
                login.showMessage("用户名不存在");
                break;
            case(TipsCode.PLAYER_PASSWORD_WRONG):
                login.showMessage("密码错误");
                break;
            case(TipsCode.LOGIN_SUCCESS):
                login.openMainPage();
                break;
            default:
                login.showMessage(playerLoginInfo.getCode() + ":代码错误");
        }
    }

    public void register(byte[] model) {

    }

    public void chat(byte[] model) throws InvalidProtocolBufferException {
        ChatMessageRequest.ChatRequest chatRequest = ChatMessageRequest.ChatRequest.parseFrom(model);
        String message = "";
        if (chatRequest.getChatMode() == ChatCode.PUBLIC_CHAT) {
            message = "[公频] " + chatRequest.getPlayerName() + " : " + chatRequest.getContent();

        } else if (chatRequest.getChatMode() == ChatCode.PRIVATE_CHAT) {
            message = "[私聊] " + chatRequest.getPlayerName() + " : " + chatRequest.getContent();
        }
        mainPage.echoChatMessage(message);
    }
}
