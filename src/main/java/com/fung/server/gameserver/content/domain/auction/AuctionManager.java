package com.fung.server.gameserver.content.domain.auction;

import com.fung.server.gameserver.content.entity.AuctionItemRecord;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理拍卖行物品
 * @author skytrc@163.com
 * @date 2020/8/3 20:34
 */
@Component
public class AuctionManager {

    /**
     * 竞拍时间
     */
    public static final int AUCTION_FINISH_TIME = 10000;

    /**
     * 存储竞拍物品的id
     * key 竞拍物品 id   value 竞拍记录
     */
    private Map<String, AuctionItemRecord> auctionItemMap;

    /**
     * 记录未购买的一口价商品
     * key 一口价物品id  value 物品记录id
     */
    private Map<String, AuctionItemRecord> buyItNowItemMap;

    private AuctionActor auctionActor;

    public AuctionManager() {
        auctionItemMap = new ConcurrentHashMap<>();
        buyItNowItemMap = new ConcurrentHashMap<>();
        auctionActor = new AuctionActor();
    }

    /**
     * 获取竞拍物品
     */
    public AuctionItemRecord getAuctionItem(String auctionId) {
        return auctionItemMap.get(auctionId);
    }

    /**
     * 获取一口价商品
     */
    public AuctionItemRecord getBuyItNowItem(String auctionId) {
        return buyItNowItemMap.get(auctionId);
    }

    /**
     * 添加竞拍物品
     */
    public void addAuctionItem(AuctionItemRecord auctionItemRecord) {
        auctionItemMap.put(auctionItemRecord.getUuid(), auctionItemRecord);
    }

    /**
     * 添加一口价物品
     */
    public void addBuyItNowItem(AuctionItemRecord auctionItemRecord) {
        buyItNowItemMap.put(auctionItemRecord.getUuid(), auctionItemRecord);
    }

    /**
     * 移除竞拍物品（拍主取消、时间到未购买、时间到已购买）
     */
    public AuctionItemRecord removeAuctionItem(String auctionItemId) {
        return auctionItemMap.remove(auctionItemId);
    }

    /**
     * 移除一口价物品（拍主取消，时间到未购买，已购买）
     */
    public AuctionItemRecord removeBuyItNowRecord(String buyItNowItemId) {
        return buyItNowItemMap.remove(buyItNowItemId);
    }


    public AuctionActor getAuctionActor() {
        return auctionActor;
    }

    public Map<String, AuctionItemRecord> getAuctionItemMap() {
        return auctionItemMap;
    }

    public Map<String, AuctionItemRecord> getBuyItNowItemMap() {
        return buyItNowItemMap;
    }
}
