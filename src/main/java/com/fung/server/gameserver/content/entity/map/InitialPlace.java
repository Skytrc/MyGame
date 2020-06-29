package com.fung.server.gameserver.content.entity.map;

import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始之地实体
 *
 * @author skytrc@163.com
 * @date 2020/4/30 10:56
 */
@Deprecated
@Component
public class InitialPlace extends BaseMap {

    @Autowired
    private Village village;

    private InitialPlace() {
        // 设置地图基本信息
        setId(1);
        setName("初始之地");
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
        addElement(4, 2, village.getName());
    }

}
