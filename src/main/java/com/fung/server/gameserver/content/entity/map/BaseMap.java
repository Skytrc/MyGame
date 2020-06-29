package com.fung.server.gameserver.content.entity.map;

import com.fung.server.gameserver.content.entity.Player;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 10:45
 */
@Deprecated
public abstract class BaseMap {

    /**
     * 地图id
     */
    private int id;

    /**
     * 地图名称
     */
    private String name;

    /**
     * 地图网格
     */
    private String[][] grid = new String[5][5];

    /**
     * 地图中在线上的玩家,以玩家id作为key，value为玩家
     */
    private Map<String, Player> mapPlayers;

    /**
     * 传送元素对应的地图
     */
    private Map<String, BaseMap> gates;

    public void addPlayer(Player player) {
        mapPlayers.put(player.getUuid(), player);
    }

    public void removePlayer(String playerId) {
        mapPlayers.remove(playerId);
    }

    /**
     * 角色地图移动
     *
     * @param oldMap 移动前的地图
     * @param newMap 移动后的地图
     * @param player 需要移动的角色
     */
    public void playerMove(BaseMap oldMap, BaseMap newMap, Player player) {
        oldMap.removePlayer(player.getUuid());
        newMap.addPlayer(player);
    }

    /**
     * 设置地图上的元素
     *
     * @param x       纵坐标
     * @param y       横坐标
     * @param element 元素，比如NPC，传送门等等
     */
    public void addElement(int x, int y, String element) {
        grid[x][y] = element;
    }

    /**
     * 展示地图中的元素
     *
     * @return 所有方格中的元素
     */
    public String showMapInfo() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null) {
                    res.append("位置 [").append(i).append(",").append(j).append("]")
                            .append(" 拥有 ").append(grid[i][j]).append("\n");
                }
            }
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
    public String showThisGridInfo(int x, int y) {
        return "[" + x + "," + y + "]" + " 拥有 " + grid[x][y];
    }

    /**
     * 判断某个坐标中是否含有某个元素
     *
     * @param x       纵坐标
     * @param y       横坐标
     * @param element 元素
     */
    public boolean hasElement(int x, int y, String element) {
        return grid[x][y].equals(element);
    }

    /**
     * 添加传送门，用map存储，名字对应地图
     *
     * @param name 地图对应的中文名
     * @param map  地图类
     */
    public void addGate(String name, BaseMap map) {
        gates.put(name, map);
    }


    /**
     * 地图某些元素初始化
     */
    public void init() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public Map<String, Player> getMapPlayers() {
        return mapPlayers;
    }

    public void setMapPlayers(Map<String, Player> mapPlayers) {
        this.mapPlayers = mapPlayers;
    }

    public Map<String, BaseMap> getGates() {
        return gates;
    }

    public void setGates(Map<String, BaseMap> gates) {
        this.gates = gates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
