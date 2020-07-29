package com.fung.server.gameserver.content.domain.player;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 20:59
 */
@Component
public class PlayerInfo {

    @Autowired
    private MapManager mapManager;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private SkillManager skillManager;

    /**
     * @param channelId 客户端channel id
     * @return 当前用户所在的地图
     */
    public GameMap getCurrentPlayerMap(String channelId) {
        return mapManager.getMapByMapId(onlinePlayer.getPlayerByChannelId(channelId).getInMapId());
    }

    public GameMap getCurrentPlayerMap(Player player){
        return mapManager.getMapByMapId(player.getInMapId());
    }

    public GameMap getGameMapById(int gameMapId) {
        return mapManager.getMapByMapId(gameMapId);
    }

    public PlayerActor getPlayerActorByChannelId(String channelId) {
        return onlinePlayer.getPlayerActorByChannelId(channelId);
    }

    public PlayerActor getPlayerActorByPlayerId(String playerId) {
        return onlinePlayer.getPlayerActorByPlayerId(playerId);
    }

    public boolean hasPlayerId(String playerId) {
        return onlinePlayer.hasPlayerId(playerId);
    }

    public Player getCurrentPlayer(String channelId) {
        return onlinePlayer.getPlayerByChannelId(channelId);
    }

    public String showPlayerInfo(Player player) {
        return showPlayerBase(player) + showPlayerValue(player) + showPlayerLocation(player);
    }

    public String showPlayerBase(Player player) {
        return "\n玩家姓名: " + player.getPlayerName() + " 玩家id: " + player.getUuid() + "\n";
    }

    public String showPlayerValue(Player player) {
        return " \n等级: " + player.getLevel() + " 当前血量: " + player.getHealthPoint() + " 最大血量: " +player.getTotalHealthPoint()
                + " 当前魔法值: " + player.getMagicPoint() + " 最大魔法值: " + player.getTotalMagicPoint() + " 攻击力: "
                + player.getTotalAttackPower() + " 魔法力: " + player.getTotalMagicPower() + " 暴击率: " + player.getTotalCriticalRate()
                + " 防御力: " + player.getTotalDefense() + "\n";
    }

    public String showPlayerLocation(Player player) {
        return " \n所在地图：" + getGameMapById(player.getInMapId()).getName() + "  所在坐标：["
                + player.getInMapX() + "," + player.getInMapY() + "]" + "\n";
    }

    public String showPlayerDate(Player player) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "\n玩家创建时间: " + dateFormat.format(player.getCreatedDate()) + " 玩家最后一次登录: "
                + dateFormat.format(player.getLoginDate());
    }

    public String showBackpack(Player player) {
        PersonalBackpack personalBackpack = player.getPersonalBackpack();
        StringBuilder stringBuilder = new StringBuilder("背包: ");
        personalBackpack.getBackpack().forEach((key, value) -> {
            String[] strings = goodManager.getGoodBaseInfoById(value.getGoodId());
            stringBuilder.append("\n位置:").append(key).append(" 有").append(strings[GoodManager.GOOD_NAME]).append("，")
                    .append(strings[GoodManager.GOOD_DESCRIPTION]).append(" 数量: ").append(value.getQuantity());
        });
        return stringBuilder.toString();
    }

    public String showSkill(Player player) {
        List<Skill> skills = player.getSkillManager().getAllSkill();
        StringBuilder stringBuilder = new StringBuilder();
        skills.forEach(skill -> {
            String[] strings = skillManager.getSkillInfoById(skill.getId());
            stringBuilder.append("\n技能: ").append(strings[0]).append(" 等级: ").append(skill.getLevel())
                    .append(" 基础物理伤害: ").append(strings[1]).append(" 基础魔法伤害: ").append(strings[2]).append(" 技能CD: ")
                    .append(strings[3]).append("  ").append(strings[4]);
        });
        return stringBuilder.toString();
    }

    public String showBodyEquipment(Player player) {
        List<Equipment> equipments = player.getEquipments();
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasEquipment = false;
        for (Equipment equipment : equipments) {
            if (equipment.getName() != null) {
                hasEquipment = true;
                break;
            }
        }
        if (!hasEquipment) {
            return stringBuilder.append("玩家身上没有穿戴装备").toString();
        }
        equipments.forEach(equipment -> {
            if (equipment.getName() != null) {
                String[] goodInfoById = goodManager.getEquipmentInfoById(equipment.getGoodId());
                stringBuilder.append("\n装备名称: ").append(goodInfoById[GoodManager.GOOD_NAME]).append(" 等级: ")
                        .append(equipment.getLevel()).append(" 装备部位: ").append(goodInfoById[GoodManager.EQUIPMENT_TYPE])
                        .append(" 描述: ").append(goodInfoById[GoodManager.GOOD_DESCRIPTION]);
            }
        });
        return stringBuilder.toString();
    }

    // .append(goodInfoById[2]).append(" 攻击力: ").append(equipment.getAttackPower())
    //                    .append(" 魔法力: ").append(equipment.getMagicPower()).append(" 增加血量: ").append(equipment.getPlusHp())
    //                    .append(" 增加魔法值: ").append(equipment.getPlusMp()).append(" 增加防御力: ").append(equipment.getDefense())
    //                    .append(" 增加暴击率: ").append(equipment.getCriticalRate()).append(" 最大耐久度: ").append(equipment.getMaxDurable())
    //                    .append(" 当前耐久度: ").append(equipment.getDurable()).append(" 使用最小等级: ").append(equipment.getMinLevel())
    //
}
