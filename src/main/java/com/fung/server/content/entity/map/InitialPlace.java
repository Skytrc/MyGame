package com.fung.server.content.entity.map;

import com.fung.server.content.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始之地实体
 *
 * @author skytrc@163.com
 * @date 2020/4/30 10:56
 */
public class InitialPlace extends BaseMap {

    private static InitialPlace instance = new InitialPlace();

    private InitialPlace() {
        // 设置地图基本信息
        setId(1);
        setName("初始之地");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    public static InitialPlace getInstance() {
        return instance;
    }

    @Override
    public void init() {
        // 设置地图传送门
        Village village = Village.getInstance();
        addGate(village.getName(), village);
        addElement(4, 2, village.getName());
    }

}
