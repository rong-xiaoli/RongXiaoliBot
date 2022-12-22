package com.rongxiaoli.module.BotCommand.Modules;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import net.mamoe.mirai.contact.Contact;

public class Help {
    public static String CommandPrefix = "help";
    public static String HelpContent =
            "/help [command]: \n" +
                    "获取帮助\n" +
                    "参数: \n" +
                    "command: 要查看的命令的帮助";

    public void Process(String[] arrCommand, Contact SenderContact) {
        StringBuilder HelpMessage = new StringBuilder();
        if (arrCommand.length == 1) {
            for (Module SingleModule :
                    RongXiaoliBot.BotModuleLoader.ModuleList) {
                HelpMessage.append(SingleModule.getHelpContent() + "\n");
            }
            SenderContact.sendMessage(HelpMessage.toString());
        } else if (arrCommand.length == 2) {
            for (Module SingleModule :
                    RongXiaoliBot.BotModuleLoader.ModuleList) {
                if (SingleModule.getPluginName().equals(arrCommand[1])) {
                    SenderContact.sendMessage(SingleModule.getHelpContent());
                }
            }
        }
    }
}
