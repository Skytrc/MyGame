package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/9 9:54
 */
@Component
public class BackpackHandler extends BaseInstructionHandler {

    @Autowired
    GoodService goodService;

    @Override
    public String handler(List<String> ins) {
        switch (ins.remove(0)) {
            case "use" : return use(ins);
            case "puton" : return putOn(ins);
            case "takeoff": return takeOff(ins);
            default: return "背包指令错误";
        }
    }

    private String takeOff(List<String> ins) {
        try {
            int position = Integer.parseInt(ins.remove(0));
            return goodService.takeOffEquipment(position, getChannelId());
        } catch (NumberFormatException ignored) {
            return "格子数必须为数字";
        }
    }

    private String putOn(List<String> ins) {
        try {
            int position = Integer.parseInt(ins.remove(0));
            return goodService.putOnEquipment(position, getChannelId());
        } catch (NumberFormatException ignored) {
            return "格子数必须为数字";
        }

    }

    private String use(List<String> ins) {
        return null;
    }
}
