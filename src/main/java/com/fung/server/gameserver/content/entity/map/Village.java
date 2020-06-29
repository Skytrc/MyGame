package com.fung.server.gameserver.content.entity.map;

import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/4 14:58
 */
@Deprecated
@Component
public class Village extends BaseMap {

    @Autowired
    private InitialPlace initialPlace;

    @Autowired
    private Castle castle;

    @Autowired
    private Forest forest;

    private Village() {
        // 设置地图基本信息
        setId(2);
        setName("村庄");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
//        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    @Override
    public void init() {
        // 设置地图传送门
        addGate(initialPlace.getName(), initialPlace);
        addElement(0, 2, initialPlace.getName());
        addGate(castle.getName(), castle);
        addElement(4, 2, castle.getName());
        addGate(forest.getName(), forest);
        addElement(2, 4, forest.getName());
    }

}
