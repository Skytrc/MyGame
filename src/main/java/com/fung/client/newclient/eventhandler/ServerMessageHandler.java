package com.fung.client.newclient.eventhandler;

import com.fung.client.newclient.Login;
import com.fung.client.newclient.MainPage;
import com.fung.client.newclient.code.ModelCode;
import com.fung.protobuf.protoclass.ChatMessage;
import com.fung.protobuf.protoclass.ChatMessageRequest;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author skytrc@163.com
 * @date 2020/6/29 11:53
 */
public class ServerMessageHandler {

    private Login login = Login.getInstance();

    private MainPage mainPage = MainPage.getInstance();

    public void handleServerMessage(ChatMessage.ChatServerMessage message) throws InvalidProtocolBufferException {
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

    public void login(byte[] model) throws InvalidProtocolBufferException {
        ChatMessageRequest.PlayerLoginInfo playerLoginInfo = ChatMessageRequest.PlayerLoginInfo.parseFrom(model);
        if (playerLoginInfo.getPlayerName() == null) {
            login.showMessage("用户名或者密码错误");
        } else {
            login.openMainPage(playerLoginInfo.getPlayerName(), playerLoginInfo.getPassword());
        }
    }

    public void register(byte[] model) {

    }

    public void chat(byte[] model) {

    }
}
