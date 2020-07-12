package com.fung.server.gameserver.content.domain.Dungeon;

import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.Dungeon;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/07/12 23:12
 **/
@Component
public class DungeonManager {

    @Autowired
    private MapManager mapManager;

    private Map<String, Dungeon> dungeonManger;

    public DungeonManager() {
        dungeonManger = new HashMap<>();
    }

    /**
     * TODO
     * 创建新的副本
     */
    private void createdNewDungeon(int dungeonId, Player player) {
        Dungeon newDungeon = new Dungeon();
//        newDungeon = (Dungeon) mapManager.getMapByMapId(dungeonId);
//        dungeon.setUuid(Uuid.createUuid());
//        player.getTempStatus().setDungeonId(dungeon.getUuid());
//        dungeonManger.put(dungeon.getUuid(), dungeon);
    }


    public Map<String, Dungeon> getDungeonManger() {
        return dungeonManger;
    }

    public void setDungeonManger(Map<String, Dungeon> dungeonManger) {
        this.dungeonManger = dungeonManger;
    }
}
