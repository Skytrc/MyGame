package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/13 11:27
 */
@Component
public class AccountHandler extends BaseInstructionHandler {

    @Autowired
    private PlayerService playerService;

    @Override
    public String handler(List<String> ins) {
        switch (ins.remove(0)) {
            case "register" : return register(ins);
            case "login" : return login(ins);
            case  "logout" : return logout();
            default: return "账号操作指令错误";
        }
    }

    public String register(List<String> ins) {
        String playerName = ins.remove(0);
        String password = ins.remove(0);
        return playerService.register(playerName, password);
    }

    public String login(List<String> ins) {
        if (ins.size() < 2) {
            return "账号密码输入错误";
        }
        String playerName = ins.remove(0);
        String password = ins.remove(0);
        return playerService.login(playerName, password, getChannelId());
    }

    public String logout() {
        return playerService.logout(getChannelId());
    }

}
