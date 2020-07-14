package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/14 10:51
 */
@Component
public class DungeonHandler extends BaseInstructionHandler{

    @Autowired
    private DungeonService dungeonService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String remove = ins.remove(0);
        if ("exit".equals(remove)) {
            return exitDungeon();
        }
        return "副本指令错误";
    }

    public String exitDungeon() {
        return dungeonService.leaveDungeon(getChannelId());
    }
}
