package com.fung.server.gameserver;

import com.fung.server.gameserver.content.config.manager.*;
import com.fung.server.gameserver.content.controller.Controller;
import com.fung.server.gameserver.content.domain.player.PlayerInit;
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
    private MonsterCreateManager monsterCreateManager;

    @Autowired
    private SkillManager skillManager;

    @Autowired
    private NpcManager npcManager;

    @Autowired
    private PlayerInit playerInit;

    @Autowired
    private Controller controller;

    public void start(int port) throws InterruptedException, IOException, InvalidFormatException {
        mapManager.mapInit();
        goodManager.goodInit();
        skillManager.skillInit();
        monsterCreateManager.monsterCreateInit();
        npcManager.npcModelInit();

        playerInit.init();
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
