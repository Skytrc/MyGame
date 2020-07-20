package com.fung.server.gameserver.content.domain.mapactor;

import com.fung.server.gameserver.message.IMessage;
import com.fung.server.gameserver.message.IMessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/7/17 12:19
 */
public interface IPlayer<H extends IMessageHandler<?>> {

    String getUuid();

    void addMessage(IMessage<H> message);
}
