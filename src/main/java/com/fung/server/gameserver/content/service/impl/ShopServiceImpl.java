package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.npc.NonPlayerCharacter;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.npc.NpcInfo;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/18 11:36
 */
@Component
public class ShopServiceImpl implements ShopService {

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private NpcInfo npcInfo;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String buyGood(String channelId, int goodId, int num) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            if (!npcInfo.hasNpc(player)) {
                writeMessage2Client.writeMessage(channelId, "\n该位置没有NPC");
                return;
            }
            NonPlayerCharacter npc = npcInfo.getNpcByPlayerInfo(player);
            if (!npc.hasShop()) {
                writeMessage2Client.writeMessage(channelId, "\nNPC没有商店功能");
                return;
            }
            if (!npc.containGood(goodId)) {
                writeMessage2Client.writeMessage(channelId, "\nNPC不出售该物品");
                return;
            }
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            // 判断背包是否已满
            if (!personalBackpack.hasGrid()) {
                writeMessage2Client.writeMessage(channelId, "\n背包已满！\n");
                return;
            }

            // 非装备判断
            if (!goodManager.isEquipment(goodId)) {
                // 判断背包是否存在该物品且加起来会不会超过最大丢叠数
                if (!personalBackpack.reachMaxStack(goodId, num, goodManager.getGoodMaxStack(goodId))) {
                    writeMessage2Client.writeMessage(channelId,  "\n超过最大堆叠数");
                    return;
                }
            }

            // 检查金钱是否足够
            int money = player.getPlayerCommConfig().getMoney();
            int goodValue = goodManager.getGoodValue(goodId);
            if (num * goodValue > money) {
                writeMessage2Client.writeMessage(channelId, "\n没有足够的金钱购买物品");
                return;
            }
            Good newGood = goodManager.createNewGood(goodId, num, player.getUuid());
            String s = personalBackpack.checkAndAddGood(newGood, goodDao);
            writeMessage2Client.writeMessage(channelId, "\n成功商品，编号为:" + goodId + " 数量为: " + num +
                    " 花费:" + num * goodValue + " 当前余额: " + player.getPlayerCommConfig().getMoney() + "\n" + s);
        });

        return "";
    }

    @Override
    public String shellGood(String channelId, int backpackPosition, int quantity) {
        PlayerActor playerActor = playerInfo.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            if (!npcInfo.hasNpc(player)) {
                writeMessage2Client.writeMessage(channelId, "\n该位置没有NPC");
                return;
            }
            NonPlayerCharacter npc = npcInfo.getNpcByPlayerInfo(player);
            if (!npc.hasShop()) {
                writeMessage2Client.writeMessage(channelId, "\nNPC没有商店功能");
                return;
            }
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            if (!personalBackpack.positionHasGood(backpackPosition)) {
                writeMessage2Client.writeMessage(channelId, "\n该位置没有物品");
                return;
            }
            Good need2ShellGood = personalBackpack.getGoodByPosition(backpackPosition);
            if (need2ShellGood.getQuantity() < quantity) {
                writeMessage2Client.writeMessage(channelId, "\n数量错误，无法超过已有的数量");
                return;
            }
            int addMoney = quantity * goodManager.getGoodValue(need2ShellGood.getGoodId());
            if (goodManager.isEquipment(need2ShellGood.getGoodId())) {
                personalBackpack.useGood(backpackPosition);
                goodDao.deleteGood(need2ShellGood);
            } else {
                // 移除商品
                personalBackpack.useGood(backpackPosition, quantity);
                // 如果商品数量为0，数据库删除记录
                if (need2ShellGood.getQuantity() == 0) {
                    goodDao.deleteGood(need2ShellGood);
                } else {
                    goodDao.insertOrUpdateGood(need2ShellGood);
                }
            }
            // 加钱
            player.getPlayerCommConfig().addMoney(addMoney);
            writeMessage2Client.writeMessage(channelId, "\n成功出售商品: " + need2ShellGood.getName() + " 获得金钱:"
                    + addMoney + " 当前金钱数量: " + player.getPlayerCommConfig().getMoney());
        });
        return "";
    }
}
