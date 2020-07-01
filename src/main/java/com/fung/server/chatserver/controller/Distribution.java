package com.fung.server.chatserver.controller;

import com.fung.server.chatserver.code.ModelCode;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/23 19:51
 */
@Component
public class Distribution {

    @Autowired
    private ChatController chatController;

    @Autowired
    private UserController userController;

    /**
     * TODO 运用设计模式干掉Switch
     */
    public void distributionById(int code, byte[] model, String channelId) throws InvalidProtocolBufferException {
        switch (code) {
            case(ModelCode.LOGIN) :
                userController.login(model, channelId);
                break;
            case(ModelCode.REGISTER) :
                userController.register(model, channelId);
                break;
            case(ModelCode.CHAT) :
                chatController.chat(model, channelId);
                break;
            case(ModelCode.TIPS_MESSAGE) :
                chatController.tip(model);
            default:
                break;
        }
    }

}
