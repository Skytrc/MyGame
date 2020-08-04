package com.fung.server.gameserver.content.dao;

import com.fung.server.gameserver.content.entity.AuctionItemRecord;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 21:13
 */
public interface AuctionDao {

    /**
     * 插入或更新竞拍记录（包括一口价和竞拍物品）
     */
    void insertOrUpdateAuctionRecord(AuctionItemRecord auctionItemRecord);

    /**
     * 根据id获取竞拍记录
     */
    AuctionItemRecord getRecord(String recordId);
}
