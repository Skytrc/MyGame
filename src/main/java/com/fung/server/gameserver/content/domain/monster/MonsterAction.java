package com.fung.server.gameserver.content.domain.monster;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.monster.BaseMonster;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author skytrc@163.com
 * @date 2020/6/15 15:12
 */
@Component
public class MonsterAction {

    @Autowired
    AttackCalculate attackCalculate;

    @Autowired
    SkillManager skillManager;

    @Autowired
    WriteMessage2Client writeMessage2Client;

    public static int checkAttackTime = 100;

    public static int hatedTime = 10_000;

    Lock lock;

    public MonsterAction() {
        lock = new ReentrantLock();
    }

    /**
     * 固定攻击单一玩家
     * @param player 玩家
     */
    @Deprecated
    public void attackPlayer(String channelId, Player player, BaseMonster monster) throws InterruptedException {
        int unleashSkillCount = 0;
        monster.setAttacking(true);
        while (true) {
            lock.lock();
            try {
                // 检测怪物是否死亡
                if (monster.getHealthPoint() <= 0) {
                    break;
                }
                // 判断玩家是否在攻击范围内
                if (!attackCalculate.calculateAttackDistance(monster, player)) {
                    break;
                }
                // 判断玩家血量是否为空
                if (player.getHealthPoint() <= 0) {
                    break;
                }
            } finally {
                lock.unlock();
            }

            // 怪物技能模块
            Skill skill = monsterSelectSkill(monster.getMonsterSkill(), unleashSkillCount++);
            DamageSkill damageSkill = skillManager.getSkillById(skill.getId());
            int totalDamage;
            lock.lock();
            try {
                totalDamage = attackCalculate.defenderHpCalculate(monster.getAttackPower(), damageSkill.getPhysicalDamage(), player.getTotalDefense());
                player.setHealthPoint(player.getHealthPoint() - totalDamage);
            } finally {
                lock.unlock();
            }

            String message = "\n 怪物使用技能: " + damageSkill.getName() + " 造成伤害: " + totalDamage + " 玩家还剩血量: " + player.getHealthPoint();
            writeMessage2Client.writeMessage(channelId, message);

            Thread.sleep(damageSkill.getCd() * 1000);
        }
        monster.setAttacking(false);
        monster.setCurrentAttackPlayer(null);
    }

    public void attackPlayer0(String channelId, Player player, BaseMonster monster, GameMapActor gameMapActor) {
        attackPlayer1(channelId, player, 0, monster,gameMapActor);
    }

    public void attackPlayer1(String channelId, Player player, int unleashSkillCount, BaseMonster monster, GameMapActor gameMapActor) {
        monster.setAttacking(true);

        // 玩家下線
        if(channelId == null) {
            monster.setCurrentAttackPlayer(null);
            return;
        }
        // 检测怪物是否死亡
        if (monster.getHealthPoint() <= 0) {
            monster.setCurrentAttackPlayer(null);
            rebirth(monster, gameMapActor);
            return;
        }
        // 判断玩家是否在攻击范围内
        if (!attackCalculate.calculateAttackDistance(monster, player)) {
            // 进入检查
            gameMapActor.schedule(gameMapActor1 -> {
                checkAttackTarget(channelId, monster, player, 0, gameMapActor);
            }, checkAttackTime);
            return;
        }
        // 判断玩家血量是否为空
        if (player.getHealthPoint() <= 0) {
            monster.setCurrentAttackPlayer(null);
            return;
        }

        // TODO 技能模塊需要單獨拉出來寫
        // 怪物技能模块
        Skill skill = monsterSelectSkill(monster.getMonsterSkill(), unleashSkillCount);
        DamageSkill damageSkill = skillManager.getSkillById(skill.getId());
        int totalDamage = attackCalculate.defenderHpCalculate(monster.getAttackPower(), damageSkill.getPhysicalDamage(), player.getTotalDefense());
        player.setHealthPoint(player.getHealthPoint() - totalDamage);

        // 根据技能cd 推送下次攻击
        gameMapActor.schedule(gameMapActor1 -> {
            attackPlayer1(channelId, player, unleashSkillCount + 1, monster, gameMapActor);
        }, damageSkill.getCd(), TimeUnit.SECONDS);

        // 发送怪物攻击消息
        String message = "\n 怪物使用技能: " + damageSkill.getName() + " 造成伤害: " + totalDamage + " 玩家还剩血量: " + player.getHealthPoint();
        writeMessage2Client.writeMessage(channelId, message);

    }

    /**
     * 重生
     */
    public void rebirth(BaseMonster monster) {
        monster.setHealthPoint(monster.getMaxHealthPoint());
    }

    public void rebirth(BaseMonster monster, GameMapActor gameMapActor) {
        gameMapActor.schedule(actor -> {
            rebirth(monster);
        }, 15, TimeUnit.SECONDS);
    }

    /**
     * 检查攻击目标
     */
    public void checkAttackTarget(String channelId, BaseMonster monster, Player player, int time, GameMapActor gameMapActor) {
        // 检查间隔时间
        if (time + checkAttackTime < hatedTime) {
            // 判断玩家是否在攻击范围内
            if (attackCalculate.calculateAttackDistance(monster, player)) {
                // 在，攻击
                gameMapActor.addMessage(actor -> {
                    attackPlayer0(channelId, player, monster, gameMapActor);
                });
            } else {
                // 不在，隔一定时间再检查
                gameMapActor.schedule(actor ->{
                    checkAttackTarget(channelId, monster, player, time + checkAttackTime, gameMapActor);
                }, checkAttackTime);
            }
        } else {
            monster.setCurrentAttackPlayer(null);
        }
    }

    public Skill monsterSelectSkill(List<Skill> skills, int unleashSkillCount) {
        return skills.get((unleashSkillCount % skills.size()));
    }
}
