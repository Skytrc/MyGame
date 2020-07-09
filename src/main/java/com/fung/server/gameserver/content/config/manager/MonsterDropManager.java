package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.monster.MonsterDrop;
import com.fung.server.gameserver.content.config.readconfig.ReadMonsterDrop;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/7/9 16:39
 */
@Component
public class MonsterDropManager {

    private Map<Integer, MonsterDrop> monsterDropMap;

    private Map<Integer, List<MonsterDrop>> monsterDropByIdMap;

    @Autowired
    private ReadMonsterDrop readMonsterDrop;


    public void monsterDropInit(Map<Integer, List<MonsterDrop>> monsterDropByIdMap) throws IOException, InvalidFormatException {
        readMonsterDrop.init();
        monsterDropMap = readMonsterDrop.getModelMap();
        this.monsterDropByIdMap = monsterDropByIdMap;

        // 把怪物对应编号的
        monsterDropByIdMapInit();

    }

    public void monsterDropByIdMapInit() {
        monsterDropMap.forEach((integer, monsterDrop) -> {
            int monsterId = monsterDrop.getMonsterId();
            if (!monsterDropByIdMap.containsKey(monsterId)) {
                // 如果沒有對應怪物id,新增
                List<MonsterDrop> monsterDrops = new ArrayList<>();
                monsterDrops.add(monsterDrop);
                monsterDropByIdMap.put(monsterId, monsterDrops);
            } else {
                monsterDropByIdMap.get(monsterId).add(monsterDrop);
            }
        });
    }

    public Map<Integer, MonsterDrop> getMonsterDropMap() {
        return monsterDropMap;
    }

    public void setMonsterDropMap(Map<Integer, MonsterDrop> monsterDropMap) {
        this.monsterDropMap = monsterDropMap;
    }

    public Map<Integer, List<MonsterDrop>> getMonsterDropByIdMap() {
        return monsterDropByIdMap;
    }
}
