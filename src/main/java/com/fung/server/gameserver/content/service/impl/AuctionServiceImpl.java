package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.dao.AuctionDao;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.domain.auction.AuctionManager;
import com.fung.server.gameserver.content.domain.auction.AuctionRep;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.entity.AuctionItemRecord;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.AuctionService;
import com.fung.server.gameserver.content.service.EmailService;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 21:12
 */
@Component
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuctionManager auctionManager;

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private AuctionRep auctionRep;

    @Override
    public void addAuctionItem(String channelId, int backpackPosition, int quantity, int startingPrice) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            Good good = personalBackpack.useGood(backpackPosition, quantity);
            if (good == null) {
                auctionRep.personalBackpackFull(channelId);
                return;
            }
            AuctionItemRecord auctionItemRecord = createNewAuction(good.getGoodId(), player.getUuid(), startingPrice, quantity);
            // 如果是装备，竞拍记录记录UUID，背包位置设置为in auction
            if (good.isEquipment()) {
                good.setPosition(PersonalBackpack.IN_AUCTION);
                auctionItemRecord.setGoodUuid(good.getUuid());
                goodDao.insertOrUpdateGood(good);
            }
            // 添加到拍卖行中
            auctionManager.addAuctionItem(auctionItemRecord);
            auctionRep.successAddAuctionItem(channelId);
            // 拍卖行线程启动，用于清理时间到期的拍卖物品
            auctionManager.getAuctionActor().schedule(auctionActor -> {
                finishAuctionItemRecord(auctionItemRecord.getUuid());
            }, AuctionManager.AUCTION_FINISH_TIME);
        });
    }

    @Override
    public void addBuyItNowItem(String channelId, int backpackPosition, int quantity, int price) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            PersonalBackpack personalBackpack = player.getPersonalBackpack();
            Good good = personalBackpack.useGood(backpackPosition, quantity);
            if (good == null) {
                auctionRep.personalBackpackFull(channelId);
                return;
            }
            AuctionItemRecord auctionItemRecord = createNewAuction(good.getGoodId(), player.getUuid(), price, quantity);
            auctionItemRecord.setBuyItNowItem(true);
            // 如果是装备，竞拍记录记录UUID，背包位置设置为in auction
            if (good.isEquipment()) {
                good.setPosition(PersonalBackpack.IN_AUCTION);
                auctionItemRecord.setGoodUuid(good.getUuid());
                goodDao.insertOrUpdateGood(good);
            }
            auctionManager.addBuyItNowItem(auctionItemRecord);
            auctionRep.successAddAuctionItem(channelId);
            auctionManager.getAuctionActor().schedule(auctionActor -> {
                finishBuyItNowItemRecord(auctionItemRecord.getUuid());
            }, AuctionManager.AUCTION_FINISH_TIME);
        });
    }

    @Override
    public void auctionItem(String channelId, String auctionItemRecordId, int price) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            AuctionItemRecord auctionItemRecord = auctionManager.getAuctionItem(auctionItemRecordId);
            if (auctionItemRecord == null) {
                auctionRep.auctionItemNotExists(channelId);
                return;
            }
            synchronized (auctionItemRecord) {
                // 比较价格
                if (price <= auctionItemRecord.getPrice()) {
                    auctionRep.auctionItemPriceTooLow(channelId);
                    return;
                }
                if (player.getPlayerCommConfig().getMoney() - price < 0) {
                    auctionRep.notEnoughMoney(channelId);
                    return;
                }

                // 返回对应的钱，修改信息
                if (auctionItemRecord.getBuyerId() != null) {
                    Player lastTimePlayer = onlinePlayer.getPlayer(auctionItemRecord.getBuyerId());
                    lastTimePlayer.getPlayerCommConfig().addMoney(auctionItemRecord.getPrice());
                }
                player.getPlayerCommConfig().minusMoney(price);
                auctionItemRecord.setBuyerId(player.getUuid());
                auctionItemRecord.setPrice(price);
                auctionItemRecord.setRounds(auctionItemRecord.getRounds() + 1);
                auctionRep.successAuction(channelId, auctionItemRecord);
            }
        });
    }

    @Override
    public void getBuyItNowItem(String channelId, String buyItNowItemRecordId) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Player player = playerActor.getPlayer();
            AuctionItemRecord buyItNowItem = auctionManager.getBuyItNowItem(buyItNowItemRecordId);
            if (buyItNowItem == null) {
                auctionRep.auctionItemNotExists(channelId);
                return;
            }
            synchronized (buyItNowItem) {
                if (buyItNowItem.getPrice() > player.getPlayerCommConfig().getMoney()) {
                    auctionRep.notEnoughMoney(channelId);
                    return;
                }
                // 移除 -> 扣钱 -> 记录 -> 发邮件
                auctionManager.removeBuyItNowRecord(buyItNowItemRecordId);
                player.getPlayerCommConfig().minusMoney(buyItNowItem.getPrice());
                buyItNowItem.setBuyerId(player.getUuid());
                auctionDao.insertOrUpdateAuctionRecord(buyItNowItem);

                Good good = judgeGood(buyItNowItemRecordId, buyItNowItem.getGoodId(), buyItNowItem.getQuantity());
                writeAuctionItemEmail(player.getUuid(), good, buyItNowItem.getPrice());
            }
        });
    }

    @Override
    public void checkAllBuyItNowItem(String channelId) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Collection<AuctionItemRecord> values = auctionManager.getBuyItNowItemMap().values();
            auctionRep.allBuyItNowInfo(channelId, values);
        });
    }

    @Override
    public void checkAllAuctionItem(String channelId) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            Collection<AuctionItemRecord> values = auctionManager.getAuctionItemMap().values();
            auctionRep.allAuctionInfo(channelId, values);
        });
    }

    @Override
    public void checkAuctionItemRecord(String channelId, String auctionItemRecordId) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {
            AuctionItemRecord auctionItemRecord = auctionManager.getAuctionItem(auctionItemRecordId);
            if (auctionItemRecord == null) {
                auctionItemRecord = auctionManager.getBuyItNowItem(auctionItemRecordId);
            }
            auctionRep.auctionInfo(channelId, auctionItemRecord);
        });
    }

    @Override
    public void cancelBuyItNowItem(String channelId, String buyItNowItemId) {
        PlayerActor playerActor = onlinePlayer.getPlayerActorByChannelId(channelId);
        playerActor.addMessage(playerActor1 -> {

        });
    }

    /**
     * 拍卖时间到期后处理
     */
    public void finishAuctionItemRecord(String auctionItemRecordId) {
        // 检查是否下架
        AuctionItemRecord auctionItemRecord = auctionManager.getAuctionItem(auctionItemRecordId);
        if (auctionItemRecord == null) {
            return;
        }
        // 将记录锁上
        synchronized (auctionItemRecord) {
            auctionManager.removeAuctionItem(auctionItemRecord.getUuid());

//            Good good;
//            // 如果是装备，获取其信息，否则创建新的good对象
//            if (auctionItemRecord.getGoodUuid() != null) {
//                good = goodDao.findGoodByGoodUuid(auctionItemRecord.getGoodUuid());
//            } else {
//                good = createNewGood(Uuid.createUuid(), auctionItemRecord.getGoodId(), auctionItemRecord.getQuantity());
//            }
            Good good = judgeGood(auctionItemRecord.getGoodUuid(), auctionItemRecord.getGoodId(), auctionItemRecord.getQuantity());

            // 记录更新数据库
            auctionDao.insertOrUpdateAuctionRecord(auctionItemRecord);
            // 如果没有人竞拍，以邮件形式返回物品
            if (auctionItemRecord.getBuyerId() == null) {
                writeUnmannedAuctionItemEmail(auctionItemRecord.getSellerId(), good);
                return;
            }
            // 有人竞拍也是以邮件的方式发放
            writeAuctionItemEmail(auctionItemRecord.getBuyerId(), good, auctionItemRecord.getPrice());
        }
    }

    /**
     * 处理一口价物品没有人买
     */
    public void finishBuyItNowItemRecord(String auctionItemRecordId) {
        // 检查是否下架
        AuctionItemRecord auctionItemRecord = auctionManager.getBuyItNowItem(auctionItemRecordId);
        if (auctionItemRecord == null) {
            return;
        }
        // 上锁
        synchronized (auctionItemRecord) {
            // 记录更新数据库
            auctionDao.insertOrUpdateAuctionRecord(auctionItemRecord);
            if (auctionItemRecord.getBuyerId() == null) {

//                Good good;
//                // 如果是装备，获取其信息，否则创建新的good对象
//                if (auctionItemRecord.getGoodUuid() != null) {
//                    good = goodDao.findGoodByGoodUuid(auctionItemRecord.getGoodUuid());
//                } else {
//                    good = createNewGood(Uuid.createUuid(), auctionItemRecord.getGoodId(), auctionItemRecord.getQuantity());
//                }
                Good good = judgeGood(auctionItemRecord.getGoodUuid(), auctionItemRecord.getGoodId(), auctionItemRecord.getQuantity());

                writeUnmannedAuctionItemEmail(auctionItemRecord.getSellerId(), good);
            }
        }
    }

    public AuctionItemRecord createNewAuction(int goodId, String sellerId, int startingPrice, int quantity) {
        AuctionItemRecord auctionItemRecord = new AuctionItemRecord();
        auctionItemRecord.setUuid(Uuid.createUuid());
        auctionItemRecord.setSellerId(sellerId);
        auctionItemRecord.setStartingTime(System.currentTimeMillis());
        auctionItemRecord.setGoodId(goodId);
        auctionItemRecord.setStartingPrice(startingPrice);
        auctionItemRecord.setPrice(startingPrice);
        auctionItemRecord.setQuantity(quantity);
        auctionItemRecord.setRounds(0);
        return auctionItemRecord;
    }

    public Good judgeGood(String auctionItemGoodUuid, int goodId, int quantity) {
        // 如果是装备，获取其信息，否则创建新的good对象
        if (auctionItemGoodUuid != null) {
            return goodDao.findGoodByGoodUuid(auctionItemGoodUuid);
        }
        return createNewGood(Uuid.createUuid(), goodId, quantity);
    }

    public Good createNewGood(String uuid,  int goodId, int quantity) {
        Good good = new Good();
        good.setUuid(uuid);
        good.setGoodId(goodId);
        good.setQuantity(quantity);
        good.setGetTime(System.currentTimeMillis());
        return good;
    }

    public void writeUnmannedAuctionItemEmail(String recipientId, Good good) {
        String title = " 拍卖结果 ";
        String content = " 您的物品未有人出价，请及时从邮件中领取 ";
        writeSystemEmailWithGood(recipientId, content, title, good);
    }

    public void writeAuctionItemEmail(String recipientId, Good good, int price) {
        String title = " 拍卖结果 ";
        String content = String.format(" 您出价: %d 成功拍得商品，请及时从邮件中领取 ", price);
        writeSystemEmailWithGood(recipientId, content, title, good);
    }

    public void writeSystemEmail(String recipientId, String content, String title) {
        emailService.systemWriteEmail(recipientId, content, title);
    }

    public void writeSystemEmailWithGood(String recipientId, String content, String title, Good good) {
        emailService.systemWriteEmailWithGood(recipientId, content, title, good);
    }
}
