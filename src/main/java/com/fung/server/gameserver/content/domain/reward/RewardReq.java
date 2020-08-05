package com.fung.server.gameserver.content.domain.reward;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 16:31
 */
@Component
public class RewardReq {

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    public void moneyReward(String channelId, int value) {
        writeMessage2Client.writeMessage(channelId, "\n成功获得金钱奖励: " + value);
    }

    public void expReward(String channelId, int quantity) {
        writeMessage2Client.writeMessage(channelId, "\n成功获得经验奖励: " + quantity);
    }

    public void goodReward(String chanelId, String goodName, int quantity) {
        writeMessage2Client.writeMessage(chanelId, String.format("\n成功获得物品奖励 %s  数量 %s", goodName, quantity));
    }
}
