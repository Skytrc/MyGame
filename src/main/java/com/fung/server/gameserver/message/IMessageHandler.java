package com.fung.server.gameserver.message;

/**
 * @author skytrc@163.com
 * @date 2020/7/6 18:36
 */
public interface IMessageHandler<H extends IMessageHandler<?>>{

    /**
     * 添加消息
     * @param msg 消息
     */
    void addMessage(IMessageHandler<H> msg);
}
