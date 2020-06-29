package com.fung.server.gameserver.content.domain.backpack;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;
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

    @Autowired
    GoodManager goodManager;

    public static final String SUCCEED_PUT_IN_BACKPACK = "成功放入背包";

    public static final String BACKPACK_FULL = "背包已满";

    public static final String REACH_MAX_STACKS = "物品达到最大堆叠数";

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
     * 背包中是否存在空位
     */
    public boolean hasGrid() {
        return backpack.size() < maxBackpackGrid;
    }

    /**
     * 判断能否达到最大堆叠数
     */
    public boolean reachMaxStack(int id, int num) {
        for (Good value : backpack.values()) {
            if (value.getGoodId() == id) {
                int newNum = num + value.getQuantity();
                // 达到最大堆叠数
                if (newNum > goodManager.getGoodMaxStack(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 需要先判断是否可以插入
     * 插入物品，判断背  包中的物品是否有& if 背包中存在该物品，是否存在最大堆叠数
     * 插入不了返回null，注意处理空值
     */
    @Deprecated
    public Good addGood(int id, int num) {
        Good good;
        // 直接判断需要加入背包的数量是否大于最大堆叠数
        if (goodManager.getGoodMaxStack(id) <= num) {
            return null;
        }

        // 如果是装备直接加入到背包中
        if (goodManager.isEquipment(id)) {

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
     * 检查并放入背包
     */
    public String checkAndAddGood(Good good) {
        // 如果是装备直接放入背包
        if (good.isHasEquipmentValue()) {
            addGood(good);
        }

        // 先检查背包中有没有该物品
        Good getBackpackGood = getGood(good);
        if (getBackpackGood != null) {
            // 检查是否达到最大堆叠数
            if (reachMaxStack(good.getGoodId(), good.getQuantity())) {
                return REACH_MAX_STACKS;
            } else {
                // 数量plus
                getBackpackGood.setQuantity(getBackpackGood.getQuantity() + good.getQuantity());
            }
        }
        return SUCCEED_PUT_IN_BACKPACK;
    }

    /**
     * 检查背包中是否存在同样的物品，如果存在返回，不存在返回空。
     */
    private Good getGood(Good good) {
        for (Map.Entry<Integer, Good> entry : backpack.entrySet()) {
            if (entry.getValue().getGoodId() == good.getGoodId()) {
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     *  直接把物品（如装备）加入背包
     *  背包满返回空值
     */
    public String addGood(Good good) {
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                good.setPosition(i);
                backpack.put(i, good);
                return "成功放入背包";
            }
        }
        return "背包已满";
    }

    /**
     * 使用物品（消耗物品）
     * 没有操作返回null
     * TODO 需要重写
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
        String message = addGood(equipment);
        if (message != SUCCEED_PUT_IN_BACKPACK) {
            return null;
        }
        equipment.setPosition(equipment.getPosition());
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
