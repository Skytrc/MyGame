package com.fung.server.gameserver.content.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 20:07
 */
@Entity
@Table(name = "auction_item")
public class AuctionItemRecord {

    @Id
    private String uuid;

    /**
     * 卖家id
     */
    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "good_id")
    private int goodId;

    /**
     * 如果是装备，则表明其uuid
     */
    @Column(name = "good_Uuid")
    private String goodUuid;

    /**
     * 买家id
     */
    @Column(name = "buyer_id")
    private String buyerId;

    /**
     * 价格(时间到了就成了最终价格)
     * 如果是一口价物品，开始价格就是价格
     */
    private int price;

    /**
     * 竞价轮数
     */
    private int rounds;

    /**
     * 开始的价格
     */
    private int startingPrice;

    /**
     * 开始时间
     */
    private long startingTime;

    private int quantity;


    /**
     * 是否为一口价物品
     */
    @Column(name = "buy_it_now_item")
    private boolean buyItNowItem;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getGoodUuid() {
        return goodUuid;
    }

    public void setGoodUuid(String goodUuid) {
        this.goodUuid = goodUuid;
    }

    public boolean isBuyItNowItem() {
        return buyItNowItem;
    }

    public void setBuyItNowItem(boolean buyItNowItem) {
        this.buyItNowItem = buyItNowItem;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
