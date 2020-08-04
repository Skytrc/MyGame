package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/21 16:28
 */
@Component
public class EmailHandler extends BaseInstructionHandler{

    @Autowired
    private EmailService emailService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String remove = ins.remove(0);
        switch (remove){
            case("create"): return create(ins);
            case("write"): return write(ins);
            case("checkmailbox"): return checkMailBox();
            case("checkemail"): return checkEmail(ins.remove(0));
            case("send"): return sendEmail(ins.remove(0));
            case("add"): return add(ins);
            case("getgood"): return getGood(ins.remove(0));
            default: return "\n邮件指令错误";
        }
    }

    public String create(List<String> ins) {
        if (ins.size() == 0){
            return "\n收信人ID不能为空";
        }
        return emailService.createEmail(getChannelId(), ins.remove(0));
    }

    public String write(List<String> list) {
        if (list.size() < 3) {
            return "\n邮件编写命令错误";
        }
        return emailService.writeContent(getChannelId(), list.remove(0), list.remove(0), list.remove(0));
    }

    public String add(List<String> ins) {
        if (ins.size() < 3) {
            return "\n邮件添加物品命令错误";
        }
        try {
            String emailId = ins.remove(0);
            int position = Integer.parseInt(ins.remove(0));
            int quantity = Integer.parseInt(ins.remove(0));
            return emailService.addGood(getChannelId(), emailId, position, quantity);
        } catch (NumberFormatException ignored) {
            return "\n物品位置，数量必须为数字";
        }
    }

    public String getGood(String emailId) {
        return emailService.gotGoodFromEmail(getChannelId(), emailId);
    }

    public String delete(String emailId) {
        return emailService.deleteResEmail(getChannelId(), emailId);
    }

    public String checkMailBox() {
        return emailService.checkMailBox(getChannelId());
    }

    public String checkEmail(String emailId) {
        return emailService.checkAEmail(getChannelId(), emailId);
    }

    public String sendEmail(String emailId) {
        return emailService.send(getChannelId(), emailId);
    }
}
