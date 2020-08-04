package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.config.skill.TreatmentSkill;
import com.fung.server.gameserver.content.dao.PlayerDao;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.equipment.EquipmentDurable;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.monster.MonsterAction;
import com.fung.server.gameserver.content.domain.monster.MonsterDropCreated;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.domain.team.StoredTeam;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Unit;
import com.fung.server.gameserver.content.service.AttackService;
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
    private StoredTeam storedTeam;

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
    private PlayerDao playerDao;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    private static final Logger LOGGER = LoggerFactory.getLogger(AttackService.class);

    @Override
    public String attack1(String channelId, int x, int y, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        // 丢到对应地图线程中处理
        mapActor.addMessage(gameMapActor -> {
            LOGGER.info(Thread.currentThread().getName());
            GameMap gameMap = mapActor.getGameMap();
            BaseHostileMonster monster = gameMap.getMonsterByXy(x, y);

            if (monster == null) {
                writeMessage2Client.writeMessage(channelId, "\n位置[" + x + "," + y + "] 没有敌对生物");
                return;
            }

            DamageSkill skill = player.getSkillManager().useDamageSkill(skillId);
            if (attackJudgement(player, monster, skill, channelId, x, y)) {
                return;
            }

            writeMessage2Client.writeMessage2MapPlayer(gameMap, "\n" + player.getPlayerName() + " 使用技能" + skill.getName());

            int minusHp;
            int monsterOldHealthPoint = monster.getHealthPoint();
            // 判断物理还是魔法攻击
            if (skill.getPhysicalDamage() != 0) {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                        player.getTotalCriticalRate(), skill.getPhysicalDamage(), monster.getDefend());
                monster.setHealthPoint(monster.getHealthPoint() - minusHp);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), attackResult(player, monster, minusHp, true));
            } else {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalMagicPower(), skill.getMagicDamage(),
                        monster.getDefend());
                monster.setHealthPoint(monster.getHealthPoint() - minusHp);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), attackResult(player, monster, minusHp, false));
            }

            // 装备耐久计算
            equipmentDurable.equipmentDurableMinus(player, false);
            if (monsterOldHealthPoint <= minusHp) {
                monster.setHealthPoint(0);
                writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n玩家: " + player.getName()
                        +"\n击败怪兽: " + monster.getName() + "\n");
                // 怪物掉落计算
                monsterDeathSettlement(monster, player, channelId, gameMapActor);
                return;
            }
            // buff 处理
            Buff buff = skill.getBuff();
            if (buff != null) {
                monster.getUnitBuffManager().putOnBuff(buff, gameMapActor, writeMessage2Client);
            }

            // 开启怪物攻击
            if (monster.getCurrentAttackPlayer() == null) {
                monster.setCurrentAttackPlayer(player);
                mapActor.addMessage(h -> monsterAction.attackPlayer0(channelId, player, monster, gameMapActor));
            }
        });
        return "";
    }

    @Override
    public String attackAoe(String channelId, int skillId, int x, int y) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        mapActor.addMessage(playerActor1 -> {
            GameMap gameMap = mapActor.getGameMap();
        });
        return "";
    }

    @Override
    public String attackPlayer(String channelId, String playerId, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        mapActor.addMessage(gameMapActor -> {
            GameMap gameMap = mapActor.getGameMap();
            String beAttackedChannelId = onlinePlayer.getChannelIdByPlayerId(playerId);
            if (beAttackedChannelId == null) {
                writeMessage2Client.writeMessage(channelId, "\n没有该名玩家");
                return;
            }
            if (!gameMap.getPlayChannel().contains(beAttackedChannelId)) {
                writeMessage2Client.writeMessage(channelId, "\n玩家不在同个地图上");
                return;
            }
            // TODO 判断地图是否能攻击
            Player beAttackPlayer = onlinePlayer.getPlayerByChannelId(beAttackedChannelId);
            DamageSkill skill = player.getSkillManager().useDamageSkill(skillId);
            if (attackJudgement(player, beAttackPlayer, skill, channelId, beAttackPlayer.getInMapX(), beAttackPlayer.getInMapY())) {
                return;
            }
            writeMessage2Client.writeMessage2MapPlayer(gameMap, "\n" + player.getPlayerName() + " 使用技能" + skill.getName());

            int minusHp;
            if (skill.getPhysicalDamage() != 0) {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalAttackPower(),
                        player.getTotalCriticalRate(), skill.getPhysicalDamage(), beAttackPlayer.getTotalDefense());
                beAttackPlayer.setHealthPoint(Math.max(beAttackPlayer.getHealthPoint() - minusHp, 0));
                writeMessage2Client.writeMessage2MapPlayer(gameMap, attackResult(player, beAttackPlayer, minusHp, true));
            } else {
                minusHp = attackCalculate.defenderHpCalculate(player.getTotalMagicPower(), skill.getMagicDamage(), beAttackPlayer.getTotalDefense());
                beAttackPlayer.setHealthPoint(Math.max(beAttackPlayer.getHealthPoint() - minusHp, 0));
                writeMessage2Client.writeMessage2MapPlayer(gameMap, attackResult(player, beAttackPlayer, minusHp, false));
            }

            // 装备耐久计算（攻击者&被攻击者）
            equipmentDurable.equipmentDurableMinus(player, false);
            equipmentDurable.equipmentDurableMinus(beAttackPlayer, true);

            Buff buff = skill.getBuff();
            // buff处理
            if (buff != null) {
                beAttackPlayer.getBuffManager().putOnBuff(buff, gameMapActor, writeMessage2Client);
            }
        });
        return "";
    }

    @Override
    public String useTreatmentSkill(String channelId, String playerId, int skillId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        GameMapActor mapActor = mapManager.getGameMapActor(player);
        mapActor.addMessage(gameMapActor -> {
            GameMap gameMap = mapActor.getGameMap();
            PlayerActor playerActorByPlayerId = onlinePlayer.getPlayerActorByPlayerId(playerId);
            if (playerActorByPlayerId == null) {
                writeMessage2Client.writeMessage(channelId, "\n玩家未在线上");
                return;
            }
            Player beTreatPlayer = playerActorByPlayerId.getPlayer();
            if (beTreatPlayer.getInMapId() != player.getInMapId()) {
                writeMessage2Client.writeMessage(channelId, "\n玩家不在同一张地图上");
                return;
            }
            if (beTreatPlayer.getHealthPoint() == 0) {
                writeMessage2Client.writeMessage(channelId, "\n无法治疗，玩家已死亡");
                return;
            }
            TreatmentSkill treatmentSkill = player.getSkillManager().useTreatmentSkill(skillId);
            if (player.getMagicPoint() < treatmentSkill.getRequireMagicPoint()) {
                writeMessage2Client.writeMessage(channelId, "\n不够蓝量使用技能");
                return;
            }
            writeMessage2Client.writeMessage2MapPlayer(gameMap, "\n" + player.getPlayerName() + " 使用技能" + treatmentSkill.getName());

            if (treatmentSkill.getTreatmentAmount() !=0) {
                beTreatPlayer.setHealthPoint(Math.min(beTreatPlayer.getHealthPoint() + treatmentSkill.getTreatmentAmount(), player.getMaxHealthPoint()));
                writeMessage2Client.writeMessage2MapPlayer(gameMap,  "\n" + beTreatPlayer.getPlayerName()
                        + " 获得治疗: " + treatmentSkill.getTreatmentAmount() + " 当前生命值: " + beTreatPlayer.getHealthPoint());
            }

            Buff buff = treatmentSkill.getBuff();
            if (buff != null) {
                beTreatPlayer.getBuffManager().putOnBuff(buff, gameMapActor, writeMessage2Client);
            }

        });
        return "";
    }

    /**
     * 攻击判断，判断状态&技能
     */
    public boolean attackJudgement(Player player, Unit beAttackUnit, DamageSkill skill, String channelId, int x, int y) {
        if (player.getBuffManager().canAction()) {
            writeMessage2Client.writeMessage(channelId, "\n无法攻击");
            return true;
        }
        if (skill == null) {
            writeMessage2Client.writeMessage(channelId, "\n没有该技能或技能在CD中");
            return true;
        }
        if (beAttackUnit.getHealthPoint() == 0) {
            writeMessage2Client.writeMessage(channelId, "\n" + beAttackUnit.getName() + " 已死亡");
            return true;
        }
        if (!attackCalculate.calculateAttackDistance(player, skill.getSkillDistance(), x, y)) {
            writeMessage2Client.writeMessage(channelId, "\n攻击距离不够  当前技能攻击距离为: " + player.getAttackDistance() + skill.getSkillDistance());
            return true;
        }
        if (player.getMagicPoint() < skill.getRequireMagicPoint()) {
            writeMessage2Client.writeMessage(channelId, "\n蓝量不足，无法释放技能");
            return true;
        }
        return false;
    }

    public void monsterDeathSettlement(BaseHostileMonster monster, Player player, String channelId, GameMapActor gameMapActor) {
        // 死亡结算
        player.getPlayerCommConfig().addMoney(monster.getValue());
        player.addExp(monster.getExp());
        playerDao.insertOrUpdatePlayerCommConfig(player.getPlayerCommConfig());
        // 数据库更新用户状态
        playerDao.updatePlayer(player);
        writeMessage2Client.writeMessage(channelId, "增加经验: " + monster.getExp() + "\n增加金钱: " + monster.getValue());
        monster.setCurrentAttackPlayer(null);
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

    public String attackResult(Unit unit, Unit beAttackUnit, int minusHp, boolean isPhysicalAttack) {
        return "\n" + unit.getName() + " 对 " + beAttackUnit.getName() + " 造成: " + minusHp
                + (isPhysicalAttack ? " 物理伤害 " : " 魔法伤害 ") + " 当前 " + beAttackUnit.getName()
                + " 血量为: " + beAttackUnit.getHealthPoint() + "\n";

    }
}
