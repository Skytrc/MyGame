package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.monster.MonsterDrop;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.config.monster.MonsterDistribution;
import com.fung.server.gameserver.content.domain.buff.UnitBuffManager;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.monster.MonsterAction;
import com.fung.server.gameserver.content.domain.monster.MonsterDropCreated;
import com.fung.server.gameserver.content.entity.Skill;
import com.google.gson.Gson;
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
    private MonsterAction monsterAction;

    @Autowired
    private MapManager mapManager;

    private Gson gson;

    public MonsterCreateManager() {
        this.gson = new Gson();
    }

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

    /**
     * 将怪物放入地图，并开启stand by状态
     */
    public void putMonsterInMap(int gameMapId, int position, int monsterId)  {
        GameMapActor gameMapActor = mapManager.getGameMapActorById(gameMapId);
        GameMap gameMap = gameMapActor.getGameMap();
        // 设置怪物初始信息
        BaseHostileMonster monster = createMonster(monsterId,  gameMap, position);
        gameMap.putMonsterInMap(position, monster);
        gameMap.addElement(position, monster);

        // 开启怪物StandBy状态
        if (monster.getIsAutoAttack() == 1 && gameMap.getId() < GameMap.DUNGEON_ID_START) {
            monsterEnterStandByState(monster, gameMapActor);
        }
    }

    /**
     * 用作于生成新副本中的怪兽
     */
    public void putMonsterInMap(GameMap map, int position, int monsterId) {
        BaseHostileMonster monster = createMonster(monsterId, map, position);
        map.putMonsterInMap(position, monster);
        map.addElement(position, monster);
    }

    public BaseHostileMonster createMonster(int monsterId, GameMap gameMap, int position) {
        BaseHostileMonster templateMonster = monsterManager.getMonsterById(monsterId);

        // 使用序列化進行深複製 TODO 建造者模式
        BaseHostileMonster monster = gson.fromJson(gson.toJson(templateMonster), BaseHostileMonster.class);

//        NormalMonster monster = new NormalMonster();
//        monster.setAttackPower(normalMonster.getAttackPower());
//        monster.setDefend(normalMonster.getDefend());
        monster.setHealthPoint(monster.getMaxHealthPoint());
//        monster.setMaxHealthPoint(normalMonster.getMaxHealthPoint());
//        monster.setId(monsterId);
//        monster.setLevel(normalMonster.getLevel());
//        monster.setName(normalMonster.getName());
//        monster.setExp(normalMonster.getExp());
//        monster.setValue(normalMonster.getValue());
//        monster.setSensingRange(normalMonster.getSensingRange());

        // 技能挂载&buff状态挂载
        List<Skill> skills = monsterSkillManager.getMonsterSkillByMonsterId(monsterId);
        monster.setSkills(skills);

        UnitBuffManager unitBuffManager = new UnitBuffManager();
        unitBuffManager.setUnit(monster);
        monster.setUnitBuffManager(unitBuffManager);


        // 设置怪物所在地图信息
        monster.setInMapId(gameMap.getId());
        monster.setInMapX(gameMap.location2xy(position)[GameMap.X]);
        monster.setInMapY(gameMap.location2xy(position)[GameMap.Y]);
        monster.setTempX(monster.getInMapX());
        monster.setTempY(monster.getInMapY());
        monster.setGameMapActor(mapManager.getGameMapActorById(gameMap.getId()));
        return monster;
    }

    @Deprecated
    public BaseHostileMonster createMonster(int monsterId, int gameMapId, int position) {
        GameMap map = mapManager.getMapByMapId(gameMapId);
        return createMonster(monsterId, map, position);
    }

    public Map<Integer, List<MonsterDrop>> getMonsterDropByIdMap() {
        return monsterDropManager.getMonsterDropByIdMap();
    }

    /**
     * 开启怪物stand By状态
     */
    public void monsterEnterStandByState(BaseHostileMonster monster, GameMapActor gameMapActor) {
        gameMapActor.addMessage(gameMapActor1 -> {
            monsterAction.standBy(monster, gameMapActor);
        });
    }
}
