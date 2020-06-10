package com.fung.server.content.controller.detailhandler;

import com.fung.server.content.service.ShowService;
import com.fung.server.content.domain.player.OnlinePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 18:19
 */
@Component
public class ShowInfoHandler extends BaseInstructionHandler {

    @Autowired
    OnlinePlayer onlinePlayer;

    @Autowired
    ShowService showService;

    @Override
    public String handler(List<String> ins) {
        if (onlinePlayer.getPlayerMap().get(getChannelId()) == null) {
            return "用户尚未登录";
        }
        String i = ins.remove(0);
        switch (i) {
            case "player" : return showPlayer();
            case "map" : return showMapElement();
            case "online" : return onlinePlayer();
            case "backpack" : return showBackpack();
            case "skill" : return showSkill();
            case "body" : return showBodyEquipment();
            case "monster" : return showMonster(ins);
            default: return "展示指令错误";
        }
    }

    /**
     * 展示玩家信息
     * @return 玩家信息（String）
     */
    public String showPlayer() {
        return showService.showPlayer(getChannelId());
    }

    /**
     * 展现地图在线玩家信息
     * @return 地图在线玩家信息（String）
     */
    public String onlinePlayer() {
        return showService.showMapOnlinePlayer(getChannelId());
    }

    /**
     * 展示地图上的元素信息
     * @return 地图元素（String）
     */
    public String showMapElement() {
        return showService.showMapElement(getChannelId());
    }

    /**
     * 所有的背包物品
     * @return 背包物品
     */
    public String showBackpack() {
        return showService.showBackpack(getChannelId());
    }

    public String showSkill() {
        return showService.showSkill(getChannelId());
    }

    public String showBodyEquipment() {
        return showService.showBodyEquipment(getChannelId());
    }

    public String showMonster(List<String> ins) {
        try {
            int x = Integer.parseInt(ins.remove(0));
            int y = Integer.parseInt(ins.remove(0));
            return showService.showMonster(getChannelId(), x, y);
        } catch (NumberFormatException ignored) {
            return "格子数必须为数字";
        }
    }

}
