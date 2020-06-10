package com.fung.server.content.domain.backpack;

import com.fung.server.content.config.manager.GoodManager;
import com.fung.server.content.entity.Equipment;
import com.fung.server.content.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 在使用前先给playerId、maxBackpackGrid赋值
 * @author skytrc@163.com
 * @date 2020/6/2 10:47
 */
public class PersonalBackpack {

    /**
     * 背包 key position   value good
     */
    private Map<Integer, Good> backpack;

    /**
     * 存储装备详细信息， key position  value 装备信息
     */
    private Map<Integer, Equipment> equipmentMap;

    /**
     * 背包最大格子数
     */
    private int maxBackpackGrid;

    private String playerId;

    @Autowired
    GoodManager goodManager;

    public PersonalBackpack() {
        backpack = new HashMap<>();
        equipmentMap = new HashMap<>();
    }

    /**
     * 获取背包所有的物品
     * 一个小的想法。这里遍历完的数据是否可以放入缓存中，
     * 在没有改变背包的前提下，可以重新使用？
     */
    public List<Good> getAllGood() {
        List<Good> list = new LinkedList<>();
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                continue;
            }
            list.add(backpack.get(i));
        }
        return list;
    }

    /**
     * 获取某一个的物品信息，没有则返回空
     */
    public Good getGoodByPosition(int position) {
        if (!backpack.containsKey(position)){
            return null;
        }
        return backpack.get(position);
    }

    /**
     * 插入物品，判断背包中的物品是否有& if 背包中存在该物品，是否存在最大堆叠数
     * 插入不了返回null，注意处理空值
     */
    public Good addGood(int id, int num) {
        Good good;
        // 直接判断数量是否大于最大堆叠数
        if (goodManager.getGoodMaxStack(id) <= num) {
            return null;
        }
        // 判断是否存在物品
        for (Good value : backpack.values()) {
            if (value.getGoodId() == id) {
                int newNum = num + value.getQuantity();
                if (newNum < goodManager.getGoodMaxStack(id)) {
                    value.setQuantity(newNum);
                    good = value;
                    return good;
                }
                return null;
            }
        }

        // 物品不存在背包
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                Good good1 = new Good();
                good1.setGoodId(id);
                good1.setQuantity(num);
                good1.setGetTime(System.currentTimeMillis());
                good1.setPlayerId(playerId);
                good1.setPosition(i);
                good = good1;
                // 放入背包
                backpack.put(i, good1);
                return good;
            }
        }
        return null;
    }

    /**
     *  直接把物品（如装备）加入背包
     *  背包满返回空值
     */
    public Good addGood(Good good) {
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                good.setPosition(i);
                backpack.put(i, good);
                return good;
            }
        }
        return null;
    }

    /**
     * 使用物品（消耗物品）
     * 没有操作返回null
     * TODO Service层需要对num验证不能小于1
     */
    public Good useGood(int position, int num) {
        if (backpack.containsKey(position)) {
            return null;
        }
        Good good = backpack.get(position);
        int newNum = good.getQuantity() - num;
        if (newNum < 0) {
            return null;
        } else if(newNum == 0) {
            return backpack.remove(position);
        }
        good.setQuantity(newNum);
        // 重新把物品刷回背包
        backpack.put(good.getPosition(), good);
        return good;
    }

    /**
     * 添加装备，背包添加装备，在equipmentMap中添加
     */
    public Equipment addEquipment(Equipment equipment) {
        Good good = addGood(equipment);
        if (good == null) {
            return null;
        }
        equipment.setPosition(good.getPosition());
        equipmentMap.put(equipment.getPosition(), equipment);
        return equipment;
    }

    /**
     * 移除装备（一般将装备放到玩家身上）
     */
    public Equipment removeEquipment(int position) {
        backpack.remove(position);
        return equipmentMap.remove(position);
    }

    /**
     * 获取装备信息，如果没有则返回空
     */
    public Equipment getEquipment(int position) {
        if (isEquipment(position)) {
            return equipmentMap.get(position);
        }
        return null;
    }

    /**
     * 判断物品是否为装备
     */
    public boolean isEquipment(int position) {
        return equipmentMap.containsKey(position);
    }

    public void setBackpack(Map<Integer, Good> backpack) {
        this.backpack = backpack;
    }

    public Map<Integer, Good> getBackpack() {
        return backpack;
    }

    public int getMaxBackpackGrid() {
        return maxBackpackGrid;
    }

    public void setMaxBackpackGrid(int maxBackpackGrid) {
        this.maxBackpackGrid = maxBackpackGrid;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setEquipmentMap(Map<Integer, Equipment> equipments) {
        this.equipmentMap = equipments;
    }

    public Map<Integer, Equipment> getEquipmentMap() {
        return equipmentMap;
    }
}
