package com.rongxiaoli.module.BotCommand.Modules;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.BotCommand.BotCommand;
import net.mamoe.mirai.contact.Contact;

public class Management {
    public static String CommandPrefix = "manage";

    public void Process(String[] arrCommand, Contact SenderContact) {
        if (SenderContact.getId() != RongXiaoliBot.Owner) {
            return;
        }
        if (arrCommand.length <= 1) {
            SenderContact.sendMessage("请输入指令");
            return;
        }
        // Disable plugins.
        if ("disable".equals(arrCommand[1])) {
            if (arrCommand.length <= 2) {
                SenderContact.sendMessage("请输入禁用插件名");
                return;
            }
            Log.WriteLog(Log.Level.Info,
                    "Plugin disable request, plugin name: " + arrCommand[2],
                    Log.LogClass.ModuleMain,
                    BotCommand.PluginName);
            for (Module SingleModule :
                    RongXiaoliBot.BotModuleLoader.ModuleList) {
                if (SingleModule.getPluginName().equals(arrCommand[2])) {
                    SingleModule.setEnabled(false);
                    SenderContact.sendMessage(SingleModule.getPluginName() + " disabled. ");
                }
            }

            // Enable plugins.
        } else if ("enable".equals(arrCommand[1])) {
            if (arrCommand.length <= 2) {
                SenderContact.sendMessage("请输入启用的插件名");
                return;
            }
            Log.WriteLog(Log.Level.Info,
                    "Plugin enable request, plugin name: " + arrCommand[2],
                    Log.LogClass.ModuleMain,
                    BotCommand.PluginName);
            for (Module SingleModule :
                    RongXiaoliBot.BotModuleLoader.ModuleList) {
                if (SingleModule.getPluginName().equals(arrCommand[2])) {
                    SingleModule.setEnabled(true);
                    SenderContact.sendMessage(SingleModule.getPluginName() + " enabled. ");
                }
            }
        } else {
            SenderContact.sendMessage("Unknown command. ");
        }
    }
}
