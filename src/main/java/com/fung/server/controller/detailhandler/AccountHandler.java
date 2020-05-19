package com.fung.server.controller.detailhandler;

import com.fung.server.content.MapManager;
import com.fung.server.content.entity.Player;
import com.fung.server.dao.PlayerDao;
import com.fung.server.controller.detailhandler.BaseInstructionHandler;
import com.fung.server.service.PlayerService;
import com.fung.server.util.playerutil.OnlinePlayer;
import com.fung.server.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/13 11:27
 */
@Component
public class AccountHandler extends BaseInstructionHandler {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private PlayerUtil playerUtil;

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
