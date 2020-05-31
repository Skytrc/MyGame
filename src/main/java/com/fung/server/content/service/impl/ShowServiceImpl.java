package com.fung.server.content.service.impl;

import com.fung.server.content.service.ShowService;
import com.fung.server.content.util.maputil.MapInfoUtil;
import com.fung.server.content.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 12:52
 */
@Component
public class ShowServiceImpl implements ShowService {

    @Autowired
    PlayerUtil playerUtil;

    @Autowired
    MapInfoUtil mapInfoUtil;

    @Override
    public String showPlayer(String channelId) {
        return playerUtil.showPlayerInfo(playerUtil.getCurrentPlayer(channelId));
    }

    @Override
    public String showMapOnlinePlayer(String channelId) {
        return mapInfoUtil.showMapOnlinePlayer(playerUtil.getCurrentPlayerMap(channelId));
    }

    @Override
    public String showMapElement(String channelId) {
        return mapInfoUtil.showMapInfo(playerUtil.getCurrentPlayerMap(channelId));
    }
}
