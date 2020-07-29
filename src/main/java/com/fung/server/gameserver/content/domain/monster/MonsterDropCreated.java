package com.fung.server.gameserver.content.domain.monster;

import com.fung.server.gameserver.content.config.good.FallingGood;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.monster.BaseMonster;
import com.fung.server.gameserver.content.config.monster.MonsterDrop;
import com.fung.server.gameserver.content.domain.good.NewPlayerGoodCreatedFactory;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 用於生成怪物掉落物
 * @author skytrc@163.com
 * @date 2020/7/9 17:29
 */
@Component
public class MonsterDropCreated {

    private final Random RANDOM = new Random();

    @Autowired
    private GoodManager goodManager;

    private Map<Integer, List<MonsterDrop>> monsterDropMap;

    public void monsterDropCreatedInit(Map<Integer, List<MonsterDrop>> monsterDropMap) {
        this.monsterDropMap = monsterDropMap;
    }

    /**
     * 返回怪物掉落物List<Good> 随机到没有物品掉落，
     */
    public List<FallingGood> goodsCreated(BaseMonster monster, Player player) {
        List<MonsterDrop> monsterDrops = monsterDropMap.get(monster.getId());
        List<FallingGood> returnGoods = new ArrayList<>();
        for (MonsterDrop monsterDrop : monsterDrops) {
            Good good = goodCreated(monsterDrop.getGoodId(), monsterDrop.getDropProbability(), monsterDrop.getMaxQuantity());
            if (good != null) {
//                FallingGood fallingGood = new FallingGood();
//                fallingGood.setName(good.getName());
//                fallingGood.setGettingTime(gettingTime);
//                fallingGood.setBeingToPlayer(player);
//                fallingGood.setInMapId(monster.getInMapId());
//                fallingGood.setInMapX(monster.getInMapX());
//                fallingGood.setInMapY(monster.getInMapY());
//                fallingGood.setGood(good);
//                fallingGood.setFriendly(true);
                FallingGood fallingGood = new FallingGood.Builder(good, monster.getTempX(), monster.getTempY()
                        , monster.getInMapId()).beingToPlayer(player).build();
                returnGoods.add(fallingGood);
            }
        }
        if (!returnGoods.isEmpty()) {
            return returnGoods;
        }
        return null;
    }

    public Good goodCreated(int goodId, float probability, int maxCreatedQuantity) {
        int rate = (int) (probability * 10_000);
        int randomNumber = RANDOM.nextInt(10_000);
        if (rate > randomNumber){
            int quantity = RANDOM.nextInt(maxCreatedQuantity);
            if (quantity == 0) {
                return null;
            }
            return goodManager.createNoBeingGood(goodId, quantity);
        }
        return null;
    }
}
