package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 16:56
 **/
public interface DungeonService {

    void enterDungeon(String channelId, int dungeonId);

    void leaveDungeon(String channelId);
}
