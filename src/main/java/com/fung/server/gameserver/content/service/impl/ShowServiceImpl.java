package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.domain.monster.MonsterInfo;
import com.fung.server.gameserver.content.service.ShowService;
import com.fung.server.gameserver.content.domain.map.MapInfo;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 12:52
 */
@Component
public class ShowServiceImpl implements ShowService {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    MonsterInfo monsterInfo;

    @Autowired
    MapInfo mapInfo;

    @Override
    public String showPlayer(String channelId) {
        return playerInfo.showPlayerInfo(playerInfo.getCurrentPlayer(channelId));
    }

    @Override
    public String showMapOnlinePlayer(String channelId) {
        return mapInfo.showMapOnlinePlayer(playerInfo.getCurrentPlayerMap(channelId));
    }

    @Override
    public String showMapElement(String channelId) {
        return mapInfo.showMapInfo(playerInfo.getCurrentPlayerMap(channelId));
    }

    @Override
    public String showBackpack(String channelId) {
        return playerInfo.showBackpack(playerInfo.getCurrentPlayer(channelId));
    }

    @Override
    public String showSkill(String channelId) {
        return playerInfo.showSkill(playerInfo.getCurrentPlayer(channelId));
    }

    @Override
    public String showBodyEquipment(String channelId) {
        return playerInfo.showBodyEquipment(playerInfo.getCurrentPlayer(channelId));
    }

    @Override
    public String showMonster(String channelId, int x, int y) {
        GameMap currentPlayerMap = playerInfo.getCurrentPlayerMap(channelId);
        BaseHostileMonster normalMonster = currentPlayerMap.getMonsterByXy(x, y);
        if (normalMonster == null) {
            return "该格子没有怪兽";
        }
        return monsterInfo.showMonster(normalMonster);
    }


}
