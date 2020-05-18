package com.fung.server.util;

import com.fung.server.util.playerutil.OnlinePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 17:47
 */
@Component
public class UtilManager {

    @Autowired
    private OnlinePlayer onlinePlayer;

    public void init() {
        onlinePlayer.init();
    }

}
