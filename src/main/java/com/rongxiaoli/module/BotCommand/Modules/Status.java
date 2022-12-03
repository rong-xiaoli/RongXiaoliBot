package com.rongxiaoli.module.BotCommand.Modules;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import net.mamoe.mirai.contact.Contact;

public class Status {
    public static String CommandPrefix = "status";
    public static String HelpContent =
            "/status : \n" +
                    "获取各插件运作状态";

    public static void Process(String[] arrCommand, Contact SenderContact) {

        StringBuilder Message = new StringBuilder();
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            Message.append(SingleModule.getPluginName()).append(": ");
            if (SingleModule.isEnabled()) {
                Message.append("On\n");
            } else {
                Message.append("Off\n");
            }
        }
        SenderContact.sendMessage(Message.toString());
    }
}
