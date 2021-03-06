package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 11:46
 */
public interface MoveService {

    /**
     * 修改玩家距离
     * @param x X轴距离
     * @param y Y轴距离
     * @param channelId channelId
     * @return 是否成功
     */
    String move(int x, int y, String channelId) throws InterruptedException;

    /**
     * 给定坐标自动移动
     * @param xy 坐标数组
     * @param channelId channelId
     * @return 是否成功
     */
    String move(int[] xy, String channelId) throws InterruptedException;

    /**
     * 地图传送
     * @param channelId channelId
     * @return 转移地图是否成功，如果成功返回新地图的信息，不成功则返回其他消息
     */
    String mapTransfer(String channelId);
}
