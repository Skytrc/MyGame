package com.fung.server.content.entity.map;

import com.fung.server.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/4 15:09
 */
@Deprecated
@Component
public class Castle extends BaseMap {

    @Autowired
    private Village village;

    private Castle() {
        // 设置地图基本信息
        setId(4);
        setName("城堡");
        setGrid(new String[5][5]);
        Map<Integer, Player> mapPlayer = new HashMap<>();
        setMapPlayers(mapPlayer);
        Map<String, BaseMap>  gates = new HashMap<>();
        setGates(gates);
    }

    @Override
    public void init() {
        // 设置传送门
        addGate(village.getName(), village);
        addElement(0, 2, village.getName());
    }
}
