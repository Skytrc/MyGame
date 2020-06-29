package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.equipment.EquipmentDurable;
import com.fung.server.gameserver.content.domain.monster.MonsterAction;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.AttackService;
import com.fung.server.gameserver.content.threadpool.AttackThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
    AttackThreadPool attackThreadPool;

    @Autowired
    EquipmentDurable equipmentDurable;

    @Autowired
    MonsterAction monsterAction;

    @Autowired
    SkillManager skillManager;

    @Override
    public String attack(String channelId, int x, int y, int skillId) throws InterruptedException {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        // 判断位置是否有怪
        GameMap currentPlayerMap = playerInfo.getCurrentPlayerMap(player);
        NormalMonster normalMonster = currentPlayerMap.getMonsterByXy(x, y);
        if (normalMonster == null) {
            return "\n位置[" + x + "," + y + "] 没有敌对生物";
        }
        if (!attackCalculate.calculateAttackDistance(player, x, y)) {
            return "\n攻击距离不够  当前攻击距离为: " + player.getAttackDistance();
        }
        int minusHp = 0;
        // 开始进行攻击判断 加锁
        synchronized (normalMonster) {
             minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                    player.getTotalCriticalRate(), skillManager.getSkillById(skillId).getPhysicalDamage(),
                    normalMonster.getDefend());
            equipmentDurable.equipmentDurableMinus(player, false);
            if (normalMonster.getHealthPoint() < minusHp) {
                // TODO 死亡结算 包括装备掉落、经验、金钱掉落
                normalMonster.setHealthPoint(0);
                return "\n对怪物: " + normalMonster.getName() + "  造成" + minusHp +"伤害  击败怪物\n";
            }
            normalMonster.setHealthPoint(normalMonster.getHealthPoint() - minusHp);
        }
        // 怪物反击,判断怪物是否正在攻击
        if (!normalMonster.isAttacking()) {
            attackThreadPool.getThreadPoolExecutor().submit(() -> {
                try {
                    monsterAction.attackPlayer(channelId, player, normalMonster);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        return "\n对怪物: " + normalMonster.getName() + " 造成 " + minusHp + " 伤害" + "  怪物目前血量: " + normalMonster.getHealthPoint() + "\n";
    }
}
