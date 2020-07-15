package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.MonsterDrop;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.config.monster.MonsterDistribution;
import com.fung.server.gameserver.content.domain.monster.MonsterDropCreated;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 16:24
 */
@Component
public class MonsterCreateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonsterCreateManager.class);

    @Autowired
    private MonsterManager monsterManager;

    @Autowired
    private MonsterDistributionManager monsterDistributionManager;

    @Autowired
    private MonsterSkillManager monsterSkillManager;

    @Autowired
    private MonsterDropManager monsterDropManager;

    @Autowired
    private MonsterDropCreated monsterDropCreated;

    @Autowired
    private MapManager mapManager;

    public void monsterCreateInit() throws IOException, InvalidFormatException {
        monsterDistributionManager.monsterDistributionInit();
        monsterManager.monsterInit();
        monsterSkillManager.monsterSkillInit();
        monsterDropManager.monsterDropInit(new HashMap<>());
        monsterDropCreated.monsterDropCreatedInit(monsterDropManager.getMonsterDropByIdMap());
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

    /**
     * 用于生成副本内的怪兽
     */
    public void configMonsterByGameMap(GameMap gameMap) {
        Map<Integer, List<MonsterDistribution>> mapMonsterMap = monsterDistributionManager.getGameMapMonsterMap();
        List<MonsterDistribution> monsterDistributionList = mapMonsterMap.get(gameMap.getId());
        monsterDistributionList.forEach(monsterDistribution -> {
            int position = gameMap.xy2Location(monsterDistribution.getInMapX(), monsterDistribution.getInMapY());
            putMonsterInMap(gameMap, position, monsterDistribution.getMonsterId());
        });
    }

    public void putMonsterInMap(int gameMapId, int position, int monsterId)  {
        GameMap map = mapManager.getMapByMapId(gameMapId);
        // 设置怪物初始信息
        NormalMonster normalMonster = createMonster(monsterId, gameMapId , position);
        map.putMonsterInMap(position, normalMonster);
        map.addElement(position, normalMonster);
    }

    /**
     * 用作于生成新副本中的怪兽
     */
    public void putMonsterInMap(GameMap map, int position, int monsterId) {
        NormalMonster normalMonster = createMonster(monsterId, map, position);
        map.putMonsterInMap(position, normalMonster);
        map.addElement(position, normalMonster);
    }

    public NormalMonster createMonster(int monsterId, GameMap gameMap, int position) {
        NormalMonster normalMonster = monsterManager.getMonsterById(monsterId);

        NormalMonster newNormalMonster = new NormalMonster();
        newNormalMonster.setAttackPower(normalMonster.getAttackPower());
        newNormalMonster.setDefend(normalMonster.getDefend());
        newNormalMonster.setHealthPoint(normalMonster.getMaxHealthPoint());
        newNormalMonster.setMaxHealthPoint(normalMonster.getMaxHealthPoint());
        newNormalMonster.setId(monsterId);
        newNormalMonster.setLevel(normalMonster.getLevel());
        newNormalMonster.setName(normalMonster.getName());
        newNormalMonster.setMonsterSkill(monsterSkillManager.getMonsterSkillByMonsterId(monsterId));

        // 设置怪物所在地图信息
        newNormalMonster.setInMapId(gameMap.getId());
        newNormalMonster.setInMapX(gameMap.location2xy(position)[GameMap.X]);
        newNormalMonster.setInMapY(gameMap.location2xy(position)[GameMap.Y]);
        return newNormalMonster;
    }

    public NormalMonster createMonster(int monsterId, int gameMapId, int position) {
        GameMap map = mapManager.getMapByMapId(gameMapId);
        return createMonster(monsterId, map, position);
//        NormalMonster normalMonster = monsterManager.getMonsterById(monsterId);
//
//        NormalMonster newNormalMonster = new NormalMonster();
//        newNormalMonster.setAttackPower(normalMonster.getAttackPower());
//        newNormalMonster.setDefend(normalMonster.getDefend());
//        newNormalMonster.setHealthPoint(normalMonster.getMaxHealthPoint());
//        newNormalMonster.setMaxHealthPoint(normalMonster.getMaxHealthPoint());
//        newNormalMonster.setId(monsterId);
//        newNormalMonster.setLevel(normalMonster.getLevel());
//        newNormalMonster.setName(normalMonster.getName());
//        newNormalMonster.setMonsterSkill(monsterSkillManager.getMonsterSkillByMonsterId(monsterId));
//        // 设置怪物所在地图信息
//        newNormalMonster.setInMapId(gameMapId);
//        newNormalMonster.setInMapX(mapManager.getMapByMapId(gameMapId).location2xy(position)[0]);
//        newNormalMonster.setInMapY(mapManager.getMapByMapId(gameMapId).location2xy(position)[1]);
    }

    public Map<Integer, List<MonsterDrop>> getMonsterDropByIdMap() {
        return monsterDropManager.getMonsterDropByIdMap();
    }
}
