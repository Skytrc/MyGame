package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.PlayerOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/30 9:55
 */
@Component
public class PlayerOtherServiceImpl implements PlayerOtherService {

    @Autowired
    private MapManager mapManager;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String rebirth(String channelId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor gameMapActorById = mapManager.getGameMapActorById(player.getInMapId());
        gameMapActorById.addMessage(gameMapActor -> {
            player.setHealthPoint(player.getMaxHealthPoint());
            player.setInMapX(1);
            player.setInMapY(1);
            writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + player.getName() + "重生完毕"
                    + " 当前生命值为: " + player.getHealthPoint() + String.format("当前坐标为 [%s,%s]", player.getInMapX(), player.getInMapY()));
        });
        return "";
    }
}
