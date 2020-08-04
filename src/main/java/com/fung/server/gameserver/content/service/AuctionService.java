package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 20:58
 */
public interface AuctionService {

    void addAuctionItem(String channelId, int backpackPosition, int quantity, int startingPrice);

    void addBuyItNowItem(String channelId, int backpackPosition, int quantity, int price);

    /**
     * @param channelId
     * @param auctionItemRecordId
     * @param price
     */
    void auctionItem(String channelId, String auctionItemRecordId, int price);

    /**
     * @param channelId
     * @param buyItNowItemRecordId
     */
    void getBuyItNowItem(String channelId, String buyItNowItemRecordId);

    void checkAllBuyItNowItem(String channelId);

    void checkAllAuctionItem(String channelId);

    void checkAuctionItemRecord(String channelId, String auctionItemRecordId);

    /**
     * TODO 取消功能
     */
    void cancelBuyItNowItem(String channelId, String buyItNowItemId);
}
