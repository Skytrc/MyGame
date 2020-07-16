package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.dao.EquipmentDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.calculate.PlayerValueCalculate;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/8 18:02
 */
@Component
public class GoodServiceImpl implements GoodService {

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private PlayerValueCalculate playerValueCalculate;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Override
    public String useGood(int position, String channelId) {
        return null;
    }

    @Override
    public String putOnEquipment(int backpackPosition, String channelId) {
        Player currentPlayer = playerInfo.getCurrentPlayer(channelId);
        PersonalBackpack backpack = currentPlayer.getPersonalBackpack();
        List<Equipment> currentPlayerEquipments = currentPlayer.getEquipments();
        if (backpack.getGoodByPosition(backpackPosition) == null) {
            return "该格子没有物品";
        }
        if (!backpack.isEquipment(backpackPosition)) {
            return "无法穿戴，该物品不是装备";
        }
        Equipment equipment = backpack.removeEquipment(backpackPosition);
        // 需要装备到身上的位置
        int bodyPosition = equipment.getType(    ).getPosition();
        equipment.setPosition(-1);
        // 如果身上装备位置有已有装备，先卸到背包中
        if (currentPlayer.getEquipments().size() > bodyPosition && currentPlayerEquipments.get(bodyPosition) != null) {
            Equipment removeEquipment = currentPlayerEquipments.get(bodyPosition);
            currentPlayerEquipments.set(bodyPosition, new Equipment());
            backpack.addEquipment(removeEquipment);
        }
        // 装备到身上的数组中
        currentPlayerEquipments.set(bodyPosition, equipment);

        equipmentDao.updateEquipment(equipment);
        // 计算基础数值
        playerValueCalculate.calculatePlayerBaseValue(currentPlayer);

        return "穿戴装备成功" + playerInfo.showPlayerValue(currentPlayer);
    }

    @Override
    public String takeOffEquipment(int equipmentPosition, String channelId) {
        Player currentPlayer = playerInfo.getCurrentPlayer(channelId);
        List<Equipment> equipments = currentPlayer.getEquipments();
        int size = equipments.size();
        if (size > equipmentPosition) {
            // 放入背包
            Equipment currentTakeOffEquipment = equipments.get(equipmentPosition);
            if (currentTakeOffEquipment.getName() == null) {
                return "该位置没有装备";
            }
            equipments.set(equipmentPosition, new Equipment());
            PersonalBackpack personalBackpack = currentPlayer.getPersonalBackpack();
            personalBackpack.addEquipment(currentTakeOffEquipment);
            equipmentDao.updateEquipment(currentTakeOffEquipment);
            playerValueCalculate.calculatePlayerBaseValue(currentPlayer);
            return "脱下装备成功" + playerInfo.showPlayerValue(currentPlayer);
        }
        return "该位置没有装备";
    }

    @Override
    public String pickUp(String channelId) {
        Player player = playerInfo.getCurrentPlayer(channelId);
        GameMapActor gameMapActor = mapManager.getGameMapActor(player);
        GameMap gameMap = gameMapActor.getGameMap();
        gameMapActor.addMessage(gameMapActor1 -> {
            FallingGood fallingGood = gameMap.getFallingGood(player);
            Good good = fallingGood.getGood();
            if (good == null) {
                writeMessage2Client.writeMessage(channelId, "该位置没有物品");
                return;
            }
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            String res = personalBackpack.checkAndAddGood(good, goodManager);
            if (res.equals(PersonalBackpack.SUCCEED_PUT_IN_BACKPACK)) {
                res = "\n捡起: " + good.getName() + " 数量: " + good.getQuantity() + " " + res;
            }
            writeMessage2Client.writeMessage(channelId, res);
            // 设置为null 等虚拟机回收
            fallingGood = null;
        });
        return "";
    }

    @Override
    public String pickUp(String channelId, String goodName) {
        return null;
    }
}
