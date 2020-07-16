package com.fung.server.gameserver.content.config.map;

import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.base.BaseElement;
import com.fung.server.gameserver.excel2class.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 地图实体 注意需要copy的类型
 * @author skytrc@163.com
 * @date 2020/5/13 18:16
 */
public class GameMap extends BaseElement implements Model {

    public static final int X = 0;

    public static final int Y = 1;

    /**
     * 地图x轴
     */
    private int x;

    /**
     * 地图y轴
     */
    private int y;

    /**
     * 设置地图传送门,key为位置，value为地图id
     */
    private Map<Integer, Integer> gates;

    /**
     * 格子对应玩家
     */
    private Map<Integer, List<Player>> playerInPosition;

    /**
     * 地图中玩家数量
     */
    private int playerQuantity;

    /**
     * key position  value 怪物 地图存储
     */
    private Map<Integer, NormalMonster> monsterMap;

    /**
     * 元素对应的地图坐标（坐标需要计算）key location  value 基础元素
     */
    private Map<Integer, BaseElement> elements;

    /**
     * 存储掉落物品
     */
    private Map<Integer, List<FallingGood>> fallingGoodMap;

    public GameMap() {
        this.setFriendly(true);
    }

    /**
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 位置
     */
    public int xy2Location(int x, int y) {
        return (x - 1) * this.x + y;
    }

    /**
     * @param location 位置
     * @return x,y坐标
     */
    public int[] location2xy(int location) {
        int[] xY = new int[2];
        xY[X] = location % x == 0 ? location / x : location / x + 1;
        xY[Y] = location % x == 0 ? y : location % x;
        return xY;
    }

    /**
     * 添加传送门,添加元素
     * @param location 位置
     * @param gameMap 下一张地图
     */
    public void addGate(int location, GameMap gameMap) {
        gates.put(location, gameMap.getId());
        elements.put(location, gameMap);
    }

    /**
     * 获得下一张地图的id
     * @param location 位置
     * @return 下一张地图id
     */
    public int getGateId(int location) {
        return gates.get(location);
    }


    /**
     * 设置地图上的元素
     *
     * @param location 地图位置
     * @param element 元素，比如NPC，传送门等等
     */
    public void addElement(int location, BaseElement element) {
        elements.put(location, element);
    }

    /**
     * 展示地图中的元素
     *
     * @return 所有方格中的元素
     */
    @Deprecated
    public String showMapInfo() {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Integer, BaseElement> entry : elements.entrySet()) {
            Integer location = entry.getKey();
            String elementName = entry.getValue().getName();
            int[] xy = location2xy(location);
            res.append("位置 [").append(xy[0]).append(",").append(xy[1]).append("]")
                    .append(" 拥有 ").append(elementName).append("\n");
        }
        return res.toString();
    }

    /**
     * 展示某一格的元素
     *
     * @param x 纵坐标
     * @param y 横坐标
     * @return 指定格数的元素
     */
    @Deprecated
    public String showThisGridInfo(int x, int y) {
        return "[" + x + "," + y + "]" + " 拥有 " + elements.get(xy2Location(x, y));
    }

    /**
     * 判断某个坐标中是否含有某个元素
     *
     * @param x       纵坐标
     * @param y       横坐标
     * @param element 元素
     */
    public boolean hasElement(int x, int y, String element) {
        return elements.get(xy2Location(x, y)).getName().equals(element);
    }

    /**
     * 判断当前位置是否有传送门，有则返回传送门id,没有返回-1
     * @param x 纵坐标
     * @param y 横坐标
     * @return 传送门id or -1
     */
    public int hasGate(int x, int y) {
        int location = xy2Location(x, y);
        if (gates.get(location) != null) {
            return gates.get(location);
        }
        return -1;
    }

    /**
     * 判断该格是否有元素
     * @param x 纵坐标
     * @param y 横坐标
     */
    public boolean hasElement(int x, int y) {
        return elements.get(xy2Location(x, y)).getName() != null;
    }

    /**
     * 从地图中获取元素 x, y
     */
    public BaseElement getElementByXy(int x, int y) {
        return elements.get(xy2Location(x, y));
    }

    /**
     * 从地图中获取元素 position
     */
    public BaseElement getElementByLocation(int position) {
        return elements.get(position);
    }

    /**
     * 角色地图移动
     *
     * @param oldMap 移动前的地图
     * @param newMap 移动后的地图
     * @param player 需要移动的角色
     */
    public void playerMove(GameMap oldMap, GameMap newMap, Player player) {
        oldMap.removePlayer(player);
        newMap.addPlayer(player);
    }

    /**
     * 玩家移动
     */
    public void playerMove(Player player, int oldX, int oldY) {
        playerInPosition.get(xy2Location(oldX, oldY)).remove(player);
        int newPosition = xy2Location(player.getInMapX(), player.getInMapY());
        checkPositionMap(newPosition);
        playerInPosition.get(newPosition).add(player);
    }

    /**
     * 地图添加线上玩家信息
     * @param player 玩家信息
     */
    public void addPlayer(Player player) {
        playerQuantity++;
        int position = xy2Location(player.getInMapX(), player.getInMapY());
        checkPositionMap(position);
        playerInPosition.get(position).add(player);
    }

    /**
     * 检查是否存在 playerInPosition 是否存在list
     */
    public void checkPositionMap(int position) {
        if (!playerInPosition.containsKey(position)){
            playerInPosition.put(position, new ArrayList<>());
        }
    }

    /**
     * 地图删除线上玩家信息
     * @param player 玩家
     */
    public void removePlayer(Player player) {
        playerQuantity--;
        playerInPosition.get(xy2Location(player.getInMapX(), player.getInMapY())).remove(player);
    }

    /**
     * 获取怪兽实体 x y
     */
    public NormalMonster getMonsterByXy(int x, int y) {
        return monsterMap.get(xy2Location(x, y));
    }

    /**
     * 获取该位置中的一件物品(无主，或自己的物品)
     */
    public FallingGood getFallingGood(Player player) {
        int location = xy2Location(player.getInMapX(), player.getInMapY());
        if (!fallingGoodMap.containsKey(location)) {
            return null;
        }
        // 数组可能为空
        if (fallingGoodMap.get(location).size() == 0){
            return null;
        }
        FallingGood fallingGood = fallingGoodMap.get(location).get(0);
        if (fallingGood.getBeingToPlayer() == null || fallingGood.getBeingToPlayer() == player) {
            fallingGoodMap.get(location).remove(fallingGood);
            return fallingGood;
        }
        return null;
    }

    /**
     * 根据物品名字拾取物品
     */
    public FallingGood getFallingGoodByName(int location, String fallingGoodName, Player player) {
        if (!fallingGoodMap.containsKey(location)) {
            return null;
        }
        // 数组可能为空
        if (fallingGoodMap.get(location).size() == 0){
            return null;
        }
        List<FallingGood> fallingGoods = fallingGoodMap.get(location);
        for (FallingGood fallingGood : fallingGoods) {
            if (fallingGood.getName().equals(fallingGoodName)) {
                if (fallingGood.getBeingToPlayer() == null || fallingGood.getBeingToPlayer() == player) {
                    fallingGoods.remove(fallingGood);
                    return fallingGood;
                }
            }
        }
        return null;
    }

    public boolean checkPositionHasFallingGood(int position) {
        return !(fallingGoodMap.get(position) == null
                || fallingGoodMap.get(position).size() == 0);
    }

    /**
     * 把一组（List）掉落物品放入FallingGoodMap中
     */
    public void putFallingGoodInMap(List<FallingGood> fallingGoodList, int x, int y) {
        putFallingGoodInMap(fallingGoodList, xy2Location(x, y));
    }

    public void putFallingGoodInMap(List<FallingGood> fallingGoodList, int location) {
        if (fallingGoodMap.containsKey(location)) {
            fallingGoodMap.get(location).addAll(fallingGoodList);
        } else {
            fallingGoodMap.put(location, fallingGoodList);
        }
    }

    public void putFallingGoodInMap(FallingGood fallingGood, int x, int y) {
        putFallingGoodInMap(fallingGood, xy2Location(x, y));
    }

    /**
     * 添加掉落物品
     */
    public void putFallingGoodInMap(FallingGood fallingGood, int location) {
        if (fallingGoodMap.containsKey(location)) {
            fallingGoodMap.get(location).add(fallingGood);
        } else {
            List<FallingGood> fallingGoods = new ArrayList<>();
            fallingGoods.add(fallingGood);
            fallingGoodMap.put(location, fallingGoods);
        }
    }

    public boolean hasPlayer() {
        return playerQuantity > 0;
    }

    public NormalMonster getMonsterByPosition(int position) {
        return monsterMap.get(position);
    }

    public void putMonsterInMap(int position, NormalMonster normalMonster) {
        monsterMap.put(position, normalMonster);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Map<Integer, Integer> getGates() {
        return gates;
    }

    public void setGates(Map<Integer, Integer> gates) {
        this.gates = gates;
    }

    public Map<Integer, BaseElement> getElements() {
        return elements;
    }

    public void setElements(Map<Integer, BaseElement> elements) {
        this.elements = elements;
    }

    public Map<Integer, NormalMonster> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<Integer, NormalMonster> monsterMap) {
        this.monsterMap = monsterMap;
    }

    public Map<Integer, List<Player>> getPlayerInPosition() {
        return playerInPosition;
    }

    public void setPlayerInPosition(Map<Integer, List<Player>> playerInPosition) {
        this.playerInPosition = playerInPosition;
    }

    public void setFallingGoodMap(Map<Integer, List<FallingGood>> fallingGoodMap) {
        this.fallingGoodMap = fallingGoodMap;
    }

    public int getPlayerQuantity() {
        return playerQuantity;
    }

    public void setPlayerQuantity(int playerQuantity) {
        this.playerQuantity = playerQuantity;
    }
}
