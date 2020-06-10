package com.fung.server.content.service.impl;

import com.fung.server.content.config.manager.SkillManager;
import com.fung.server.content.config.map.GameMap;
import com.fung.server.content.config.monster.Monster;
import com.fung.server.content.dao.EquipmentDao;
import com.fung.server.content.domain.calculate.AttackCalculate;
import com.fung.server.content.domain.equipment.EquipmentDurable;
import com.fung.server.content.domain.player.OnlinePlayer;
import com.fung.server.content.domain.player.PlayerInfo;
import com.fung.server.content.entity.Equipment;
import com.fung.server.content.entity.Player;
import com.fung.server.content.service.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 16:41
 */
@Component
public class AttackServiceImpl implements AttackService {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    OnlinePlayer onlinePlayer;

    @Autowired
    AttackCalculate attackCalculate;

    @Autowired
    EquipmentDurable equipmentDurable;

    @Autowired
    SkillManager skillManager;

    @Autowired
    EquipmentDao equipmentDao;

    @Override
    public String attack(String channelId, int x, int y, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        // 判断位置是否有怪
        GameMap currentPlayerMap = playerInfo.getCurrentPlayerMap(player);
        Monster monster = currentPlayerMap.getMonsterByXy(x, y);
        if (monster == null) {
            return "\n位置[" + x + "," + y + "] 没有敌对生物";
        }
        if (!attackCalculate.calculateAttackDistance(player, x, y)) {
            return "\n攻击距离不够  当前攻击距离为: " + player.getAttackDistance();
        }
        // 开始进行攻击判断
        // TODO 加锁
        int minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                player.getTotalCriticalRate(), skillManager.getSkillById(skillId).getPhysicalDamage(),
                monster.getDefend());
        equipmentDurable.equipmentDurableMinus(player, false);
        if (monster.getHealthPoint() < minusHp) {
            // TODO 死亡结算
            // 装备更新到数据库
            List<Equipment> equipments = player.getEquipments();
            equipments.forEach(equipmentDao::updateEquipment);

            // 经验获取
            player.setExp(monster.getExp());

            monster.setHealthPoint(monster.getMaxHealthPoint());
            return "\n对怪物: " + monster.getName() + "  造成" + minusHp +"伤害  击败怪物\n";
        }
        monster.setHealthPoint(monster.getHealthPoint() - minusHp);
        return "\n对怪物: " + monster.getName() + " 造成 " + minusHp + " 伤害" + "  怪物目前血量: " + monster.getHealthPoint() + "\n";
    }
}
