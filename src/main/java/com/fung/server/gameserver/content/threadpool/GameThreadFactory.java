package com.fung.server.gameserver.content.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skytrc@163.com
 * @date 2020/7/6 20:34
 */
public class GameThreadFactory implements ThreadFactory {

    private final String namePrefix;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final ThreadGroup group;

    public GameThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
        SecurityManager s = System.getSecurityManager();
        // 取得线程组
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = namePrefix + "-thread" + threadNumber.getAndIncrement();
        return new Thread(group, r, threadName);
    }
}
