package com.fung.server.content.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skytrc@163.com
 * @date 2020/6/16 15:30
 */
@Component
public class AttackThreadFactory implements ThreadFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttackThreadFactory.class);

    private final String namePrefix;

    private final AtomicInteger nextId = new AtomicInteger(1);

    public AttackThreadFactory() {
        namePrefix = "From AttackThreadFactory's " + "worker-";
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = namePrefix + nextId.getAndIncrement();
        return new Thread(null, r, name, 0);
    }
}
