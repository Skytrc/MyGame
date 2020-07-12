package com.fung.server.gameserver.content.domain.npc;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.npc.NonPlayerCharacter;
import com.fung.server.gameserver.content.config.npc.NpcOption;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快速获取Npc信息
 * @author skytrc@163.com
 * @date 2020/6/17 17:24
 */
@Component
public class NpcInfo {

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private GoodManager goodManager;

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
        if (npc == null) {
            return "当前玩家位置没有npc";
        }
        Map<Integer, NpcOption> npcOptionMap = npc.getNpcOptionMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append(npc.getName()).append(" 选项: \n");
        npcOptionMap.forEach((key, npcOption) -> {
            stringBuilder.append("\n选项: ").append(key).append(npcOption.getName());
        });
        return stringBuilder.toString();
    }

    /**
     * TODO
     * 获取某一选项
     */
    public String getNpcMessageByChoose(Player player, int choose) {
        NonPlayerCharacter npc = getNpcByPlayerInfo(player);
        if (npc == null) {
            return "当前玩家位置没有npc";
        }
        return npc.getNpcOptionMap().get(choose).getContent();
    }

    /**
     * TODO
     * 返回商店列表
     */
    public String openShop(Player player) {
        NonPlayerCharacter npc = getNpcByPlayerInfo(player);
        if (npc == null) {
            return "当前玩家位置没有npc";
        }
        if (npc.getGoodsId()== null) {
            return "该npc没有商店功能";
        }
        List<Integer> goodsId = npc.getGoodsId();
        StringBuilder stringBuilder = new StringBuilder("\n");
        stringBuilder.append("商店有: ");
        goodsId.forEach(goodId ->{
            String[] goodInfoById = goodManager.getGoodInfoById(goodId);
            stringBuilder.append(goodInfoById[GoodManager.GOOD_ID]).append("  ").append(goodInfoById[GoodManager.GOOD_NAME])
                    .append("  ").append(goodInfoById[GoodManager.GOOD_DESCRIPTION]).append("\n");
        });
        return stringBuilder.toString();
    }

    /**
     * TODO 数字可以优化
     */
    public int enterDungeon(Player player) {
        NonPlayerCharacter npc = getNpcByPlayerInfo(player);
        int dungeonId = -1;
        for (Integer integer : npc.getNpcOptionMap().keySet()) {
            if (integer >= 100) {
                dungeonId = 100;
                break;
            }
        }
        return dungeonId;
    }

    public void init() {
        npcMap = new HashMap<>();
    }
}
