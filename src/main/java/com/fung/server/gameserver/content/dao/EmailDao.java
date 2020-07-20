package com.fung.server.gameserver.content.dao;

import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.EmailStatus;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/16 17:01
 */
public interface EmailDao {

    /**
     * 创建新的邮件
     * @param email 邮件
     */
    void createNewEmail(Email email);

    /**
     * 更新邮件数据
     * @param email 邮件
     */
    void updateEmail(Email email);

    /**
     * 根据id获得邮件
     * @param emailId email id
     * @return 邮件
     */
    Email getEmailById(String emailId);

    /**
     * 创建email状态
     */
    void createEmailStatus(EmailStatus emailStatus);

    /**
     * 更新email状态
     */
    void updateEmailStatus(EmailStatus emailStatus);

    /**
     * 删除email状态
     */
    void deleteEmailStatus(String emailId, String playerId);

    /**
     * 查找所有的邮件
     * @param playerId player id
     * @return 所有的邮件
     */
    List<Email> getAllEmailByPlayerId(String playerId);

    /**
     * 删除email
     * @param email email实体
     */
    void deleteEmailBy(Email email);
}
