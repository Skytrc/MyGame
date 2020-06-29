package com.fung.server.gameserver.content.domain.monster;

import com.fung.server.gameserver.channelstore.AsynWriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.config.monster.BaseMonster;
import com.fung.server.gameserver.content.config.skill.DamageSkill;
import com.fung.server.gameserver.content.domain.calculate.AttackCalculate;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
    AsynWriteMessage2Client writeMessage2Client;

    Lock lock;

    public MonsterAction() {
        lock = new ReentrantLock();
    }

    /**
     * 固定攻击单一玩家
     * @param player 玩家
     */
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

    public Skill monsterSelectSkill(List<Skill> skills, int unleashSkillCount) {
        return skills.get((unleashSkillCount % skills.size()));
    }
}
