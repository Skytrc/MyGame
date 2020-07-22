package com.fung.server.gameserver.content.domain.dungeon;

import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.Dungeon;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 23:12
 **/
@Component
public class DungeonManager {

    @Autowired
    private MapManager mapManager;

    /**
     * 存储副本
     */
    private Map<String, GameMapActor> dungeonManger;

    /**
     * 保存所有空闲副本的uuid
     */
    private Map<Integer, List<String>> idleDungeon;

    public DungeonManager() {
        dungeonManger = new HashMap<>();
        idleDungeon = new HashMap<>();
    }

    /**
     * 查看容器中是否有空闲的副本
     */
    public boolean hasEmptyDungeon(int dungeonId) {
        return (idleDungeon.containsKey(dungeonId) || (idleDungeon.get(dungeonId).size() > 0));
    }

    /**
     * 玩家获取新的副本
     */
    public GameMapActor playerGotDungeon(Dungeon newDungeon, Player player) {
        newDungeon.setBeforeMapId(player.getInMapId());
        GameMapActor mapActor = new GameMapActor();
        mapActor.setGameMap(newDungeon);
        dungeonManger.put(newDungeon.getUuid(), mapActor);
        return mapActor;
    }

    /**
     * 玩家获取容器中已有的副本
     */
    public GameMapActor playerGotDungeon(int dungeonId) {
        List<String> dungeons = idleDungeon.get(dungeonId);
        return dungeonManger.get(dungeons.remove(0));
    }

    public boolean playerLeaveDungeon(Player player, GameMap nextMap) {
        GameMapActor mapActor = dungeonManger.get(player.getTempStatus().getDungeonId());
        Dungeon dungeon = (Dungeon) mapActor.getGameMap();
        dungeon.removePlayer(player);
        nextMap.addPlayer(player);
        player.setInMapId(dungeon.getBeforeMapId());
        player.setInMapX(1);
        player.setInMapY(1);
        dungeonPlayerCheck(mapActor);
        return true;
    }

    /**
     * 判断玩家离开副本后，副本是否还有人。如果没有人，重新设置副本的内容并加入空闲副本中
     */
    private void dungeonPlayerCheck(GameMapActor gameMapActor) {
        Dungeon gameMap = (Dungeon) gameMapActor.getGameMap();
        if (!gameMap.hasPlayer()) {
            resetDungeon(gameMap);
            if (idleDungeon.containsKey(gameMap.getId())) {
                idleDungeon.get(gameMap.getId()).add(gameMap.getUuid());
            } else {
                List<String> dungeonUuid = new ArrayList<>();
                dungeonUuid.add(gameMap.getUuid());
                idleDungeon.put(gameMap.getId(), dungeonUuid);
            }
        }
    }

    /**
     * 重置副本中的内容
     */
    private void resetDungeon(Dungeon dungeon) {

        // TODO 怪物重置
    }

    public GameMapActor getDungeonActorByUuid(String uuid) {
        return dungeonManger.get(uuid);
    }


    public Map<String, GameMapActor> getDungeonManger() {
        return dungeonManger;
    }

    public void setDungeonManger(Map<String, GameMapActor> dungeonManger) {
        this.dungeonManger = dungeonManger;
    }
}
