package com.fung.server.gameserver.content.domain.monster.monsterthread;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.message.IMessage;
import com.fung.server.gameserver.message.IMessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/7/27 10:47
 */
public interface IMonster<H extends IMessageHandler<?>> {

    String getName();

    GameMap getGameMap();

    void addMessage(IMessage<H> message);
}
