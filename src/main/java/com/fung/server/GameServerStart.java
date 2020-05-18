package com.fung.server;

import com.fung.server.content.MapManager;
import com.fung.server.controller.Controller;
import com.fung.server.util.UtilManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/4/27 11:50
 */
@Component
public class GameServerStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServerStart.class);

    @Autowired
    private MapManager mapManager;

    @Autowired
    private UtilManager utilManager;

    @Autowired
    private Controller controller;

    public void start(int port) throws InterruptedException {
        mapManager.mapInit();
//        mapManagement.saveGameMap();
        utilManager.init();
        controller.init();
        controller.severStart(port);
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Spring 初始化");
        GameServerStart gameServerStart = context.getBean(GameServerStart.class);
        gameServerStart.start(port);
    }
}
