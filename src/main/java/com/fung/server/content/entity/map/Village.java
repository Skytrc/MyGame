package com.fung.server.content.entity.map;

import com.fung.server.content.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/4 14:58
 */
public class Village extends BaseMap {

    private static Village instance = new Village();

    private Village() {
        // 设置地图基本信息
        setId(2);
        setName("村庄");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    public static Village getInstance() {
        return instance;
    }

    @Override
    public void init() {
        // 设置地图传送门
        InitialPlace initialPlace = InitialPlace.getInstance();
        Castle castle = Castle.getInstance();
        Forest forest = Forest.getInstance();
        addGate(initialPlace.getName(), initialPlace);
        addElement(0, 2, initialPlace.getName());
        addGate(castle.getName(), castle);
        addElement(4, 2, castle.getName());
        addGate(forest.getName(), forest);
        addElement(2, 4, forest.getName());
    }

}
