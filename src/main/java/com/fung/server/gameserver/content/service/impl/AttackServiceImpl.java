package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.monster.NormalMonster;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.equipment.EquipmentDurable;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.monster.MonsterAction;
import com.fung.server.gameserver.content.domain.monster.MonsterDropCreated;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.AttackService;
import com.fung.server.gameserver.content.threadpool.AttackThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private PlayerInfo playerInfo;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private AttackCalculate attackCalculate;

    @Autowired
    private AttackThreadPool attackThreadPool;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private EquipmentDurable equipmentDurable;

    @Autowired
    private MonsterAction monsterAction;

    @Autowired
    private MonsterDropCreated monsterDropCreated;

    @Autowired
    private SkillManager skillManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    private static final Logger LOGGER = LoggerFactory.getLogger(AttackService.class);

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

    @Override
    public String attack1(String channelId, int x, int y, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        // 判斷是否在副本內
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        // 丢到对应地图线程中处理
        mapActor.addMessage(gameMapActor -> {
            LOGGER.info(Thread.currentThread().getName());
            GameMap currentPlayerMap = mapActor.getGameMap();
            BaseHostileMonster monster = currentPlayerMap.getMonsterByXy(x, y);
            if (monster == null) {
                writeMessage2Client.writeMessage(channelId, "\n位置[" + x + "," + y + "] 没有敌对生物");
                return;
            }
            if (!attackCalculate.calculateAttackDistance(player, x, y)) {
                writeMessage2Client.writeMessage(channelId, "\n攻击距离不够  当前攻击距离为: " + player.getAttackDistance());
                return;
            }
            if (monster.getHealthPoint() <= 0) {
                writeMessage2Client.writeMessage(channelId, "\n" + monster.getName() + "  已死亡");
            }
            int minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                    player.getTotalCriticalRate(), skillManager.getSkillById(skillId).getPhysicalDamage(),
                    monster.getDefend());
            // 装备耐久计算
            equipmentDurable.equipmentDurableMinus(player, false);
            if (monster.getHealthPoint() < minusHp) {
                monster.setHealthPoint(0);
                writeMessage2Client.writeMessage(channelId, "\n对怪物: " + monster.getName() + "  造成" + minusHp +"伤害  击败怪物\n");
                // 怪物掉落计算
                monsterDeathSettlement(monster, player, channelId, gameMapActor);
                return;
            }
            monster.setHealthPoint(monster.getHealthPoint() - minusHp);
            // 开启怪物攻击
            if (!monster.isAttacking()) {
                monster.setCurrentAttackPlayer(player);
                mapActor.addMessage(h -> monsterAction.attackPlayer0(channelId, player, monster, gameMapActor));
            }
            writeMessage2Client.writeMessage(channelId, "\n对怪物: " + monster.getName() + " 造成 " + minusHp + " 伤害" + "  怪物目前血量: " + monster.getHealthPoint() + "\n");
        });
        return "";
    }

    public void monsterDeathSettlement(BaseHostileMonster monster, Player player, String channelId, GameMapActor gameMapActor) {
        // 死亡结算 TODO  数据库保存
        player.getPlayerCommConfig().addMoney(monster.getValue());
        player.addExp(monster.getExp());
        monsterAction.rebirth(monster, gameMapActor);
        monsterDrop(monster, player, channelId);
    }

    public void monsterDrop(BaseHostileMonster monster, Player player, String channelId) {
        List<FallingGood> fallingGoods = monsterDropCreated.goodsCreated(monster, player);
        if (fallingGoods != null) {
            GameMap map = mapManager.getMapByMapId(monster.getInMapId());
            map.putFallingGoodInMap(fallingGoods, monster.getInMapX(), monster.getInMapY());
            writeMessage2Client.writeMessage(channelId, monsterDropMessage(fallingGoods, monster.getInMapX(), monster.getInMapY()));
        }
    }

    public String monsterDropMessage(List<FallingGood> fallingGoodList, int x, int y) {
        StringBuilder stringBuilder = new StringBuilder("\n地图[ ");
        stringBuilder.append(x).append(" , ").append(y).append(" ] 掉落: \n");
        for (FallingGood fallingGood : fallingGoodList) {
            stringBuilder.append(fallingGood.getName()).append(" 数量:").append(fallingGood.getGood().getQuantity())
                    .append(" 属于玩家 ").append(fallingGood.getBeingToPlayer().getPlayerName()).append("\n");
        }
        return stringBuilder.toString();
    }
}
