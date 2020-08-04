package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.map.Dungeon;
import com.fung.server.gameserver.content.config.readconfig.ReadMap;
import com.fung.server.gameserver.content.config.readconfig.ReadMapGates;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.map.GameMapGates;
import com.fung.server.gameserver.content.domain.dungeon.DungeonManager;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * GameMapActor 封装Map，用于申请多线程处理地图中的事件
     */
    private Map<Integer, GameMapActor> gameMapActorMap;

    /**
     * 用于存储地图传送门信息
     * key map id  value gameMapGate
     */
    private Map<Integer, List<GameMapGates>> gameMapGateMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(MapManager.class);

    @Autowired
    private ReadMap readMap;

    @Autowired
    private ReadMapGates readMapGates;

    @Autowired
    private MonsterCreateManager monsterCreateManager;

    @Autowired
    private DungeonManager dungeonManager;

    public MapManager() {
        gameMapActorMap = new HashMap<>();
    }

    public void mapInit() throws IOException, InvalidFormatException {
        readMap.init();
        readMapGates.init();

        gameMapGateMap = new HashMap<>();
        HashMap<Integer, GameMapGates> modelMap = readMapGates.getModelMap();
        modelMap.forEach((integer, gameMapGates)-> {
            int thisMapId = gameMapGates.getThisMapId();
            if (!gameMapGateMap.containsKey(thisMapId)) {
                List<GameMapGates> gameMapGatesList = new ArrayList<>();
                gameMapGatesList.add(gameMapGates);
                gameMapGateMap.put(thisMapId, gameMapGatesList);
            } else {
                gameMapGateMap.get(thisMapId).add(gameMapGates);
            }
        });

        // 初始化各张地图实体
        gameMapCollection = readMap.getModelMap();
        gameMapCollection.forEach((id,item) -> {
            gameMapInit(item);

            // 封装到MapActor中
            GameMapActor gameMapActor = new GameMapActor();
            gameMapActor.setGameMap(item);
            gameMapActorMap.put(id, gameMapActor);
        });

        // 增加传送门
        modelMap.forEach((id, item) -> {
            GameMap currentMap = gameMapCollection.get(item.getThisMapId());
            GameMap nextMap = gameMapCollection.get(item.getNextMapId());
            currentMap.addGate(item.getLocation(), nextMap);
        });

        LOGGER.info("地图初始化成功");
    }

    public void gameMapInit(GameMap gameMap) {
        gameMap.setElements(new HashMap<>());
        gameMap.setGates(new HashMap<>());
        gameMap.setPlayerInPosition(new ConcurrentHashMap<>());
        gameMap.setMonsterMap(new HashMap<>());
        gameMap.setFallingGoodMap(new HashMap<>());
        gameMap.setPlayChannel(new ArrayList<>());
    }

    public GameMap getMapByMapId(int i) {
        return gameMapCollection.get(i);
    }
        public GameMapActor getGameMapActorById(int id) {
        return gameMapActorMap.get(id);
    }

    public Dungeon createNewDungeon(int dungeonId) {
        GameMap templateMap = getMapByMapId(dungeonId);
        Dungeon dungeon = new Dungeon();
        dungeon.setX(templateMap.getX());
        dungeon.setY(templateMap.getY());
        dungeon.setName(templateMap.getName());
        dungeon.setUuid(Uuid.createUuid());
        dungeon.setId(dungeonId);
        gameMapInit(dungeon);
        // 初始化dungeon怪物
        monsterCreateManager.configMonsterByGameMap(dungeon);
        // Todo 副本多地图连接
        return dungeon;
    }

    public GameMapActor playerGotDungeon(int dungeonId, Player player) {
        if (!dungeonManager.hasEmptyDungeon(dungeonId)) {
            return dungeonManager.playerGotDungeon(createNewDungeon(dungeonId), player);
        }
        return dungeonManager.playerGotDungeon(dungeonId);
    }

    public boolean playerLeaveDungeon(Player player) {
        if (player.getTempStatus().getDungeonId() == null) {
            return false;
        }
        GameMapActor dungeonActor = dungeonManager.getDungeonActorByUuid(player.getTempStatus().getDungeonId());
        Dungeon dungeon = (Dungeon) dungeonActor.getGameMap();
        GameMap nextMap = getMapByMapId(dungeon.getBeforeMapId());
        return dungeonManager.playerLeaveDungeon(player, nextMap);
    }

    public GameMapActor getGameMapActor(Player player) {
        if (player.getTempStatus().getDungeonId() ==  null) {
            return getGameMapActorById(player.getInMapId());
        }
        return dungeonManager.getDungeonActorByUuid(player.getTempStatus().getDungeonId());
    }

    public GameMapActor getDungeonActorByUuid(String uuid){
        return dungeonManager.getDungeonActorByUuid(uuid);
    }

    public Map<Integer, GameMap> getGameMapCollection() {
        return gameMapCollection;
    }

    public void setGameMapCollection(Map<Integer, GameMap> gameMapCollection) {
        this.gameMapCollection = gameMapCollection;
    }
}
