package com.fung.server.service.impl;

import com.fung.server.content.entity.GameMap;
import com.fung.server.content.entity.Player;
import com.fung.server.dao.LocationDao;
import com.fung.server.service.BaseInstructionHandler;
import com.fung.server.util.playerutil.OnlinePlayer;
import com.fung.server.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/5 16:18
 */
@Component
public class MoveHandler extends BaseInstructionHandler {

    @Autowired
    PlayerUtil playerUtil;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OnlinePlayer onlinePlayer;

    private Player player;

    private GameMap currentGameMap;

    @Override
    public String handler(List<String> ins) {
        // 检测登录
        if (onlinePlayer.getPlayerMap().get(getChannelId()) == null) {
            return "用户尚未登录";
        }
        switch (ins.remove(0)) {
            case "u":
                return up(ins);
            case "d":
                return down(ins);
            case "l":
                return left(ins);
            case "r":
                return right(ins);
            case "map":
                return map();
            default:
                return "移动指令错误";
        }
    }

    // TODO  up down left right 合并？

    public String up(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            player = playerUtil.getCurrentPlayer(getChannelId());
            if (player.getInMapX() - distance < 1) {
                return "移动步数超过地图限制";
            }
            player.setInMapX(player.getInMapX() - distance);
            locationDao.updatePlayerLocation(player);
            return "向上移动了：" + distance + " 当前坐标 [" + player.getInMapX() + "," + player.getInMapY() + "]";
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String down(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            player = playerUtil.getCurrentPlayer(getChannelId());
            currentGameMap = playerUtil.getCurrentPlayerMap(getChannelId());
            if (player.getInMapX() + distance > currentGameMap.getX()) {
                return "移动步数超过地图限制";
            }
            player.setInMapX(player.getInMapX() + distance);
            locationDao.updatePlayerLocation(player);
            return "向下移动了：" + distance + " 当前坐标 [" + player.getInMapX() + "," + player.getInMapY() + "]";
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String left(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            player = playerUtil.getCurrentPlayer(getChannelId());
            if (player.getInMapY() - distance < 1) {
                return "移动步数超过地图限制";
            }
            player.setInMapY(player.getInMapY() - distance);
            locationDao.updatePlayerLocation(player);
            return "向左移动了：" + distance + " 当前坐标 [" + player.getInMapX() + "," + player.getInMapY() + "]";
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String right(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            player = playerUtil.getCurrentPlayer(getChannelId());
            currentGameMap = playerUtil.getCurrentPlayerMap(getChannelId());
            if (player.getInMapY() + distance > currentGameMap.getY()) {
                return "移动步数超过地图限制";
            }
            player.setInMapY(player.getInMapY() + distance);
            locationDao.updatePlayerLocation(player);
            return "向右移动了：" + distance + " 当前坐标 [" + player.getInMapX() + "," + player.getInMapY() + "]";
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String map() {
        currentGameMap = playerUtil.getCurrentPlayerMap(getChannelId());
        player = playerUtil.getCurrentPlayer(getChannelId());
        int nextMapId = currentGameMap.hasGate(player.getInMapX(), player.getInMapY());
        if (nextMapId == -1) {
            return "当前坐标没有传送门";
        }
        player.setInMapId(nextMapId);
        // 坐标问题可以根据门的位置改变，待优化
        player.setInMapX(1);
        player.setInMapY(1);
        locationDao.updatePlayerLocation(player);
        GameMap nextGameMap = playerUtil.getGameMapById(nextMapId);
        currentGameMap.removePlayer(player.getId());
        nextGameMap.addPlayer(player);
        return "转移成功\n" + playerUtil.showPlayerInfo(player);
    }

}
