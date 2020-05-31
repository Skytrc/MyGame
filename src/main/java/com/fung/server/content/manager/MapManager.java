package com.fung.server.content.manager;

import com.fung.server.content.config.ConfigMap;
import com.fung.server.content.config.ConfigMapGates;
import com.fung.server.content.domain.GameMap;
import com.fung.server.content.domain.GameMapGates;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 9:45
 */
@Component
public class MapManager {

    /**
     * 用于记录地图  key 地图id  value 地图
     */
    private Map<Integer, GameMap> gameMapCollection;

    private static final Logger LOGGER = LoggerFactory.getLogger(MapManager.class);

    @Autowired
    private ConfigMap configMap;

    @Autowired
    private ConfigMapGates configMapGates;

    public void mapInit() throws IOException, InvalidFormatException {
        configMap.init();
        configMapGates.init();

        // 初始化各张地图实体
        gameMapCollection = configMap.getModelMap();
        gameMapCollection.forEach((id,item) -> {
            item.setElements(new HashMap<>());
            item.setGates(new HashMap<>());
            item.setMapPlayers(new HashMap<>());
        });

        // 增加传送门
        Map<Integer, GameMapGates> gamMapGates = configMapGates.getModelMap();
        gamMapGates.forEach((id, item) -> {
            gameMapCollection.get(item.getThisMap()).addGate(item.getLocation(), gameMapCollection.get(item.getNextMap()));
        });

        // 增加Element


        LOGGER.info("地图初始化成功");
    }

    /**
     * 初始化一张地图的基本信息
     * @param gameMap 需要初始化地图实例
     * @param id 地图id
     * @param name 地图名字
     * @param x x轴
     * @param y y轴
     */
    @Deprecated
    public void setMapBasicInfo(GameMap gameMap, int id, String name, int x, int y) {
        gameMap.setId(id);
        gameMap.setName(name);
        gameMap.setX(x);
        gameMap.setY(y);
        gameMap.setElements(new HashMap<>(x * y));
        gameMap.setGates(new HashMap<>());
        gameMap.setMapPlayers(new HashMap<>());
    }

    public GameMap getMapByMapId(int i) {
        return gameMapCollection.get(i);
    }


    public Map<Integer, GameMap> getGameMapCollection() {
        return gameMapCollection;
    }

    public void setGameMapCollection(Map<Integer, GameMap> gameMapCollection) {
        this.gameMapCollection = gameMapCollection;
    }
}
