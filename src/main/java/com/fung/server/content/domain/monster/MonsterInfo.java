package com.fung.server.content.domain.monster;

import com.fung.server.content.config.monster.Monster;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/9 16:33
 */
@Component
public class MonsterInfo {
    public String showMonster(Monster monster) {
        return "\n怪兽名称: " + monster.getName() + "  最大生命值: " + monster.getMaxHealthPoint() + "  目前生命值: " + monster.getHealthPoint()
                + "  攻击力: " + monster.getAttackPower() + "  魔法力: " + monster.getMagicPower() + "  防御力" + monster.getDefend()
                + "  击杀获得经验值: " +  monster.getExp();
    }
}
