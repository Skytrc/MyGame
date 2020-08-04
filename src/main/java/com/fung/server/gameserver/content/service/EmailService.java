package com.fung.server.gameserver.content.service;

import com.fung.server.gameserver.content.entity.Good;

/**
 * @author skytrc@163.com
 * @date 2020/7/16 14:47
 */
public interface EmailService {

    String systemWriteEmail(String recipientId, String content, String title);

    String systemWriteEmailWithGood(String recipientId, String content, String title, Good good);

    String createEmail(String channelId, String recipientId);

    String writeContent(String channelId, String emailId, String subject, String content);

    String addGood(String channelId, String emailId, int backpackPosition, int goodQuantity);

    String send(String channelId, String emailId);

    /**
     * 从数据库中更新个人邮箱
     * @param channelId channel id
     * @return 更新邮箱
     */
    String checkMailBox(String channelId);

    String checkAEmail(String channelId, String emailId);

    String checkDraftBox(String channelId);

    String gotGoodFromEmail(String channelId, String emailId);

    String deleteResEmail(String channelId, String emailId);
}
