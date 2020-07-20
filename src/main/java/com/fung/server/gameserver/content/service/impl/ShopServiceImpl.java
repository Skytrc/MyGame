package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author skytrc@163.com
 * @date 2020/6/18 11:36
 */
public class ShopServiceImpl implements ShopService {

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String buyGood(String channelId, int goodId, int num) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            // 判断背包是否已满
            if (!personalBackpack.hasGrid()) {
                writeMessage2Client.writeMessage(channelId, "\n背包已满！\n");
            }

            // 判断背包是否存在该物品且加起来会不会超过最大丢叠数
            if (!personalBackpack.reachMaxStack(goodId, num, goodManager.getGoodMaxStack(goodId))) {
                writeMessage2Client.writeMessage(channelId,  "\n超过最大堆叠数");
            }

            // 检查金钱是否足够
            int money = player.getPlayerCommConfig().getMoney();

        });

        return "";
    }

    @Override
    public String shellGood(String channelId, int backpackNum, int quantity) {
        return null;
    }
}
