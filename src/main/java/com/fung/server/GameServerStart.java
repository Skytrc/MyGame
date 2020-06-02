package com.fung.server;

import com.fung.server.content.config.manager.GoodManager;
import com.fung.server.content.config.manager.MapManager;
import com.fung.server.content.controller.Controller;
import com.fung.server.content.util.UtilManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    private GoodManager goodManager;

    @Autowired
    private UtilManager utilManager;

    @Autowired
    private Controller controller;

    public void start(int port) throws InterruptedException, IOException, InvalidFormatException {
        mapManager.mapInit();
        goodManager.goodInit();
        utilManager.init();
        controller.init();
        controller.severStart(port);
    }

    public static void main(String[] args) throws InterruptedException, IOException, InvalidFormatException {
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
