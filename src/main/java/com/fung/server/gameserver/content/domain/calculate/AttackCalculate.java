package com.fung.server.gameserver.content.domain.calculate;

import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseMonster;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.content.entity.Unit;
import org.springframework.stereotype.Component;

import java.util.List;
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

    /**
     * 计算玩家攻击距离
     */
    public boolean calculateAttackDistance(Player player, int skillDistance, int monsterInx, int monsterIny) {
        int playerInx = player.getInMapX();
        int playerIny = player.getInMapY();
        int distance = Math.abs(monsterInx - playerInx) + Math.abs(monsterIny - playerIny);
        return distance <= player.getAttackDistance() + skillDistance;
    }

    /**
     * 计算怪物攻击距离
     */
    public boolean calculateAttackDistance(BaseMonster monster, Player player) {
        int distance = Math.abs(monster.getInMapX() - player.getInMapX()) + Math.abs(monster.getInMapY() - player.getInMapY());
        return distance <= monster.getAttackDistance();
    }

    /**
     * 计算攻击距离
     */
    public boolean calculateAttackDistance(Unit unit, Unit beAttackUnit, Skill skill) {
        double distance = Math.sqrt(Math.pow(Math.abs(unit.getTempX() - beAttackUnit.getTempX()), 2) + Math.pow(Math.abs(unit.getTempY() - beAttackUnit.getTempY()), 2));
        return distance <= unit.getAttackDistance() + skill.getSkillDistance();

    }

    /**
     * TODO AOE计算
     */
    public List<Integer> calculateAoeRange(GameMap gameMap, int centerX, int centerY, int distance) {
        int gameMapX = gameMap.getX();
        int gameMapY = gameMap.getY();
        return null;
    }



}
