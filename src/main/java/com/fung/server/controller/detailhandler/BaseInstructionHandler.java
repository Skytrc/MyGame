package com.fung.server.controller.detailhandler;

import java.util.List;

/**
 * @author skytrc@163.coms
 * @date 2020/5/5 16:13
 */
public abstract class BaseInstructionHandler {

    /**
     * 指令对应的channel id
     */
    private String channelId;

    /**
     * 判断指令
     * @param ins 指令
     * @return 处理结果
     */
    public abstract String handler(List<String> ins);

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
