package com.fung.server.gameserver.content.domain.reward;

import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * TODO 写得比较简陋粗暴, 没有考虑可拓展性
 * @author skytrc@163.com
 * @date 2020/8/5 15:51
 */
@Component
public class Reward {

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private RewardReq rewardReq;

    @Autowired
    private GoodDao goodDao;

    public void rewardPlayer(Player player, String channelId, int rewardType, int id, int quantity) {
        RewardType rewardType1 = RewardType.getRewardType(rewardType);
        switch (Objects.requireNonNull(rewardType1)) {
            case EXP:
                rewardExp(player, channelId, quantity);
                return;
            case GOOD:
                rewardGood(player, channelId, id, quantity);
                return;
            case MONEY:
                rewardMoney(player, channelId, quantity);
            default:
        }
    }

    public void rewardGood(Player player, String channelId, int id, int quantity) {
        // TODO 没有考虑到背包容量问题
        PersonalBackpack personalBackpack = player.getPersonalBackpack();
        Good newGood = goodManager.createNewGood(id, quantity, player.getUuid());
        personalBackpack.checkAndAddGood(newGood, goodDao);
        rewardReq.goodReward(channelId, goodManager.getGoodNameById(id), quantity);
    }

    public void rewardMoney(Player player, String channelId, int quantity) {
        player.getPlayerCommConfig().addMoney(quantity);
        rewardReq.moneyReward(channelId, quantity);
    }

    public void rewardExp(Player player, String channelId, int quantity) {
        player.addExp(quantity);
        rewardReq.expReward(channelId, quantity);
    }
}
