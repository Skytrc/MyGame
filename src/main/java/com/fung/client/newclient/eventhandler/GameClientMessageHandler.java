package com.fung.client.newclient.eventhandler;

import com.fung.client.newclient.MainPage;
import com.fung.protobuf.protoclass.InstructionProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/2 11:40
 */
@Component
public class GameClientMessageHandler {

    @Autowired
    private MainPage mainPage;

    public void echoMessage(InstructionProto.Instruction instruction) {
        String instruction1 = instruction.getInstruction();
        mainPage.echoGameMessage(instruction1);
    }
}
