package com.fung.server.gameserver.content.domain.calculate;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.map.GameMap;
import com.fung.server.gameserver.content.config.monster.BaseHostileMonster;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 10:36
 */
@Component
public class MoveCalculate {

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Autowired
    private PlayerInfo playerInfo;

    public static final int MOVE_CD = 1000;

    /**
     * 第一种情况，移动固定距离
     */
    public void moveLimited(Player player, String channelId, int x, int y) throws InterruptedException {
        // 判断是哪个坐标移动了
        boolean isX = y == 0;

        int speed = player.getMoveSpeed();
        int second = Math.abs((isX ? x : y)) / speed;
        if (second % speed != 0) {
            second++;
        }
        int oldAxis = isX ? player.getInMapX() : player.getInMapY();

        for (int i = second; i > 1; i--) {
            // 隔一秒走一次
            Thread.sleep(1000);
            if (isX) {
                if (x == Math.abs(x)) {
                    player.setInMapX(player.getInMapX() + speed);
                } else {
                    player.setInMapX(player.getInMapX() - speed);
                }
            } else {
                if (y == Math.abs(y)) {
                    player.setInMapY(player.getInMapY() + speed);
                } else {
                    player.setInMapY(player.getInMapY() - speed);
                }
            }
            String message = "\n玩家移动到坐标: " + " [ " + player.getInMapX() + " , " + player.getInMapY() + " ]";
            // 把移动信息发送给玩家
            writeMessage2Client.writeMessage(channelId, message);
        }
        // 最后一秒需要额外处理
        Thread.sleep(1000);
        if (isX) {
            player.setInMapX(oldAxis + x);
        } else {
            player.setInMapY(oldAxis + y);
        }
        writeMessage2Client.writeMessage(channelId, "\n玩家移动到坐标: [" + player.getInMapX() + " , " + player.getInMapY() + "] ");
    }

    public void moveGrid(GameMapActor gameMapActor, Player player, String channelId, int x1, int y1) throws InterruptedException {

        int x0 = player.getInMapX();
        int y0 = player.getInMapY();
        float length = Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0));

        float dx = (x1 - x0) / length;
        float dy = (y1 - y0) / length;
        gameMapActor.addMessage(gameMapActor1 -> {
            moveGrid0(gameMapActor, player, channelId, x0, y0, dx, dy, 0, length);
        });
    }

    public void moveGrid0(GameMapActor gameMapActor, Player player, String channelId,
                          float x1, float y1, float dx, float dy, int playerHasMoveDistance, float distance) {
        if (playerHasMoveDistance <= distance) {
            int oldX = player.getInMapX();
            int oldY = player.getInMapY();
            player.setInMapX((int) (x1 + 0.5));
            player.setInMapY((int) (y1 + 0.5));
            GameMap gameMap = gameMapActor.getGameMap();
            gameMap.playerMove(player, oldX, oldY);
            writeMessage2Client.writeMessage(channelId, "\n玩家移动到坐标: [" + player.getInMapX() + " , " + player.getInMapY() + "] ");
            gameMapActor.schedule(gameMapActor1 -> {
                moveGrid0(gameMapActor, player, channelId, x1 + dx, y1 + dy, dx, dy, playerHasMoveDistance + 1, distance);
            }, MOVE_CD);
        } else {
            writeMessage2Client.writeMessage(channelId, "移动完成");
        }
    }
}
