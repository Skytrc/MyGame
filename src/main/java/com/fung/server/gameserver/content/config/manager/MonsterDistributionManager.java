package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.monster.MonsterDistribution;
import com.fung.server.gameserver.content.config.readconfig.ReadMonsterDistribution;
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
 * @date 2020/6/3 15:58
 */
@Component
public class MonsterDistributionManager {

    private Map<Integer, MonsterDistribution> distributionMap;

    /**
     * 用于读取某张地图中的所有怪兽资料，方便生成新的地图（副本）
     * key game map id    value list -> MonsterDistribution
     */
    private Map<Integer, List<MonsterDistribution>> gameMapMonsterMap;

    @Autowired
    private ReadMonsterDistribution readMonsterDistribution;

    public void monsterDistributionInit() throws IOException, InvalidFormatException {
        readMonsterDistribution.init();
        gameMapMonsterMap = new HashMap<>();
        distributionMap = readMonsterDistribution.getModelMap();
        gameMapMonsterMapInit();
    }

    public void gameMapMonsterMapInit() {
        for (Map.Entry<Integer, MonsterDistribution> entry : distributionMap.entrySet()) {
            MonsterDistribution value = entry.getValue();
            if (!gameMapMonsterMap.containsKey(value.getInMapId())) {
                List<MonsterDistribution> monsterDistributionList = new ArrayList<>();
                monsterDistributionList.add(value);
                gameMapMonsterMap.put(value.getInMapId(), monsterDistributionList);
            } else {
                gameMapMonsterMap.get(value.getId()).add(value);
            }
        }
    }

    public Map<Integer, MonsterDistribution> getDistributionMap() {
        return distributionMap;
    }

    public void setDistributionMap(Map<Integer, MonsterDistribution> distributionMap) {
        this.distributionMap = distributionMap;
    }

    public Map<Integer, List<MonsterDistribution>> getGameMapMonsterMap() {
        return gameMapMonsterMap;
    }
}
