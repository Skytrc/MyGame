package com.fung.server.gameserver.content.domain.monster;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.domain.buff.UnitBuffManager;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.calculate.MoveCalculate;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 15:12
 */
@Component
public class MonsterAction {

    @Autowired
    private AttackCalculate attackCalculate;

    @Autowired
    private SkillManager skillManager;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    private static final Logger LOGGER = LoggerFactory.getLogger(MonsterAction.class);

    public static int checkAttackTime = 100;

    public static int hatedTime = 10_000;

    public static int checkRangeCd = 1000;

    public void standBy(BaseHostileMonster monster, GameMapActor gameMapActor) {
        GameMap gameMap = gameMapActor.getGameMap();
        Player player = checkRange(monster, gameMap);
        // 检测不到玩家继续stand By
        if (player == null) {
            gameMapActor.schedule(gameMapActor1 -> {
                standBy(monster, gameMapActor);
            }, checkRangeCd);
        } else {
            attackPlayer0(onlinePlayer.getChannelIdByPlayerId(player.getUuid()), player, monster, gameMapActor);
        }
//        LOGGER.info(String.format("current thread %s", Thread.currentThread().getName()));
    }

    public void attackPlayer0(String channelId, Player player, BaseHostileMonster monster, GameMapActor gameMapActor) {
        attackPlayer1(channelId, player, 0, monster,gameMapActor);
    }

    public void attackPlayer1(String channelId, Player player, int unleashSkillCount, BaseHostileMonster monster, GameMapActor gameMapActor) {
        // TODO 确定attacking标志位有用?
        monster.setAttacking(true);

        // 玩家下線
        if(channelId == null) {
            monster.setCurrentAttackPlayer(null);
            // 检测是否离开位置，go back&stand by
            checkAndGoBackPlace(gameMapActor, monster);
            return;
        }
        // 检测怪物是否死亡
        if (monster.getHealthPoint() <= 0) {
            return;
        }

        // 检测是否达到攻击距离, 达不到则移动
        // 怪物技能模块
        Skill skill = monsterSelectSkill(monster.getSkills(), unleashSkillCount);
        DamageSkill damageSkill = skillManager.getSkillById(skill.getId());
        if (!attackCalculate.calculateAttackDistance(monster, player, skill)) {
            monsterMoveAndCheckAttack(gameMapActor, monster, player, channelId, skill);
            return;
        }

        // TODO 不在范围内移动
        // 判断玩家是否在攻击范围内
//        if (!attackCalculate.calculateAttackDistance(monster, player, damageSkill)) {
//            // 进入检查
//            gameMapActor.schedule(gameMapActor1 -> {
//                checkAttackTarget(channelId, monster, player, 0, gameMapActor, damageSkill);
//            }, checkAttackTime);
//            return;
//        }


        // 判断玩家血量是否为空
        if (player.getHealthPoint() <= 0) {
            monster.setCurrentAttackPlayer(null);
            // 判断怪物是否在原位，不在则回去
            if (monster.getTempX() != monster.getInMapX() || monster.getTempY() != monster.getInMapY()) {
                checkAndGoBackPlace(gameMapActor, monster);
            }
            return;
        }
        // 如果中了不可行动的buff，重复检查
        UnitBuffManager unitBuffManager = monster.getUnitBuffManager();
        if (unitBuffManager.canAction()){
            gameMapActor.schedule(gameMapActor1 -> {
                attackPlayer1(channelId, player, unleashSkillCount, monster, gameMapActor);
            }, 1, TimeUnit.SECONDS);
            return;
        }

        int totalDamage = attackCalculate.defenderHpCalculate(monster.getAttackPower(), damageSkill.getPhysicalDamage(), player.getTotalDefense());
        player.setHealthPoint(player.getHealthPoint() - totalDamage);

        // 根据技能cd 推送下次攻击
        gameMapActor.schedule(gameMapActor1 -> {
            attackPlayer1(channelId, player, unleashSkillCount + 1, monster, gameMapActor);
        }, damageSkill.getCd(), TimeUnit.SECONDS);

        // 发送怪物攻击消息
        String message = "\n 怪物使用技能: " + damageSkill.getName() + " 造成伤害: " + totalDamage + " 玩家还剩血量: " + player.getHealthPoint();
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), message);
    }

    /**
     * 重生
     */
    public void rebirthFinish(BaseHostileMonster monster, GameMapActor gameMapActor) {
        monster.setHealthPoint(monster.getMaxHealthPoint());
        monster.setAttacking(false);
        monster.setRebirth(false);
        monster.setTempX(monster.getInMapX());
        monster.setTempY(monster.getInMapY());
        // 重生玩進入stand by狀態
        if (monster.getIsAutoAttack() == 1) {
            standBy(monster, gameMapActor);
        }
    }

    public void rebirth(BaseHostileMonster monster, GameMapActor gameMapActor) {
        if (monster.isRebirth()) {
            return;
        }
        monster.setRebirth(true);
        gameMapActor.schedule(actor -> {
            rebirthFinish(monster, gameMapActor);
            LOGGER.info(monster.getName() + "重生中");
        }, 15, TimeUnit.SECONDS);
    }

    /**
     * 检查攻击目标
     */
    public void checkAttackTarget(String channelId, BaseHostileMonster monster, Player player, int time, GameMapActor gameMapActor, Skill skill) {
        // 检查间隔时间
        if (time + checkAttackTime < hatedTime) {
            // 判断玩家是否在攻击范围内
            if (attackCalculate.calculateAttackDistance(monster, player, skill)) {
                // 在，攻击
                gameMapActor.addMessage(actor -> {
                    attackPlayer0(channelId, player, monster, gameMapActor);
                });
            } else {
                // 不在，隔一定时间再检查
                gameMapActor.schedule(actor ->{
                    checkAttackTarget(channelId, monster, player, time + checkAttackTime, gameMapActor, skill);
                }, checkAttackTime);
            }
        } else {
            monster.setCurrentAttackPlayer(null);
        }
    }

    /**
     * 怪物根据范围检测，简单遍历周围坐标
     */
    public Player checkRange(BaseHostileMonster monster, GameMap gameMap) {
        int sensingRange = monster.getSensingRange();
        int tempX = monster.getTempX();
        int tempY = monster.getTempY();

        int minSensingX = Math.max(tempX - sensingRange, 1);
        int minSensingY = Math.max(tempY - sensingRange, 1);
        int maxSensingX = Math.min(tempX + sensingRange, gameMap.getX());
        int maxSensingY = Math.min(tempY + sensingRange, gameMap.getY());

        // 空
        if (gameMap.getPlayerInPosition().size() == 0) {
            return null;
        }

        for (int i = minSensingX; i <= maxSensingX; i++) {
            for (int j = minSensingY; j < maxSensingY; j++) {
                List<Player> players = gameMap.getPlayerInPosition().get(gameMap.xy2Location(i, j));
                if (players != null) {
                    if (players.size() != 0) {
                        return players.get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 怪物根据技能列表简单实用技能
     */
    public Skill monsterSelectSkill(List<Skill> skills, int unleashSkillCount) {
        return skills.get((unleashSkillCount % skills.size()));
    }

    /**
     * 怪物移动&攻击检查
     */
    public void monsterMoveAndCheckAttack(GameMapActor gameMapActor,BaseHostileMonster monster, Player player, String channelId, Skill skill) {
        // 如果移动距离过远，回去原来的位置
        if (monster.getHealthPoint() == 0) {
            return;
        }
        if (monster.tooFar()) {
            checkAndGoBackPlace(gameMapActor, monster);
            return;
        }
        // 如果达到攻击距离攻击
        if (attackCalculate.calculateAttackDistance(monster, player, skill)) {
            gameMapActor.addMessage(gameMapActor1 -> {
                // TODO 重写 attack
                attackPlayer0(channelId, player, monster, gameMapActor);
            });
            return;
        }

        int x0 = monster.getTempX();
        int y0 = monster.getTempY();

        int playerInMapX = player.getInMapX();
        int playerInMapY = player.getInMapY();

        if (playerInMapX > x0){
            x0++;
        } else if (playerInMapX < x0) {
            x0--;
        }

        if (playerInMapY > y0){
            y0++;
        } else if (playerInMapY < y0) {
            y0--;
        }
        monster.setTempX(x0);
        monster.setTempY(y0);

        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + "追击敌人移动: " + moveResult(monster, x0, y0));
        gameMapActor.schedule(gameMapActor1 -> {
            monsterMoveAndCheckAttack(gameMapActor, monster,  player, channelId, skill);
        }, MoveCalculate.MOVE_CD);
    }

    public void checkAndGoBackPlace(GameMapActor gameMapActor, BaseHostileMonster monster) {
        monster.setCurrentAttackPlayer(null);
        // 如果在原点就stand by
        if (monster.getTempY() == monster.getInMapY() && monster.getTempX() == monster.getInMapX()) {
            standBy(monster, gameMapActor);
            return;
        }
        // 回原位，攻击目标清空
        monsterMoveGrid(gameMapActor, monster);
        //TODO 移动并 Stand by
    }

    /**
     * 怪物回原位(直线生成算法)
     */
    public void monsterMoveGrid(GameMapActor gameMapActor, BaseHostileMonster monster) {
        int x0 = monster.getTempX();
        int y0 = monster.getTempY();
        int x1 = monster.getInMapX();
        int y1 = monster.getInMapY();
        float length = Math.max(Math.abs(x1 - x0),Math.abs(y1 - y0));

        float dx = (x1 - x0) / length;
        float dy = (y1 - y0) / length;
        if (monster.getHealthPoint() != 0) {
            gameMapActor.addMessage(gameMapActor1 -> {
                monsterMoverGrid0(gameMapActor, monster, x0, y0, dx, dy, 0, length);
            });
        }
    }

    public void monsterMoverGrid0(GameMapActor gameMapActor, BaseHostileMonster monster, float x0, float y0, float dx, float dy,
                                  int monsterHasMoveDistance, float distance) {
        if (monster.getHealthPoint() <= 0) {
            return;
        }
        if (monsterHasMoveDistance <= distance) {
            monster.setTempX((int) (x0 + 0.5));
            monster.setTempY((int) (y0 + 0.5));
            writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + "回原位移动: "+ moveResult(monster, monster.getTempX(), monster.getTempY()));
            if (monster.getHealthPoint() != 0) {
                gameMapActor.schedule(gameMapActor1 -> {
                    monsterMoverGrid0(gameMapActor, monster, x0 + dx, y0 + dy, dx, dy, monsterHasMoveDistance + 1, distance);
                }, MoveCalculate.MOVE_CD);
            }
        } else { // 到达，进入stand by状态
            standBy(monster, gameMapActor);
        }

    }

    public String moveResult(BaseHostileMonster monster, int x0, int y0) {
        return "\n" + monster.getName() + " 移动到"
                + " [ " + x0 + "," + y0 + " ]";
    }
}
