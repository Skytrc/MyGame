package com.fung.server.content.entity.map;

import com.fung.server.content.entity.Player;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 10:45
 */
public abstract class BaseMap {

    /**
     * 地图id
     */
    private int id;

    /**
     * 地图网格
     */
    private int[][] grid = new int[3][3];

    /**
     * 地图中在线上的玩家
     */
    private Map<Integer, Player> mapPlayers;

    /**
     * 设置传送门
     */
    private Map<int[][], BaseMap> gate;

    /**
     * 设置地图连接门
     *
     * @param coordinate 需要连接的坐标
     * @param map        连接下一个地图
     */
    public void setGate(int[][] coordinate, BaseMap map) {

    }

    public void addPlayer(Player player) {
        mapPlayers.put(player.getId(), player);
    }

    public void removePlayer(int playerId) {
        mapPlayers.remove(playerId);
    }

    /**
     * 角色地图移动
     * @param oldMap 移动前的地图
     * @param newMap 移动后的地图
     * @param player 需要移动的角色
     */
    public void playerMove(BaseMap oldMap, BaseMap newMap, Player player) {
        oldMap.removePlayer(player.getId());
        newMap.addPlayer(player);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public Map<Integer, Player> getMapPlayers() {
        return mapPlayers;
    }

    public void setMapPlayers(Map<Integer, Player> mapPlayers) {
        this.mapPlayers = mapPlayers;
    }

    public Map<int[][], BaseMap> getGate() {
        return gate;
    }

    public void setGate(Map<int[][], BaseMap> gate) {
        this.gate = gate;
    }
}
