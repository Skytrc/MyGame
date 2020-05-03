package com.fung.server;

/**
 * @author skytrc@163.com
 * @date 2020/4/27 11:50
 */
public class GameServerStart {

//    private static final Logger LOGGER = LoggerFactory.getLogger(GameServerStart.class);

    private static GameServer game_server = new GameServer();

    public void start(int port) throws InterruptedException {
        game_server.start(port);
//        LOGGER.info("Game server start");
        System.out.println("Game server start");
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        GameServerStart gameServerStart = new GameServerStart();
        gameServerStart.start(port);
    }
}
