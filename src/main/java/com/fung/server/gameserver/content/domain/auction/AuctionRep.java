package com.fung.server.gameserver.content.domain.auction;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.entity.AuctionItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author skytrc@163.com
 * @date 2020/8/4 0:00
 */
@Component
public class AuctionRep {

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    public void personalBackpackFull(String channelId) {
        writeMessage2Client.writeMessage(channelId, "\n背包已满或背包该格子不存在该物品");
    }

    public void successAddAuctionItem(String channelId) {
        writeMessage2Client.writeMessage(channelId, "\n成功将物品放入拍卖行");
    }

    public void auctionItemNotExists(String channelId) {
        writeMessage2Client.writeMessage(channelId, "\n竞拍物品不存在或拍卖时间已到");
    }

    public void auctionItemPriceTooLow(String channelId) {
        writeMessage2Client.writeMessage(channelId, "\n出价不能低于竞拍价格");
    }

    public void notEnoughMoney(String channelId) {
        writeMessage2Client.writeMessage(channelId, "\n没有足够的金钱购买");
    }

    public void successAuction(String channelId, AuctionItemRecord auctionItemRecord) {
        writeMessage2Client.writeMessage(channelId, String.format("\n 你成功出价 %d , 单号为 %s", auctionItemRecord.getPrice(), auctionItemRecord.getUuid()));
    }

    public void allBuyItNowInfo(String channelId, Collection<AuctionItemRecord> auctionItemRecords) {
        StringBuilder res = new StringBuilder("\n 一口价商店有: ");
        for (AuctionItemRecord auctionItemRecord : auctionItemRecords) {
            res.append(String.format("\n拍卖编号: %s  拍卖商品 %s  拍卖数量 %d  拍卖价格 %d  拍卖剩余时间 %d毫秒", auctionItemRecord.getUuid()
                    , goodManager.getGoodNameById(auctionItemRecord.getGoodId()), auctionItemRecord.getQuantity()
                    , auctionItemRecord.getPrice()
                    , auctionItemRecord.getStartingTime() + AuctionManager.AUCTION_FINISH_TIME - System.currentTimeMillis()));
        }
        writeMessage2Client.writeMessage(channelId, res.toString());
    }

    public void allAuctionInfo(String channelId, Collection<AuctionItemRecord> auctionItemRecords) {
        StringBuilder res = new StringBuilder("\n 拍卖行有: ");
        for (AuctionItemRecord auctionItemRecord : auctionItemRecords) {
            res.append(String.format("\n拍卖编号: %s  拍卖商品 %s  拍卖数量 %d  开始价格 %d  当前价格 %d  拍卖轮数 %d  拍卖剩余时间 %d毫秒", auctionItemRecord.getUuid()
                    , goodManager.getGoodNameById(auctionItemRecord.getGoodId()), auctionItemRecord.getQuantity()
                    , auctionItemRecord.getStartingPrice(), auctionItemRecord.getPrice(), auctionItemRecord.getRounds()
                    , auctionItemRecord.getStartingTime() + AuctionManager.AUCTION_FINISH_TIME - System.currentTimeMillis()));
        }
        writeMessage2Client.writeMessage(channelId, res.toString());
    }

    /**
     * 偷懒直接copy
     */
    public void auctionInfo(String channelId, AuctionItemRecord auctionItemRecord) {
        String res = "\n";
        if (auctionItemRecord.isBuyItNowItem()) {
            res = res + String.format("\n拍卖编号: %s  拍卖商品 %s  拍卖数量 %d  拍卖价格 %d  拍卖剩余时间 %d毫秒", auctionItemRecord.getUuid()
                    , goodManager.getGoodNameById(auctionItemRecord.getGoodId()), auctionItemRecord.getQuantity()
                    , auctionItemRecord.getPrice()
                    , auctionItemRecord.getStartingTime() + AuctionManager.AUCTION_FINISH_TIME - System.currentTimeMillis());
        } else {
            res = res + String.format("\n拍卖编号: %s  拍卖商品 %s  拍卖数量 %d  开始价格 %d  当前价格 %d  拍卖轮数 %d  拍卖剩余时间 %d毫秒", auctionItemRecord.getUuid()
                    , goodManager.getGoodNameById(auctionItemRecord.getGoodId()), auctionItemRecord.getQuantity()
                    , auctionItemRecord.getStartingPrice(), auctionItemRecord.getPrice(), auctionItemRecord.getRounds()
                    , auctionItemRecord.getStartingTime() + AuctionManager.AUCTION_FINISH_TIME - System.currentTimeMillis());
        }
        writeMessage2Client.writeMessage(channelId, res);
    }
}
