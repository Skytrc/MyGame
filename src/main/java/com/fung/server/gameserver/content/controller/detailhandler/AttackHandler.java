package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/9 15:40
 */
@Component
public class AttackHandler extends BaseInstructionHandler{

    @Autowired
    private AttackService attackService;

    @Override
    public String handler(List<String> ins) {
        String remove = ins.remove(0);
        if (("monster").equals(remove)) {
            return attackMonster(ins);
        } else if ("player".equals(remove)) {
            return attackPlayer(ins);
        } else if ("treat".equals(remove)) {
            return useTreatment(ins);
        }
        return "攻击指令错误";
    }

    public String attackMonster(List<String> ins) {
        try {
            if (ins.size() < 3) {
                return "\n攻击玩家指令错误";
            }
            int skillId = Integer.parseInt(ins.remove(0));
            int x = Integer.parseInt(ins.remove(0));
            int y = Integer.parseInt(ins.remove(0));
            return attackService.attack1(getChannelId(), x, y, skillId);
        } catch (NumberFormatException ignored) {
            return "\n格子数、技能id必须为数字";
        }
    }

    public String attackPlayer(List<String> ins) {
        try {
            if (ins.size() < 2) {
                return "\n攻击玩家指令错误";
            }
            int skillId = Integer.parseInt(ins.remove(0));
            String playerId = ins.remove(0);
            return attackService.attackPlayer(getChannelId(), playerId, skillId);
        } catch (NumberFormatException ignored) {
            return "\n技能id必须为数字";
        }
    }

    public String useTreatment(List<String> ins) {
        try {
            if (ins.size() < 2) {
                return "\n治疗指令错误";
            }
            int skillId = Integer.parseInt(ins.remove(0));
            String playerId = ins.remove(0);
            return attackService.useTreatmentSkill(getChannelId(), playerId, skillId);
        } catch (NumberFormatException ignored) {
            return "\n技能id必须为数字";
        }
    }
}
