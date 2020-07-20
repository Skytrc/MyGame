package com.fung.server.gameserver.content.domain.email;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/19 14:29
 */
@Component
public class EmailView {

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    public void emailBaseInfo(Email email) {

    }

    public void emailContent(Email email) {

    }

    public void emailGoodInfo(Email email) {

    }
}
