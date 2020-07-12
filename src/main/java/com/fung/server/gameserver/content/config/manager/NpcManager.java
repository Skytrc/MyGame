package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.npc.NonPlayerCharacter;
import com.fung.server.gameserver.content.config.npc.NpcOption;
import com.fung.server.gameserver.content.config.readconfig.ReadNpc;
import com.fung.server.gameserver.content.domain.npc.NpcInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 11:01
 */
@Component
public class NpcManager {

    @Autowired
    private ReadNpc readNpc;

    @Autowired
    private NpcOptionManager npcOptionManager;

    @Autowired
    private NpcShopManager npcShopManager;

    @Autowired
    private NpcInfo npcInfo;

    @Autowired
    private MapManager mapManager;

    private Map<Integer, NonPlayerCharacter> npcMap;

    public void npcInit() throws IOException, InvalidFormatException {
        readNpc.init();

        npcMap = readNpc.getModelMap();
        npcMap.forEach((key, npc) -> {
            npc.setNpcOptionMap(new HashMap<>());
        });
    }

    public void npcModelInit() throws IOException, InvalidFormatException {
        npcInit();
        npcOptionManager.npcOptionInit();
        npcShopManager.npcShopInit();
        npcInfo.init();
        putNpcInMap();
    }

    /**
     * 将NPC放入地图中
     */
    public void putNpcInMap() {
        npcMap.forEach((key, npc) -> {
            // Npc放置入地图
            configNpcDialogue(npc);
            shopNpcConfig(npc);
            GameMap mapByMapId = mapManager.getMapByMapId(npc.getInMapId());
            mapByMapId.addElement(mapByMapId.xy2Location(npc.getInMapX(), npc.getInMapY()), npc);

            // Npc放置入MapIdLocationNpcMap中
            npcInfo.putNpcInMap(npc, npc.getInMapId(), npc.getInMapX(), npc.getInMapY());
        });
    }

    /**
     * 配置NPC对话框
     */
    public void configNpcDialogue(NonPlayerCharacter npc) {
        List<NpcOption> npcOptions = npcOptionManager.getNpcOptionByNpcId(npc.getId());
        Map<Integer, NpcOption> npcOptionMap = npc.getNpcOptionMap();
        for (NpcOption npcOption : npcOptions) {
            npcOptionMap.put(npcOption.getOptionLocation(), npcOption);
        }
    }

    /**
     * 配置NPC商店出售物品物品的类型
     */
    public void shopNpcConfig(NonPlayerCharacter npc) {
        if (npcShopManager.getNpcShopMap().containsKey(npc.getId())){
            npc.setGoodsId(npcShopManager.getNpcShopMap().get(npc.getId()));
        }
    }

    public NonPlayerCharacter getNpcById(int id) {
        return npcMap.get(id);
    }

    public Map<Integer, NonPlayerCharacter> getNpcMap() {
        return npcMap;
    }

    public void setNpcMap(Map<Integer, NonPlayerCharacter> npcMap) {
        this.npcMap = npcMap;
    }
}
