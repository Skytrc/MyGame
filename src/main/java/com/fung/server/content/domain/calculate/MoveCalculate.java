package com.fung.server.content.domain.calculate;

import com.fung.server.channelstore.AsynWriteMessage2Client;
import com.fung.server.channelstore.StoredChannel;
import com.fung.server.content.entity.Player;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/10 10:36
 */
@Component
public class MoveCalculate {

    @Autowired
    StoredChannel storedChannel;

    @Autowired
    AsynWriteMessage2Client writeMessage2Client;

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
        writeMessage2Client.writeMessage(channelId, "\n最后玩家移动到坐标: [" + player.getInMapX() + " , " + player.getInMapY() + "] ");
    }

    public void moveGrid(Player player, int x, int y) {

    }
}
