package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.domain.npc.NpcInfo;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.DungeonService;
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
    private PlayerInfo playerInfo;

    @Autowired
    private NpcInfo npcInfo;

    @Autowired
    private DungeonService dungeonService;

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

    @Override
    public String openDungeon(String channel) {
        Player player = playerInfo.getCurrentPlayer(channel);
        if (!npcInfo.hasNpc(player)) {
            return "当前玩家位置没有npc";
        }
        int dungeon = npcInfo.enterDungeon(player);
        if (dungeon == -1) {
            return "该Npc没有副本功能";
        }
        dungeonService.enterDungeon(channel, dungeon);
        return null;
    }

    @Override
    public String joinDungeon(String channelId, String dungeonId) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        String res = dungeonService.enterDungeon(channelId, dungeonId);
        if ("".equals(res)) {
            return "副本编号: " + dungeonId + " 副本不存在";
        }
        return "成功进入副本: " + res + " 副本编号为: " + dungeonId;
    }
}
