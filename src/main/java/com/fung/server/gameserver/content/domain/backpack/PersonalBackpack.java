package com.fung.server.gameserver.content.domain.backpack;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;

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
//    private Map<Integer, Equipment> equipmentMap;

    /**
     * 背包最大格子数
     */
    private int maxBackpackGrid;

    private String playerId;

    public PersonalBackpack() {
        backpack = new HashMap<>();
    }

    /**
     * 获取背包所有的物品
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
    public boolean reachMaxStack(int id, int num, int maxNum) {
        for (Good value : backpack.values()) {
            if (value.getGoodId() == id) {
                int newNum = num + value.getQuantity();
                // 达到最大堆叠数
                if (newNum > maxNum) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查并放入背包
     */
    public String checkAndAddGood(Good good, GoodManager goodManager) {
        // 如果是装备直接放入背包
        if (goodManager.isEquipment(good.getGoodId())) {
            return addGood(good);
        }

        // 先检查背包中有没有该物品
        Good getBackpackGood = getGood(good);
        if (getBackpackGood != null) {
            // 检查是否达到最大堆叠数
            int goodMaxStack = goodManager.getGoodMaxStack(good.getGoodId());
            if (reachMaxStack(good.getGoodId(), good.getQuantity(), goodMaxStack)) {
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
                return SUCCEED_PUT_IN_BACKPACK;
            }
        }
        return BACKPACK_FULL;
    }

    /**
     * 使用物品
     * 没有操作返回null
     */
    public Good useGood(int position, int num) {
        Good good = backpack.get(position);
        int newNum = good.getQuantity() - num;
        if (newNum < 0) {
            return null;
        } else if(newNum == 0) {
            return backpack.remove(position);
        }
        good.setQuantity(newNum);
        return good;
    }

    /**
     * 使用物品（1次）例如装备等
     */
    public Good useGood(int position) {
        return useGood(position, 1);
    }

    /**
     * 检查位置有没有物品
     */
    public boolean positionHasGood(int position) {
        return backpack.get(position) != null;
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

}
