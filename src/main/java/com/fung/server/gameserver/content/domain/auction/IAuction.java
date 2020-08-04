package com.fung.server.gameserver.content.domain.auction;

import com.fung.server.gameserver.message.IMessage;
import com.fung.server.gameserver.message.IMessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/8/4 15:58
 */
public interface IAuction<H extends IMessageHandler<?>> {

    void addMessage(IMessage<H> message);
}
