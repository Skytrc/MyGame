package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.npc.NpcOption;
import com.fung.server.gameserver.content.config.readconfig.ReadNpcOption;
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
 * @date 2020/6/17 11:18
 */
@Component
public class NpcOptionManager {

    @Autowired
    ReadNpcOption readNpcOption;

    /**
     * key NPCid  value npc 选项
     */
    private Map<Integer, List<NpcOption>> npcOptionMap;

    public void npcOptionInit() throws IOException, InvalidFormatException {
        readNpcOption.init();
        npcOptionMapInit();
    }

    public void npcOptionMapInit() {
        npcOptionMap = new HashMap<>();
        readNpcOption.getModelMap().forEach((key, value) -> {
            if (!npcOptionMap.containsKey(value.getNpcId())) {
                npcOptionMap.put(value.getNpcId(), new ArrayList<>());
            }
            npcOptionMap.get(value.getNpcId()).add(value);
        });
    }

    public List<NpcOption> getNpcOptionByNpcId(int npcId) {
        return npcOptionMap.get(npcId);
    }
}
