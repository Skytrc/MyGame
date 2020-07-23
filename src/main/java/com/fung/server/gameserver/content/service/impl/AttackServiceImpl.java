package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.equipment.EquipmentDurable;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.monster.MonsterAction;
import com.fung.server.gameserver.content.domain.monster.MonsterDropCreated;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
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
    public String attack1(String channelId, int x, int y, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        // 丢到对应地图线程中处理
        mapActor.addMessage(gameMapActor -> {
            // TODO 需要在初始化时就设置好
            if (player.getBuffManager().getGameMapActor() == null){
                player.getBuffManager().setGameMapActor(gameMapActor);
            }
            LOGGER.info(Thread.currentThread().getName());
            GameMap currentPlayerMap = mapActor.getGameMap();
            BaseHostileMonster monster = currentPlayerMap.getMonsterByXy(x, y);
            if (!player.getBuffManager().canAction()) {
                writeMessage2Client.writeMessage(channelId, "\n无法攻击");
                return;
            }
            if (monster == null) {
                writeMessage2Client.writeMessage(channelId, "\n位置[" + x + "," + y + "] 没有敌对生物");
                return;
            }

            DamageSkill skill = player.getSkillManager().useDamageSkill(skillId);
            if (skill == null) {
                writeMessage2Client.writeMessage(channelId, "\n没有该技能或技能在CD中");
                return;
            }

            if (!attackCalculate.calculateAttackDistance(player, skill.getSkillDistance(), x, y)) {
                writeMessage2Client.writeMessage(channelId, "\n攻击距离不够  当前技能攻击距离为: " + player.getAttackDistance() + skill.getSkillDistance());
                return;
            }
            if (player.getMagicPoint() < skill.getRequireMagicPoint()) {
                writeMessage2Client.writeMessage(channelId, "\n蓝量不足，无法释放技能");
                return;
            }
            if (monster.getHealthPoint() <= 0) {
                writeMessage2Client.writeMessage(channelId, "\n" + monster.getName() + "  已死亡");
                return;
            }

            int minusHp;
            int monsterOldHealthPoint = monster.getHealthPoint();
            // 判断物理还是魔法攻击
            if (skill.getPhysicalDamage() != 0) {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                        player.getTotalCriticalRate(), skill.getPhysicalDamage(), monster.getDefend());
                monster.setHealthPoint(monster.getHealthPoint() - minusHp);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n玩家: " + player.getName() + "对怪物: "
                        + monster.getName() + " 造成 " + minusHp + " 物理伤害" + "  怪物目前血量: "
                        + monster.getHealthPoint() + "\n");
            } else {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalMagicPower(), skill.getMagicDamage(),
                        monster.getDefend());
                monster.setHealthPoint(monster.getHealthPoint() - minusHp);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n玩家: " + player.getName() + "对怪物: "
                        + monster.getName() + " 造成 " + minusHp + " 魔法伤害" + "  怪物目前血量: "
                        + monster.getHealthPoint() + "\n");
            }

            // 装备耐久计算
            equipmentDurable.equipmentDurableMinus(player, false);
            if (monsterOldHealthPoint < minusHp) {
                monster.setHealthPoint(0);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "玩家: " + player.getName()
                        +"\n击败怪兽: " + monster.getName() + "\n");
                // 怪物掉落计算
                monsterDeathSettlement(monster, player, channelId, gameMapActor);
                return;
            }
            // buff 处理
            Buff buff = skill.getBuff();
            if (buff != null) {
                monster.getUnitBuffManager().putOnBuff(buff);
            }

            // 开启怪物攻击
            if (!monster.isAttacking()) {
                monster.setCurrentAttackPlayer(player);
                mapActor.addMessage(h -> monsterAction.attackPlayer0(channelId, player, monster, gameMapActor));
            }
        });
        return "";
    }

    public void monsterDeathSettlement(BaseHostileMonster monster, Player player, String channelId, GameMapActor gameMapActor) {
        // 死亡结算 TODO  数据库保存
        player.getPlayerCommConfig().addMoney(monster.getValue());
        player.addExp(monster.getExp());
        writeMessage2Client.writeMessage(channelId, "增加经验: " + monster.getExp() + "\n增加金钱: " + monster.getValue());
        monsterAction.rebirth(monster, gameMapActor);
        monsterDrop(monster, player, gameMapActor.getGameMap());
    }

    public void monsterDrop(BaseHostileMonster monster, Player player, GameMap gameMap) {
        List<FallingGood> fallingGoods = monsterDropCreated.goodsCreated(monster, player);
        if (fallingGoods != null) {
            GameMap map = mapManager.getMapByMapId(monster.getInMapId());
            map.putFallingGoodInMap(fallingGoods, monster.getInMapX(), monster.getInMapY());
            writeMessage2Client.writeMessage2MapPlayer(gameMap, monsterDropMessage(fallingGoods, monster.getInMapX(), monster.getInMapY()));
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
