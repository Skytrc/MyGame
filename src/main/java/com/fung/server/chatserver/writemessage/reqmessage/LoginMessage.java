package com.fung.server.chatserver.writemessage.reqmessage;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 15:25
 */
public interface LoginMessage {

    /**
     * 登录
     */

    String PASSWORD_WRONG = "密码错误";

    String PLAYER_NAME_NOT_EXITS = "玩家名不存在";

    /**
     * 注册
     */

    String PLAYER_NAME_EXITS = "玩家名已存在";
}
