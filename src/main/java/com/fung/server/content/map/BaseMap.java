package com.fung.server.content.map;

import com.fung.server.content.player.Player;

import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 10:45
 */
public abstract class BaseMap {

//    private BaseMap() {}
    /**
     * 地图id
     */
    public int id;

    /**
     * 地图网格
     */
    public int[][] grid = new int[3][3];

    /**
     * 地图中在线上的玩家
     */
    public Map<Integer, Player> mapPlayers;

    /**
     * 设置地图id
     */
    public abstract void setId();

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }


    /**
     * 设置地图连接门
     * @param coordinate 需要连接的地图
     * @param map 连接下一个地图
     */
    public abstract void setGate (int[][] coordinate, BaseMap map);

    public int getId() {
        return id;
    }

    public int[][] getGrid() {
        return grid;
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

}
