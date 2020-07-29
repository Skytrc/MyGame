package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.good.GoodSpecies;
import com.fung.server.gameserver.content.entity.Medicine;
import com.fung.server.gameserver.content.config.good.equipment.EquipmentCreated;
import com.fung.server.gameserver.content.domain.equipment.EquipmentCreatedFactory;
import com.fung.server.gameserver.content.domain.good.GoodBaseInfo;
import com.fung.server.gameserver.content.domain.good.GoodEffect;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.entity.Good;
import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 11:09
 */
@Component
public class
GoodManager {

    @Autowired
    private MedicineManager medicineManager;

    @Autowired
    private EquipmentCreatedManager equipmentCreatedManager;

    private EquipmentCreatedFactory equipmentCreatedFactory;

    @Autowired
    private GoodEffect goodEffect;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    private Gson gson;

    public static final int GOOD_ID = 0;

    public static final int GOOD_NAME = 1;

    public static final int GOOD_DESCRIPTION = 2;

    public static final int GOOD_MAX_STACKS = 3;

    public static final int GOOD_VALUE = 4;

    public static final int EQUIPMENT_TYPE = 5;

    public GoodManager() {
        this.gson = new Gson();
    }

    public void goodInit() throws IOException, InvalidFormatException {
        medicineManager.medicineInit(gson);
        equipmentCreatedManager.equipmentCreatedInit();
    }

    /**
     * 创建新的物品，如果是装备，无论数量多少只返回一件
     */
    public Good createNewGood(int goodId, int goodQuantity, String playerId) {
        Good good = createNoBeingGood(goodId, goodQuantity);
        good.setUuid(playerId);
        return good;
    }

    public Good createNoBeingGood(int goodId, int goodQuantity) {
        if (isInRange(GoodSpecies.MEDICINCE, goodId)) {
            return medicineManager.createNoBeingNewMedicine(goodId, goodQuantity);
        } else if (isInRange(GoodSpecies.EQUIPMENT, goodId)) {
            return equipmentCreatedFactory.createNoBelongingEquipment(goodId);
        }
        return null;
    }

    /**
     * 物品信息，用于初始化从数据库中获得的数据
     */
    public String[] getGoodInfo(int goodId) {
        String[] res = new String[5];
        GoodBaseInfo goodBaseInfo;
        goodBaseInfo = getGoodInfoImplByGoodId(goodId);
        if (goodBaseInfo == null) {
            return null;
        }
        res[GOOD_ID] = String.valueOf(goodBaseInfo.getGoodId());
        res[GOOD_NAME] = goodBaseInfo.getName();
        res[GOOD_DESCRIPTION] = goodBaseInfo.getDescription();
        res[GOOD_MAX_STACKS] = String.valueOf(goodBaseInfo.getMaxStacks());
        res[GOOD_VALUE] = String.valueOf(goodBaseInfo.getValue());
        return res;
    }


    /**
     * 获取物品模板信息
     * 物品id 物品名字 物品描述
     */
    public String[] getGoodBaseInfoById(int goodId) {
        String[] res = new String[3];
        GoodBaseInfo goodBaseInfo;
        goodBaseInfo = getGoodInfoImplByGoodId(goodId);
        if (goodBaseInfo == null) {
            return null;
        }
        res[GOOD_ID] = String.valueOf(goodBaseInfo.getGoodId());
        res[GOOD_NAME] = goodBaseInfo.getName();
        res[GOOD_DESCRIPTION] = goodBaseInfo.getDescription();
        return res;
    }

    /**
     * 获取物品名称
     */
    public String getGoodNameById(int goodId) {
        GoodBaseInfo goodBaseInfo;
        goodBaseInfo = getGoodInfoImplByGoodId(goodId);
        if (goodBaseInfo == null) {
            return null;
        }
        return goodBaseInfo.getName();
    }

    public int getGoodMaxStack(int goodId) {
        return getGoodInfoImplByGoodId(goodId).getMaxStacks();
    }

    /**
     * 判断物品是否为装备
     */
    public boolean isEquipment(int goodId) {
        return isInRange(GoodSpecies.EQUIPMENT, goodId);
    }

    /**
     * 获取物品价值
     */
    public int getGoodValue(int goodId) {
        return getGoodInfoImplByGoodId(goodId).getValue();
    }


    /**
     * 通过ID获取对应的物品接口,在通过操作接口来获取对应的信息
     */
    public GoodBaseInfo getGoodInfoImplByGoodId(int goodId) {
        GoodBaseInfo goodBaseInfo;
        if (isInRange(GoodSpecies.MEDICINCE, goodId)) {
            Map<Integer, Medicine> medicineMap = medicineManager.getMedicineMap();
            goodBaseInfo = medicineMap.get(goodId);
        } else if (isInRange(GoodSpecies.EQUIPMENT, goodId)) {
            Map<Integer, EquipmentCreated> createdMap = equipmentCreatedManager.getEquipmentCreatedMap();
            goodBaseInfo = createdMap.get(goodId);
        } else {
            return null;
        }
        return goodBaseInfo;
    }

    /**
     * 需要从manager中获得新的物品信息，背包中的good不能向下转型。
     * 使用物品，根据物品id，分发到各个物品模块处理
     */
    public void useGood(Good good, PlayerActor playerActor, String channelId) {
        int goodId = good.getGoodId();
        // 获取物品模板
        good = getGoodTemplateById(goodId);
        writeMessage2Client.writeMessage(channelId, " 使用物品: " + good.getName());
        goodEffect.medicineEffect(good, playerActor, channelId);
    }

    /**
     * 获取物品模板， 装备则获取装备创建的模板
     */
    public Good getGoodTemplateById(int goodId) {
        if (isInRange(GoodSpecies.MEDICINCE, goodId)) {
            return medicineManager.getMedicineMap().get(goodId);
        } else if (isInRange(GoodSpecies.EQUIPMENT, goodId)) {
            // 返回的是装备创建的模板
            return equipmentCreatedManager.getEquipmentCreatedMap().get(goodId);
        }
        return null;
    }

    /**
     * 是否在某个范围内
     */
    public boolean isInRange(GoodSpecies goodSpecies, int goodId) {
        return (goodId >= goodSpecies.getStart() && goodId <= goodSpecies.getEnd());
    }

    /**
     * 根据装备ID获取装备最基础的描述
     */
    public String[] getEquipmentInfoById(int goodId) {
        if (isEquipment(goodId)) {
            String[] res = new String[6];
            Map<Integer, EquipmentCreated> createdMap = equipmentCreatedManager.getEquipmentCreatedMap();
            EquipmentCreated equipmentCreated = createdMap.get(goodId);
            res[GOOD_ID] = String.valueOf(equipmentCreated.getGoodId());
            res[GOOD_NAME] = equipmentCreated.getName();
            res[GOOD_DESCRIPTION] = equipmentCreated.getDescription();
            res[EQUIPMENT_TYPE] = equipmentCreated.getType();
            return res;
        }
        return null;
    }
}
