package com.fung.server.gameserver.content.domain.good;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.domain.equipment.EquipmentCreatedFactory;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/5 9:54
 */
@Component
public class NewPlayerGoodCreatedFactory {

    @Autowired
    private EquipmentCreatedFactory factory;

    @Autowired
    private GoodManager goodManager;

    /**
     * TODO 这里可以用配置来优化
     * 玩家注册时获得的一般物品（如药物等）
     */
    public List<Good> newPlayerGoodCreated(String playerId) {
        List<Good> goods = new ArrayList<>();
        goods.add(goodManager.createNewGood(1, 10, playerId));
        goods.add(goodManager.createNewGood(2, 10, playerId));
        return goods;
    }

    /**
     * TODO 这里可以用配置来优化
     * 玩家注册时获得的装备
     */
    public List<Equipment> newPlayerEquipmentCreated(String playerId) {
        List<Equipment> equipments = new ArrayList<>(5);
        equipments.add(factory.createNewEquipment(playerId, 100));
        equipments.add(factory.createNewEquipment(playerId, 101));
        equipments.add(factory.createNewEquipment(playerId, 102));
        equipments.add(factory.createNewEquipment(playerId, 103));
        equipments.add(factory.createNewEquipment(playerId, 104));
        return equipments;
    }

//    /**
//     * 创建一个新的物品
//     * @param quantity 数量
//     */
//    public Good createdNewGood(String playerId, int goodId, int quantity) {
//        Good good = createNoBelongingGood(goodId, quantity);
//        good.setPlayerId(playerId);
//        return good;
//    }
//
//    /**
//     * 创建没有PlayerID的物品
//     */
//    public Good createNoBelongingGood(int goodId, int quantity) {
//        if (isEquipment(goodId)) {
//            return factory.createNoBelongingEquipment(goodId);
//        }
//        Good good = new Good();
//        good.setName(goodManager.getGoodNameById(goodId));
//        good.setUuid(Uuid.createUuid());
//        good.setGetTime(System.currentTimeMillis());
//        good.setGoodId(goodId);
//        good.setQuantity(quantity);
//        return good;
//    }
}
