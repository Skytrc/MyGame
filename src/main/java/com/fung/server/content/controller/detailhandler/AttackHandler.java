package com.fung.server.content.controller.detailhandler;

import com.fung.server.content.service.AttackService;
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
    AttackService attackService;

    @Override
    public String handler(List<String> ins) {
        if (("monster").equals(ins.remove(0))) {
            return attackMonster(ins);
        }
        return null;
    }

    public String attackMonster(List<String> ins) {
        try {
            int skillId = Integer.parseInt(ins.remove(0));
            int x = Integer.parseInt(ins.remove(0));
            int y = Integer.parseInt(ins.remove(0));
            return attackService.attack(getChannelId(), x, y, skillId);
        } catch (NumberFormatException ignored) {
            return "格子数必须为数字";
        }
    }
}
