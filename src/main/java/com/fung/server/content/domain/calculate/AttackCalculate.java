package com.fung.server.content.domain.calculate;

import com.fung.server.content.config.monster.BaseMonster;
import com.fung.server.content.entity.Player;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 15:09
 */
@Component
public class AttackCalculate {

    private final Random RANDOM = new Random();

    public int defenderHpCalculate(int totalAttackPower, float totalCriticalRate,int skillValue, int totalDefense){
        if (calculateIsCriticalStrike(totalCriticalRate)) {
            totalAttackPower *= 2;
        }
        return Math.max(totalAttackPower + skillValue - totalDefense, 0);
    }

    public int defenderHpCalculate(int totalMagicPower,int skillValue, int totalDefense) {
        return Math.max(totalMagicPower + skillValue - totalDefense, 0);
    }

    public boolean calculateIsCriticalStrike(float criticalRate) {
        int rate = (int) (criticalRate * 10000);
        int randomValue = RANDOM.nextInt(10000);
        return rate > randomValue;
    }

    public boolean calculateAttackDistance(Player player, int monsterInx, int monsterIny) {
        int playerInx = player.getInMapX();
        int playerIny = player.getInMapY();
        int distance = Math.abs(monsterInx - playerInx) + Math.abs(monsterIny - playerIny);
        return distance <= player.getAttackDistance();
    }

    public boolean calculateAttackDistance(BaseMonster monster, Player player) {
        int distance = Math.abs(monster.getInMapX() - player.getInMapX()) + Math.abs(monster.getInMapY() - player.getInMapY());
        return distance <= monster.getAttackDistance();
    }
}
