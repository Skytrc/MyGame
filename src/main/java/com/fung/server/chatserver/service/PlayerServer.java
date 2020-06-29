package com.fung.server.chatserver.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 15:10
 */
public interface PlayerServer {

    /**
     * 聊天服务器登录
     * @param playerName 玩家姓名
     * @param password 玩家密码
     * @param channelId channel id
     */
    void login(String playerName, String password, String channelId);

    /**
     * 玩家注册
     * @param playerName 玩家姓名
     * @param password 玩家密码
     * @param channelId channel id
     */
    void register(String playerName, String password, String channelId);

    /**
     * 登出
     * @param channelId channel id
     */
    void logout(String channelId);
}
