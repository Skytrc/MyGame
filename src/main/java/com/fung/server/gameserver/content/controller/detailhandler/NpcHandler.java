package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.NpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 16:23
 */
@Component
public class NpcHandler extends BaseInstructionHandler{

    @Autowired
    private NpcService npcService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String s = ins.remove(0);
        if ("talk".equals(s)) {
            return talkNpc();
        } else if("choose".equals(s)) {
            int choose;
            try {
                choose = Integer.parseInt(ins.remove(0));
            } catch (NumberFormatException ignored) {
                return "选项必须为数字";
            }
            return getMessageByChoose(choose);
        } else if("shop".equals(s)) {
            return openShop();
        }
        return "NPC命令错误";
    }

    public String talkNpc() {
        return npcService.allChoose(getChannelId());
    }

    public String getMessageByChoose(int choose) {
        return npcService.oneChoose(getChannelId(), choose);
    }

    public String openShop() {
        return npcService.openShop(getChannelId());
    }

    public String openDungeon() {
        return npcService.openDungeon(getChannelId());
    }
}
