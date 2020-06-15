package com.fung.server.content.config.manager;

import com.fung.server.content.config.map.GameMap;
import com.fung.server.content.config.monster.NormalMonster;
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
    MonsterSkillManager monsterSkillManager;

    @Autowired
    MapManager mapManager;

    public void monsterCreateInit() throws IOException, InvalidFormatException {
        monsterDistributionManager.monsterDistributionInit();
        monsterManager.monsterInit();
        monsterSkillManager.monsterSkillInit();
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
        // 设置怪物初始信息
        NormalMonster normalMonster = createMonster(monsterId, gameMapId , position);
        map.putMonsterInMap(position, normalMonster);
        map.addElement(position, normalMonster);
    }

    public NormalMonster createMonster(int monsterId, int gameMapId, int position) {
        NormalMonster normalMonster = monsterManager.getMonsterById(monsterId);

        NormalMonster newNormalMonster = new NormalMonster();
        newNormalMonster.setAttackPower(normalMonster.getAttackPower());
        newNormalMonster.setDefend(normalMonster.getDefend());
        newNormalMonster.setExp(normalMonster.getExp());
        newNormalMonster.setHealthPoint(normalMonster.getMaxHealthPoint());
        newNormalMonster.setMaxHealthPoint(normalMonster.getMaxHealthPoint());
        newNormalMonster.setId(monsterId);
        newNormalMonster.setLevel(normalMonster.getLevel());
        newNormalMonster.setName(normalMonster.getName());
        newNormalMonster.setMonsterSkill(monsterSkillManager.getMonsterSkillByMonsterId(monsterId));
        // 设置怪物所在地图信息
        normalMonster.setInMapId(gameMapId);
        normalMonster.setInMapX(mapManager.getMapByMapId(gameMapId).location2xy(position)[0]);
        normalMonster.setInMapY(mapManager.getMapByMapId(gameMapId).location2xy(position)[1]);
        return newNormalMonster;
    }
}
