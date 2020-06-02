package com.fung.server.content.config;

import com.fung.server.content.entity.Player;
import com.fung.server.content.entity.base.BaseElement;
import com.fung.server.excel2class.Model;

import java.util.Map;


/**
 * 地图实体
 * @author skytrc@163.com
 * @date 2020/5/13 18:16
 */
public class GameMap extends BaseElement implements Model {

    private static final int X = 0;

    private static final int Y = 1;

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
     * 地图中在线上的玩家,以玩家id作为key，value为玩家
     */
    private Map<String, Player> mapPlayers;

    /**
     * 元素对应的地图坐标（坐标需要计算）key location  value 基础元素
     */
    private Map<Integer, BaseElement> elements;

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

    @Deprecated
    public String showPlayer () {
        StringBuilder res = new StringBuilder("地图: " + getName() + " 有玩家" );
        for (Map.Entry<String, Player> entry: mapPlayers.entrySet()) {
            res.append(entry.getValue().getPlayerName()).append(" 、");
        }
        return res.toString();
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
     * 角色地图移动
     *
     * @param oldMap 移动前的地图
     * @param newMap 移动后的地图
     * @param player 需要移动的角色
     */
    public void playerMove(GameMap oldMap, GameMap newMap, Player player) {
        oldMap.removePlayer(player.getUuid());
        newMap.addPlayer(player);
    }

    /**
     * 地图添加线上玩家信息
     * @param player 玩家信息
     */
    public void addPlayer(Player player) {
        mapPlayers.put(player.getUuid(), player);
    }

    /**
     * 地图删除线上玩家信息
     * @param playerUuid 玩家id
     */
    public void removePlayer(String playerUuid) {
        mapPlayers.remove(playerUuid);
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

    public Map<String, Player> getMapPlayers() {
        return mapPlayers;
    }

    public void setMapPlayers(Map<String, Player> mapPlayers) {
        this.mapPlayers = mapPlayers;
    }

    public Map<Integer, BaseElement> getElements() {
        return elements;
    }

    public void setElements(Map<Integer, BaseElement> elements) {
        this.elements = elements;
    }
}
