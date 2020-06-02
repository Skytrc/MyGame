package com.fung.server.content.domain.backpack;

import com.fung.server.content.config.manager.GoodManager;
import com.fung.server.content.entity.OriGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 在使用前先给playerId、maxBackpackGrid赋值
 * @author skytrc@163.com
 * @date 2020/6/2 10:47
 */
@Component
public class PersonalBackpack {
    private Map<Integer, OriGood> backpack;

    /**
     * 背包最大格子数
     */
    private int maxBackpackGrid;

    private String playerId;

    @Autowired
    GoodManager goodManager;

    /**
     * 获取背包所有的物品
     * 一个小的想法。这里遍历完的数据是否可以放入缓存中，
     * 在没有改变背包的前提下，可以重新使用？
     */
    public List<OriGood> getAllGood() {
        List<OriGood> list = new LinkedList<>();
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
    public OriGood getGoodByPosition(int position) {
        if (!backpack.containsKey(position)){
            return null;
        }
        return backpack.get(position);
    }

    /**
     * 插入物品，判断背包中的物品是否有& if 背包中存在该物品，是否存在最大堆叠数
     * 插入不了返回null，注意处理空值
     */
    public OriGood addGood(int id, int num) {
        OriGood oriGood;
        // 直接判断数量是否大于最大堆叠数
        if (goodManager.getGoodMaxStack(id) <= num) {
            return null;
        }
        // 判断是否存在物品
        for (OriGood value : backpack.values()) {
            if (value.getGoodId() == id) {
                int newNum = num + value.getQuantity();
                if (newNum < goodManager.getGoodMaxStack(id)) {
                    value.setQuantity(newNum);
                    oriGood = value;
                    return oriGood;
                }
                return null;
            }
        }

        // 物品不存在背包
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                OriGood oriGood1 = new OriGood();
                oriGood1.setGoodId(id);
                oriGood1.setQuantity(num);
                oriGood1.setGetTime(System.currentTimeMillis());
                oriGood1.setPlayerId(playerId);
                oriGood1.setPosition(i);
                oriGood = oriGood1;
                // 放入背包
                backpack.put(i, oriGood1);
                return oriGood;
            }
        }
        return null;
    }

    /**
     *  直接把物品（如装备）加入背包
     *  背包满返回空值
     */
    public OriGood addGood(OriGood oriGood) {
        for (int i = 0; i < maxBackpackGrid; i++) {
            if (!backpack.containsKey(i)) {
                oriGood.setPosition(i);
                backpack.put(i, oriGood);
                return oriGood;
            }
        }
        return null;
    }

    /**
     * 使用物品（消耗物品）
     * 没有操作返回null
     * TODO Service层需要对num验证不能小于1
     */
    public OriGood useGood(int position, int num ) {
        if (backpack.containsKey(position)) {
            return null;
        }
        OriGood oriGood = backpack.get(position);
        int newNum = oriGood.getQuantity() - num;
        if (newNum < 0) {
            return null;
        } else if(newNum == 0) {
            return backpack.remove(position);
        }
        oriGood.setQuantity(newNum);
        // 重新把物品刷回背包
        backpack.put(oriGood.getPosition(), oriGood);
        return oriGood;
    }

    public void setBackpack(Map<Integer, OriGood> backpack) {
        this.backpack = backpack;
    }

    public Map<Integer, OriGood> getBackpack() {
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
