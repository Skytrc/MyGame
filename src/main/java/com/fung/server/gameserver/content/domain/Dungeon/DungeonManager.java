package com.fung.server.gameserver.content.domain.Dungeon;

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
     * 创建新的副本
     */
    private Dungeon createdNewDungeon(int dungeonId) {
        return mapManager.createNewDungeon(dungeonId);
    }

    /**
     * 玩家获取副本
     * 先查看是否存在空闲副本，有返回副本（从idleDungeon中剔除），没有则创建新的副本
     */
    public GameMapActor playerGotDungeon(int dungeonId, Player player) {
        if (!idleDungeon.containsKey(dungeonId) || (idleDungeon.get(dungeonId).size() == 0)) {
            Dungeon newDungeon = createdNewDungeon(dungeonId);
            newDungeon.setBeforeMapId(player.getInMapId());
            GameMapActor mapActor = new GameMapActor();
            mapActor.setGameMap(newDungeon);
            dungeonManger.put(newDungeon.getUuid(), mapActor);
            return mapActor;
        }
        String s = idleDungeon.get(dungeonId).remove(0);
        return dungeonManger.get(s);
    }

    public boolean playerLeaveDungeon(Player player) {
        if (player.getTempStatus().getDungeonId() == null) {
            return false;
        }
        GameMapActor mapActor = dungeonManger.get(player.getTempStatus().getDungeonId());
        Dungeon dungeon = (Dungeon) mapActor.getGameMap();
        dungeon.removePlayer(player);
        GameMap nextMap = mapManager.getMapByMapId(dungeon.getBeforeMapId());
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
    public void dungeonPlayerCheck(GameMapActor gameMapActor) {
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
    public void resetDungeon(Dungeon dungeon) {

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
