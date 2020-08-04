package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.PlayerOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/30 9:52
 */
@Component
public class PlayerOtherHandler extends BaseInstructionHandler{

    @Autowired
    private PlayerOtherService playerOtherService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String remove = ins.remove(0);
        if ("rebirth".equals(remove)) {
            return rebirth();
        }
        return "\n玩家指令错误";
    }

    public String rebirth() {
        return playerOtherService.rebirth(getChannelId());
    }
}
