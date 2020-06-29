package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.domain.npc.NpcInfo;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.NpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 17:18
 */
@Component
public class NpcServiceImpl implements NpcService {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    NpcInfo npcInfo;

    @Override
    public String allChoose(String channelId) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        if (!npcInfo.hasNpc(player)){
            return "当前玩家位置没有npc";
        }
        return npcInfo.getNpcChoose(player);
    }

    @Override
    public String oneChoose(String channelId, int choose) {
        Player currentPlayer = playerInfo.getCurrentPlayer(channelId);
        return npcInfo.getNpcMessageByChoose(currentPlayer, choose);
    }

    @Override
    public String openShop(String channelId) {
        Player currentPlayer = playerInfo.getCurrentPlayer(channelId);
        return npcInfo.openShop(currentPlayer);
    }
}
