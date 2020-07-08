package com.fung.server.gameserver.content.domain.time;

/**
 * TODO 暂时不使用TriggerTaskExecutor
 * @author skytrc@163.com
 * @date 2020/7/7 21:16
 */
public interface TimeTriggerTask {

    boolean canTrigger();

    void trigger(long time);

    long getTriggerTime();
}
