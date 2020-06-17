package com.fung.server.content.domain.npc;

import com.fung.server.content.config.manager.MapManager;
import com.fung.server.content.config.map.GameMap;
import com.fung.server.content.config.npc.NonPlayerCharacter;
import com.fung.server.content.config.npc.NpcOption;
import com.fung.server.content.domain.player.PlayerInfo;
import com.fung.server.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速获取Npc信息
 * @author skytrc@163.com
 * @date 2020/6/17 17:24
 */
@Component
public class NpcInfo {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    MapManager mapManager;

    /**
     * TODO 初始化
     */
    private Map<Integer, Map<Integer, NonPlayerCharacter>> npcMap;

    /**
     * 判断是否地图是否存在npc
     */
    public boolean hasNpc(Player player) {
        if (npcMap.containsKey(player.getInMapId())) {
            GameMap gameMap = playerInfo.getCurrentPlayerMap(player);
            return npcMap.get(gameMap.getId()).containsKey(gameMap.xy2Location(player.getInMapX(), player.getInMapY()));
        }
        return false;
    }

    /**
     * 获取当前玩家地址的npc
     */
    public NonPlayerCharacter getNpcByPlayerInfo(Player player) {
        GameMap playerMap = playerInfo.getCurrentPlayerMap(player);
        Map<Integer, NonPlayerCharacter> locationNpcMap = npcMap.get(playerMap.getId());
        return locationNpcMap.get(playerMap.xy2Location(player.getInMapX(), player.getInMapY()));
    }

    public Map<Integer, Map<Integer, NonPlayerCharacter>> getNpcMap(Player player) {
        return npcMap;
    }

    /**
     * npc丢进Map中
     */
    public void putNpcInMap(NonPlayerCharacter npc, int mapId, int x, int y) {
        // 判断是否存在嵌套map，无就新建并put，有直接put

        GameMap mapByMapId = mapManager.getMapByMapId(mapId);
        if (!npcMap.containsKey(mapId)){
            HashMap<Integer, NonPlayerCharacter> locationNonPlayerCharacterHashMap = new HashMap<>();
            locationNonPlayerCharacterHashMap.put(mapByMapId.xy2Location(x, y), npc);
            npcMap.put(mapId, locationNonPlayerCharacterHashMap);
        } else {
            Map<Integer, NonPlayerCharacter> locationNonPlayerCharacterMap = npcMap.get(mapId);
            locationNonPlayerCharacterMap.put(mapByMapId.xy2Location(x, y), npc);
        }

    }

    /**
     * 获得Npc所有的选择（String）
     */
    public String getNpcChoose(Player player) {
        NonPlayerCharacter npc = getNpcByPlayerInfo(player);
        Map<Integer, NpcOption> npcOptionMap = npc.getNpcOptionMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append(npc.getName()).append(" 选项: \n");
        npcOptionMap.forEach((key, npcOption) -> {
            stringBuilder.append("\n选项: ").append(key).append(npcOption.getName());
        });
        return stringBuilder.toString();
    }

    /**
     * 获取某一选项
     */
    public String getNpcMessageByChoose(Player player, int choose) {
        NonPlayerCharacter npc = getNpcByPlayerInfo(player);
        return npc.getNpcOptionMap().get(choose).getContent();
    }

    public void init() {
        npcMap = new HashMap<>();
    }
}
