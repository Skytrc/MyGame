package com.fung.server.service.impl;

import com.fung.server.content.entity.Player;
import com.fung.server.service.PlayerService;

/**
 * @author skytrc@163.com
 * @date 2020/5/18 21:05
 */
public class PlayerServiceImpl implements PlayerService {

    @Override
    public Player getPlayerByUsername() {
        return null;
    }

    @Override
    public boolean register(String playerName, String password) {
        return false;
    }

    @Override
    public void login(String playerName, String password, String channelId) {

    }

    @Override
    public void logout() {

    }
}
