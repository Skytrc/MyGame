package com.fung.server.content.controller.detailhandler;

import com.fung.server.content.service.MoveService;
import com.fung.server.content.util.playerutil.OnlinePlayer;
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
    OnlinePlayer onlinePlayer;

    @Autowired
    MoveService moveService;

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

    public String up(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            return moveService.move(-distance, 0, getChannelId());
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String down(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            return moveService.move(distance, 0, getChannelId());
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String left(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            return moveService.move(0, -distance, getChannelId());
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String right(List<String> ins) {
        try {
            int distance = Integer.parseInt(ins.remove(0));
            return moveService.move(0, distance, getChannelId());
        } catch (NumberFormatException ignored) {
            return "移动步数必须为数字";
        }
    }

    public String map() {
        return moveService.mapTransfer(getChannelId());
    }

}
