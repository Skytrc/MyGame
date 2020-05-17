package com.fung.server.content;

import com.fung.server.content.entity.GameMap;
import com.fung.server.dao.MapInitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 9:45
 */
@Component
public class MapManagement {

    /**
     * 用于记录地图  key 地图id  value 地图
     */
    private Map<Integer, GameMap> gameMapCollection;

    private static final Logger LOGGER = LoggerFactory.getLogger(MapManagement.class);

    @Autowired
    public MapInitDao mapInitDao;

    public void mapInit() {
        gameMapCollection = new HashMap<>(4);

        // 初始化各张地图实体
        GameMap initialPlace = new GameMap();
        GameMap village = new GameMap();
        GameMap forest = new GameMap();
        GameMap castle = new GameMap();

        setMapBasicInfo(initialPlace, 1, "初始之地", 5, 5);
        setMapBasicInfo(village, 2, "村庄", 5, 5);
        setMapBasicInfo(forest, 3, "森林", 5, 5);
        setMapBasicInfo(castle, 4, "城堡" ,5, 5);

        initialPlace.addGate(15, village);
        village.addGate(11, initialPlace);
        village.addGate(23, forest);
        village.addGate(15, castle);
        forest.addGate(3, village);
        castle.addGate(11, village);

        gameMapCollection.put(initialPlace.getId(), initialPlace);
        gameMapCollection.put(village.getId(), village);
        gameMapCollection.put(forest.getId(), forest);
        gameMapCollection.put(castle.getId(), castle);
        LOGGER.info("地图初始化成功");
    }

    public void saveGameMap() {
        for (Map.Entry<Integer, GameMap> entry : gameMapCollection.entrySet()) {
            mapInitDao.saveMap(entry.getValue());
        }
    }

    /**
     * 初始化一张地图的基本信息
     * @param gameMap 需要初始化地图实例
     * @param id 地图id
     * @param name 地图名字
     * @param x x轴
     * @param y y轴
     */
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
