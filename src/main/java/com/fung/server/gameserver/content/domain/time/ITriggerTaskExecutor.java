package com.fung.server.gameserver.content.domain.time;

import java.util.concurrent.TimeUnit;

/**
 * TODO 暂时不使用TriggerTaskExecutor
 * 定时调度线程池
 * @author skytrc@163.com
 * @date 2020/7/7 21:00
 */
public interface ITriggerTaskExecutor {

    TriggerFuture execute(String taskName, Runnable command);

    TriggerFuture schedule(String taskName, Runnable command, long delay, TimeUnit unit);

    TriggerFuture schedule(String taskName, Runnable command, long executeTime);

    TriggerFuture scheduleUniqueTask(String taskId, Runnable command, long executeTime);
}
