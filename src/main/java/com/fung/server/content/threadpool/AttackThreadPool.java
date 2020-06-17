package com.fung.server.content.threadpool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author skytrc@163.com
 * @date 2020/6/16 14:59
 */
@Component
public class AttackThreadPool {

    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * TODO 以后线程需要优化，现在先随手写一写试一下
     */
    public AttackThreadPool() {
        threadPoolExecutor = new ThreadPoolExecutor(20, 30, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), new AttackThreadFactory());
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
