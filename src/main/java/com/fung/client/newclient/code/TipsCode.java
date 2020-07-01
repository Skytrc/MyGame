package com.fung.client.newclient.code;

/**
 * @author skytrc@163.com
 * @date 2020/6/29 10:41
 */
public interface TipsCode {

    /**
     * 登录成功
     */
    int LOGIN_SUCCESS = 101;

    /**
     * 玩家名字已存在
     */
    int PLAYER_NAME_EXISTS = 102;

    /**
     * 玩家名字不存在
     */
    int PLAYER_NAME_NOT_EXISTS = 103;

    /**
     * 玩家密码错误
     */
    int PLAYER_PASSWORD_WRONG = 104;

    /**
     * 玩家不存在或玩家未上线
     */
    int PLAYER_NOT_EXISTS_OR_NOT_ONLINE = 105;
}
