package com.fung.server.gameserver.message;

import com.fung.server.gameserver.content.threadpool.GameThreadFactory;
import com.fung.server.gameserver.content.threadpool.GameThreadPool;
import org.jctools.queues.MpscLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skytrc@163.com
 * @date 2020/7/6 19:18
 */
public class MessageHandler<H extends IMessageHandler<?>> implements Runnable, IMessageHandler<H> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    private static ThreadPoolExecutor DEFAULT_THREAD_POOL_EXECUTOR = new GameThreadPool()
            .getNewThreadPoolExecutor("MessageHandler-worker");

    private static ScheduledExecutorService DEFAULT_SCHEDULED_EXECUTOR =
            new ScheduledThreadPoolExecutor(2, new GameThreadFactory("MessageHandler-Scheduler"));

    private ThreadPoolExecutor threadPoolExecutor;

    private ScheduledExecutorService scheduledExecutor;

    private Queue<IMessage<H>> messages = MpscLinkedQueue.newMpscLinkedQueue();

    private AtomicInteger size = new AtomicInteger();

    private volatile Thread currentThread;

    public MessageHandler(ThreadPoolExecutor threadPoolExecutor, ScheduledExecutorService scheduledExecutor) {
        super();
        this.threadPoolExecutor = threadPoolExecutor;
        this.scheduledExecutor = scheduledExecutor;
    }

    public MessageHandler() {
        this(DEFAULT_THREAD_POOL_EXECUTOR, DEFAULT_SCHEDULED_EXECUTOR);
    }

    @Override
    public void run() {
        this.currentThread = Thread.currentThread();
        // 处理队列中的任务
        while (true) {
            IMessage<H> message =  messages.poll();
            if (message == null) {
                break;
            }
            try {
                // 处理事件
                long before = System.currentTimeMillis();
                message.execute((H)this);
            } catch (Throwable throwable) {
                LOGGER.error("", throwable);
            }
            int queueSize = size.decrementAndGet();
            if (queueSize <= 0) {
                break;
            }
        }
    }

    /**
     * 推送任务到线程队列中
     */
    @Override
    public void addMessage(IMessage<H> msg) {
        // 添加到队列当中
        messages.add(msg);
        int queueSize = size.incrementAndGet();
        // 当队列中只有当前加入的任务，立即执行
        if (queueSize == 1) {
            threadPoolExecutor.execute(this);
        }
    }

    /**
     * 定时推送任务， 单位毫秒
     */
    public void schedule(IMessage<H> msg, long delay) {
        scheduledExecutor.schedule(() -> {
            addMessage(msg);
        }, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时推送任务
     */
    public void schedule(IMessage<H> msg, long delay, TimeUnit unit) {
        scheduledExecutor.schedule(() -> {
            addMessage(msg);
        }, delay, unit);
    }

    public boolean isInThread() {
        return Thread.currentThread() == currentThread;
    }

}
