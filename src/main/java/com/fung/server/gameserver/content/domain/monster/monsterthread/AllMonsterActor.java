package com.fung.server.gameserver.content.domain.monster.monsterthread;

import com.fung.server.gameserver.content.domain.mapactor.IGameMap;
import com.fung.server.gameserver.content.domain.monster.monsterfsm.MonsterStatusManager;
import com.fung.server.gameserver.message.MessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/7/27 10:46
 */
public class AllMonsterActor extends MessageHandler<AllMonsterActor> implements IGameMap<AllMonsterActor> {

    private MonsterStatusManager monsterStatusManager;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    public MonsterStatusManager getMonsterStatusManager() {
        return monsterStatusManager;
    }

    public void setMonsterStatusManager(MonsterStatusManager monsterStatusManager) {
        this.monsterStatusManager = monsterStatusManager;
    }
}
