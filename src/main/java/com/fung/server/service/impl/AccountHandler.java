package com.fung.server.service.impl;

import com.fung.server.content.MapManager;
import com.fung.server.content.entity.Player;
import com.fung.server.dao.UserDao;
import com.fung.server.service.BaseInstructionHandler;
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
    private UserDao userDao;

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
        if (userDao.getPlayerByPlayerNamePassword(playerName, password) != null) {
            return "角色已存在";
        } else {
            Player player = new Player();
            player.setPlayerName(playerName);
            player.setPassword(password);
            player.setHitPoint(100);
            player.setMaxHitPoint(100);
            player.setInMapX(0);
            player.setInMapY(0);
            player.setCreatedDate(new Date());
            userDao.playerRegister(player);
            return "角色创建完毕";
        }
    }

    public String login(List<String> ins) {
        if (ins.size() < 2) {
            return "账号密码输入错误";
        }
        String playerName = ins.remove(0);
        String password = ins.remove(0);
        if (userDao.getPlayerByPlayerNamePassword(playerName, password) == null) {
            return "角色名或密码错误";
        }
        Player player = userDao.getPlayerByPlayerNamePassword(playerName, password);
        // channel 绑定 玩家
        onlinePlayer.getPlayerMap().put(getChannelId(), player);
        // 地图添加上线玩家
        mapManager.getMapByMapId(player.getInMapId()).addPlayer(player);
        return "登录成功\n" + playerUtil.showPlayerInfo(player);
    }

    public String logout() {
        if (onlinePlayer.getPlayerMap().remove(getChannelId()) != null) {
            return "登出成功";
        }
        return "没有角色登录";
    }

}
