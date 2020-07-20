package com.fung.server.gameserver.content.domain.mapactor;

import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.message.MessageHandler;

/**
 * @author skytrc@163.com
 * @date 2020/7/17 12:19
 */
public class PlayerActor extends MessageHandler<PlayerActor> implements IPlayer<PlayerActor> {

    private Player player;

    @Override
    public String getUuid() {
        return null;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
