package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/29 17:01
 */
@Component
public class ShopHandler extends BaseInstructionHandler{

    @Autowired
    private ShopService shopService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String remove = ins.remove(0);
        if("buy".equals(remove)) {
            return buyGood(ins);
        } else if ("shell".equals(remove)) {
            return shellGood(ins);
        }
        return "商店命令错误";
    }

    public String buyGood(List<String> ins) {
        if (ins.size() < 2) {
            return "\n购买指令错误";
        }
        try {
            int goodId = Integer.parseInt(ins.remove(0));
            int goodQuantity = Integer.parseInt(ins.remove(0));
            return shopService.buyGood(getChannelId(), goodId, goodQuantity);
        } catch (NumberFormatException ignored) {
            return "\n购买物品id，购买数量必须为数字";
        }
    }

    public String shellGood(List<String> ins) {
        if (ins.size() < 2) {
            return "\n出售指令错误";
        }
        try {
            int goodPosition = Integer.parseInt(ins.remove(0));
            int goodQuantity = Integer.parseInt(ins.remove(0));
            return shopService.shellGood(getChannelId(), goodPosition, goodQuantity);
        } catch (NumberFormatException numberFormatException) {
            return "\n出售物品位置，出售物品数量必须为数字";
        }
    }

}
