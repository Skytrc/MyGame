package com.fung.server.content.domain.monster;

import com.fung.server.content.config.monster.NormalMonster;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/9 16:33
 */
@Component
public class MonsterInfo {
    public String showMonster(NormalMonster normalMonster) {
        return "\n怪兽名称: " + normalMonster.getName() + "  最大生命值: " + normalMonster.getMaxHealthPoint() + "  目前生命值: " + normalMonster.getHealthPoint()
                + "  攻击力: " + normalMonster.getAttackPower() + "  魔法力: " + normalMonster.getMagicPower() + "  防御力" + normalMonster.getDefend()
                + "  击杀获得经验值: " +  normalMonster.getExp();
    }
}
