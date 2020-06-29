package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.monster.MonsterDistribution;
import com.fung.server.gameserver.content.config.readconfig.ReadMonsterDistribution;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 15:58
 */
@Component
public class MonsterDistributionManager {

    private Map<Integer, MonsterDistribution> distributionMap;

    @Autowired
    private ReadMonsterDistribution readMonsterDistribution;

    public void monsterDistributionInit() throws IOException, InvalidFormatException {
        readMonsterDistribution.init();

        distributionMap = readMonsterDistribution.getModelMap();
    }

    public Map<Integer, MonsterDistribution> getDistributionMap() {
        return distributionMap;
    }

    public void setDistributionMap(Map<Integer, MonsterDistribution> distributionMap) {
        this.distributionMap = distributionMap;
    }
}
