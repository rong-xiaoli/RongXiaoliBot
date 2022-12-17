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
        switch (arrCommand[1]) {
            // Disable plugins.
            case "disable":
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
                    }
                }
                break;

            // Enable plugins.
            case "enable":
                if (arrCommand.length <= 2) {
                    SenderContact.sendMessage("请输入启用的插件名");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Plugin enable request, plugin name: " + arrCommand[2],
                        Log.LogClass.ModuleMain,
                        BotCommand.PluginName);
                for (Module SinleModule :
                        RongXiaoliBot.BotModuleLoader.ModuleList) {
                    if (SinleModule.getPluginName().equals(arrCommand[2])) {
                        SinleModule.setEnabled(true);
                    }
                }
                break;
            default:
                SenderContact.sendMessage("未知的命令");
                break;
        }
    }
}
