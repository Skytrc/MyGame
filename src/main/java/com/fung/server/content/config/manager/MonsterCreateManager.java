package com.fung.server.content.config.manager;

import com.fung.server.content.config.map.GameMap;
import com.fung.server.content.config.monster.Monster;
import com.fung.server.content.config.monster.MonsterDistribution;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 16:24
 */
@Component
public class MonsterCreateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonsterCreateManager.class);

    @Autowired
    MonsterManager monsterManager;

    @Autowired
    MonsterDistributionManager monsterDistributionManager;

    @Autowired
    MapManager mapManager;

    public void monsterCreateInit() throws IOException, InvalidFormatException {
        monsterDistributionManager.monsterDistributionInit();
        monsterManager.monsterInit();
        readMonsterDistribution2ConfigMonster();
        LOGGER.info("怪物生成完毕");
    }

    public void readMonsterDistribution2ConfigMonster() {
        Map<Integer, MonsterDistribution> distributionMap = monsterDistributionManager.getDistributionMap();
        // 遍历配置，把怪物一个个放入地图中
        for (Map.Entry<Integer, MonsterDistribution> entry : distributionMap.entrySet()) {
            MonsterDistribution value = entry.getValue();
            int position = mapManager.getMapByMapId(value.getInMapId()).xy2Location(value.getInMapX(), value.getInMapY());
            putMonsterInMap(value.getInMapId(), position, value.getMonsterId());
        }
    }

    public void putMonsterInMap(int gameMapId, int position, int monsterId)  {
        GameMap map = mapManager.getMapByMapId(gameMapId);
        Monster monster = createMonster(monsterId);
        map.putMonsterInMap(position, monster);
        map.addElement(position, monster);
    }

    public Monster createMonster(int monsterId) {
        Monster monster = monsterManager.getMonsterById(monsterId);

        Monster newMonster = new Monster();
        newMonster.setAttackPower(monster.getAttackPower());
        newMonster.setDefend(monster.getDefend());
        newMonster.setExp(monster.getExp());
        newMonster.setHealthPoint(monster.getMaxHealthPoint());
        newMonster.setMaxHealthPoint(monster.getMaxHealthPoint());
        newMonster.setId(monsterId);
        newMonster.setLevel(monster.getLevel());
        newMonster.setName(monster.getName());
        return newMonster;
    }
}
