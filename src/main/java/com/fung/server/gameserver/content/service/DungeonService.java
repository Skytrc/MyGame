package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 16:56
 **/
public interface DungeonService {

    String enterDungeon(String channelId, int dungeonId);

    String enterDungeon(String channelId, String dungeonUuid);

    String leaveDungeon(String channelId);
}
