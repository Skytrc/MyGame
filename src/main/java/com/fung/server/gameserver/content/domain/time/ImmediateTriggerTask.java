package com.fung.server.gameserver.content.domain.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 暂时不使用TriggerTaskExecutor
 * 触发器，即时执行
 * @author skytrc@163.com
 * @date 2020/7/7 21:19
 */
public abstract class ImmediateTriggerTask implements TimeTriggerTask {

    private String taskName;
    private long triggerTime = System.currentTimeMillis();
    private boolean trigger;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImmediateTriggerTask.class);

    public ImmediateTriggerTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public boolean canTrigger() {
        return !trigger;
    }

    protected abstract void handle(long time);

    @Override
    public void trigger(long time) {
        try {
            handle(time);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        this.trigger = true;
    }

    @Override
    public long getTriggerTime() {
        return triggerTime;
    }

    @Override
    public String toString() {
        return taskName;
    }
}
