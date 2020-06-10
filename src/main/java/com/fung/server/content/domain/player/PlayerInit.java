package com.fung.server.content.domain.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 17:47
 */
@Component
public class PlayerInit {

    @Autowired
    private OnlinePlayer onlinePlayer;

    public void init() {
        onlinePlayer.init();
    }

}
