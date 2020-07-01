package com.fung.server.chatserver;

import com.fung.server.chatserver.controller.Distribution;
import com.fung.server.chatserver.stored.ChatStoredChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author skytrc@163.com
 * @date 2020/6/19 10:42
 */
@Component
public class ChatServerStart {

    @Autowired
    private Distribution distribution;

    @Autowired
    private ChatStoredChannel chatStoredChannel;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServerStart.class);

    public void start(int port) throws InterruptedException {
        ChatServer chatServer = new ChatServer(distribution, chatStoredChannel);
        chatStoredChannel.storeChannelInit(new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>());
        chatServer.start(port);
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8090;
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Spring 初始化");
        ChatServerStart chatServerStart = context.getBean(ChatServerStart.class);
        chatServerStart.start(port);
    }
}
