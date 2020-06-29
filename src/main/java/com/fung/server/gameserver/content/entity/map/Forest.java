package com.fung.server.gameserver.content.entity.map;

import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/4 15:07
 */
@Deprecated
@Component
public class Forest extends BaseMap {

    @Autowired
    private Village village;

    private Forest() {
        // 设置地图基本信息
        setId(3);
        setName("森林");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
//        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    @Override
    public void init() {
        // 设置地图传送门
        addGate(village.getName(), village);
        addElement(2, 0, village.getName());
    }
}
