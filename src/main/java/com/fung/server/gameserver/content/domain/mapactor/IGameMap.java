package com.fung.server.gameserver.content.domain.mapactor;

import com.fung.server.gameserver.message.IMessage;
import com.fung.server.gameserver.message.IMessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/7/7 16:35
 */
public interface IGameMap<H extends IMessageHandler<?>> {

    String getName();

    int getId();

    String getUuid();

    void addMessage(IMessage<H> message);
}
