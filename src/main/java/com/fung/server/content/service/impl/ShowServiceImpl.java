package com.fung.server.content.service.impl;

import com.fung.server.content.service.ShowService;
import com.fung.server.content.domain.map.MapInfo;
import com.fung.server.content.domain.player.PlayeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 12:52
 */
@Component
public class ShowServiceImpl implements ShowService {

    @Autowired
    PlayeInfo playeInfo;

    @Autowired
    MapInfo mapInfo;

    @Override
    public String showPlayer(String channelId) {
        return playeInfo.showPlayerInfo(playeInfo.getCurrentPlayer(channelId));
    }

    @Override
    public String showMapOnlinePlayer(String channelId) {
        return mapInfo.showMapOnlinePlayer(playeInfo.getCurrentPlayerMap(channelId));
    }

    @Override
    public String showMapElement(String channelId) {
        return mapInfo.showMapInfo(playeInfo.getCurrentPlayerMap(channelId));
    }
}
