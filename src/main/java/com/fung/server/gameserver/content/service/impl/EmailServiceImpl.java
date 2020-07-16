package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.service.EmailService;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/16 14:47
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public String createEmail(String channelId, String recipientId) {
        return null;
    }

    @Override
    public String writeContent(String channelId, String emailId, String content) {
        return null;
    }

    @Override
    public String addGood(String channelId, String emailId, int backpackPosition) {
        return null;
    }

    @Override
    public String send(String channelId, String emailId) {
        return null;
    }

    @Override
    public String checkEmail(String channelId) {
        return null;
    }

    @Override
    public String gotGoodFromEmail(String channelId, String emailId) {
        return null;
    }

    @Override
    public String deleteEmail(String channelId, String email) {
        return null;
    }
}
