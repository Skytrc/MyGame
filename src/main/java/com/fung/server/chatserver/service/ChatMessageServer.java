package com.fung.server.chatserver.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/30 15:17
 */
public interface ChatMessageServer {

    /**
     * 公频聊天
     * @param channelId 发言玩家channel id
     * @param content 发言内容
     */
    void publicChat(String channelId, String content);

    /**
     * 私聊
     * @param playerName 私聊玩家名字
     * @param channelId 发言玩家Channel id
     * @param content 发言内容
     */
    void privateChat(String playerName, String channelId, String content);

    /**
     * 根据提示代码显示提示信息
     * @param code 提示代码
     */
    void echoTip(int code);
}
