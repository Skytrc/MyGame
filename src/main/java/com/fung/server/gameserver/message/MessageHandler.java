package com.fung.server.gameserver.message;

import com.fung.server.gameserver.content.threadpool.GameThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skytrc@163.com
 * @date 2020/7/6 19:18
 */
public class MessageHandler<H extends IMessageHandler<?>> implements Runnable, IMessageHandler<H> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    private static ThreadPoolExecutor DEFAULT_THREAD_POOL_EXECUTOR = new GameThreadPool().getNewThreadPoolExecutor("MessageHandler-worker");

    private AtomicInteger size = new AtomicInteger();

    private ThreadPoolExecutor threadPoolExecutor;

    private volatile Thread currentThread;

    @Override
    public void addMessage(IMessageHandler<H> msg) {

    }

    @Override
    public void run() {

    }
}
