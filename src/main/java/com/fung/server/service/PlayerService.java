package com.fung.server.service;

import com.fung.server.content.entity.Player;

/**
 * @author skytrc@163.com
 * @date 2020/5/18 21:04
 */
public interface PlayerService {

    /**
     * 玩家注册
     * @param playerName 玩家名
     * @param password 密码
     * @return 是否注册成功
     */
    String register(String playerName, String password);

    /**
     * 登录
     * @param playerName 玩家名
     * @param password 玩家密码
     * @param channelId 玩家channelId
     * @return 登录状态
     */
    String login(String playerName, String password, String channelId);

    /**
     * 登出
     * @param channelId channelId
     * @return 登出状态
     */
    String logout(String channelId);
}
