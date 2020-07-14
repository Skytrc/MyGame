package com.fung.server.gameserver.message;

/**
 * @author skytrc@163.com
 * @date 2020/7/7 10:13
 */
public interface IMessage<H extends IMessageHandler<?>> {

    /**
     * 执行事务
     * @param h 事务
     */
    void execute(H h) throws InterruptedException;

    /**
     * 返回 class 名称
     * @return class 名称
     */
    default String name() {return getClass().toString();}
}
