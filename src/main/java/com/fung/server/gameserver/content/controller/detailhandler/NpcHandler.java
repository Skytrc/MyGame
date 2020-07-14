package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.DungeonService;
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

    @Autowired
    private DungeonService dungeonService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String s = ins.remove(0);
        switch (s) {
            case("talk"):
                return talkNpc();
            case("choose"):
                int choose;
                try {
                    choose = Integer.parseInt(ins.remove(0));
                } catch (NumberFormatException ignored) {
                    return "选项必须为数字";
                }
                return getMessageByChoose(choose);
            case("shop"):
                return openShop();
            case("createdungeon"):
                return openDungeon();
            case("joindungeon"):
                return ins.remove(0);
            default: return "NPC命令错误";
        }
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

    public String joinDungeon(String dungeonUuid) {
        return dungeonService.enterDungeon(getChannelId(), dungeonUuid);
    }
}
