package com.fung.server.content.entity.map;

import com.fung.server.content.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/4 15:07
 */
public class Forest extends BaseMap {

    private static Forest instance = new Forest();

    private Forest() {
        // 设置地图基本信息
        setId(3);
        setName("森林");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    public static Forest getInstance() {
        return instance;
    }

    @Override
    public void init() {
        // 设置地图传送门
        Village village = Village.getInstance();
        addGate(village.getName(), village);
        addElement(2, 0, village.getName());
    }
}
