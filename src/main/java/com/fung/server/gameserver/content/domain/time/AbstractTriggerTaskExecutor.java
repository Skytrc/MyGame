package com.fung.server.gameserver.content.domain.time;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * TODO 暂时不使用TriggerTaskExecutor
 * @author skytrc@163.com
 * @date 2020/7/7 21:09
 */
public abstract class AbstractTriggerTaskExecutor implements ITriggerTaskExecutor{

    private Map<String, TriggerFuture> taskIdWithFutureMap = new ConcurrentHashMap<>();

    public abstract TriggerFuture addTask(TimeTriggerTask task);

    @Override
    public TriggerFuture execute(String taskName, Runnable command) {
        return null;
    }

    @Override
    public TriggerFuture schedule(String taskName, Runnable command, long delay, TimeUnit unit) {
        return null;
    }

    @Override
    public TriggerFuture schedule(String taskName, Runnable command, long executeTime) {
        return null;
    }

    @Override
    public TriggerFuture scheduleUniqueTask(String taskId, Runnable command, long executeTime) {
        return null;
    }
}
