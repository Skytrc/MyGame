package com.fung.server.gameserver.content.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author skytrc@163.com
 * @date 2020/7/6 19:52
 */

public class GameThreadPool {

    public ThreadPoolExecutor getNewThreadPoolExecutor(String groupName) {
        int availProcessors = Runtime.getRuntime().availableProcessors();
        // 如果是CPU密集型应用，则线程池大小设置为N+1
        return new ThreadPoolExecutor(availProcessors + 1, availProcessors + 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new GameThreadFactory(groupName));
    }
}
