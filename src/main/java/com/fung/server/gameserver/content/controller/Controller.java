package com.fung.server.gameserver.content.controller;

import com.fung.protobuf.protoclass.InstructionPack;
import com.fung.server.gameserver.channelstore.StoredChannel;
import com.fung.server.gameserver.content.controller.detailhandler.*;
import com.fung.server.gameserver.init.GameServer;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author skytrc@163.com
 * @date 2020/5/5 15:11
 */
@Component
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private Map<String, BaseInstructionHandler> map;

    @Autowired
    private MoveHandler moveHandler;

    @Autowired
    private AccountHandler accountHandler;

    @Autowired
    private ShowInfoHandler showInfoHandler;

    @Autowired
    private AttackHandler attackHandler;

    @Autowired
    private InstructionPack instructionPack;

    @Autowired
    private BackpackHandler backpackHandler;

    @Autowired
    private NpcHandler npcHandler;

    @Autowired
    private TeamHandler teamHandler;

    @Autowired
    private DungeonHandler dungeonHandler;

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    private ShopHandler shopHandler;

    @Autowired
    private Controller controller;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private StoredChannel storedChannel;

    public void init() {
        map = new HashMap<>();
        map.put("move", moveHandler);
        map.put("account", accountHandler);
        map.put("show", showInfoHandler);
        map.put("backpack", backpackHandler);
        map.put("attack", attackHandler);
        map.put("npc", npcHandler);
        map.put("team", teamHandler);
        map.put("dungeon", dungeonHandler);
        map.put("email", emailHandler);
        map.put("shop", shopHandler);
    }

    public String handleMessage(String msg, String channelId) throws InterruptedException {
        List<String> messages = new LinkedList<>();
        msg = msg.replaceAll("\"|instruction:|\\n", "").trim();
        // 将string类型指令转换为list<String> 方便处理
        Collections.addAll(messages, msg.toLowerCase().split(" "));
        String ins = messages.remove(0);
        // 从map 中提出对应的指令处理方式
        BaseInstructionHandler instructionHandler = map.get(ins);
        instructionHandler.setChannelId(channelId);
        if (instructionHandler == null) {
            return "指令错误";
        }
        return instructionHandler.handler(messages);
    }

    public void severStart(int port) throws InterruptedException {
        GameServer gameServer = new GameServer(controller, instructionPack, onlinePlayer, storedChannel);
        gameServer.start(port);
    }

}