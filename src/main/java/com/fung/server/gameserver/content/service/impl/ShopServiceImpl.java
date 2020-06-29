package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author skytrc@163.com
 * @date 2020/6/18 11:36
 */
public class ShopServiceImpl implements ShopService {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    GoodManager goodManager;

    @Override
    public String buyGood(String channelId, int goodId, int num) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        PersonalBackpack personalBackpack = player.getPersonalBackpack();
        // 判断背包是否已满
        if (!personalBackpack.hasGrid()) {
            return "\n背包已满！\n";
        }

        // 判断背包是否存在该物品且加起来会不会超过最大丢叠数
        if (!personalBackpack.reachMaxStack(goodId, num)) {
            return "\n超过最大堆叠数";
        }

        // 检查金钱是否足够
        int money = player.getPlayerCommConfig().getMoney();

        return null;
    }

    @Override
    public String shellGood(String channelId, int backpackNum, int quantity) {
        return null;
    }
}
