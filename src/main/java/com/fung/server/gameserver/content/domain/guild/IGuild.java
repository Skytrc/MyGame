package com.fung.server.gameserver.content.domain.guild;

import com.fung.server.gameserver.message.IMessage;
import com.fung.server.gameserver.message.IMessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 16:33
 */
public interface IGuild<H extends IMessageHandler<?>> {

    void addMessage(IMessage<H> message);
}
