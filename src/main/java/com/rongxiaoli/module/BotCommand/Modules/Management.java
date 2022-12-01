package com.rongxiaoli.module.BotCommand.Modules;

import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.module.BotCommand.BotCommand;
import com.rongxiaoli.module.Broadcast.Broadcast;
import com.rongxiaoli.module.DailySign.DailySign;
import com.rongxiaoli.module.Picture.PicturePlugin;
import net.mamoe.mirai.contact.Contact;

public class Management {
    public static String CommandPrefix = "管理";
    public static void Process(String[] arrCommand, Contact SenderContact) {
        if (SenderContact.getId() != RongXiaoliBot.Owner) {
            return;
        }
        if (arrCommand.length <= 1) {
            SenderContact.sendMessage("请输入指令");
            return;
        }
        switch (arrCommand[1]) {
            // Disable plugins.
            case "禁用":
                if (arrCommand.length <= 2) {
                    SenderContact.sendMessage("请输入禁用插件名");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Plugin disable request, plugin name: " + arrCommand[2],
                        Log.LogClass.ModuleMain,
                        BotCommand.PluginName);
                switch (arrCommand[2]) {
                    case "setu":
                        PicturePlugin.IsEnabled = false;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已禁用");
                        break;
                    case "broadcast":
                        Broadcast.IsEnabled = false;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已禁用");
                        break;
                    case "autoaccept":
                        AutoAcceptPlugin.IsEnabled = false;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已禁用");
                        break;
                    case "dailysign" :
                        DailySign.Enabled = false;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已禁用");
                    default:
                        SenderContact.sendMessage("未知插件");
                        break;
                }
                break;

            // Enable plugins.
            case "启用":
                if (arrCommand.length <= 2) {
                    SenderContact.sendMessage("请输入启用的插件名");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Plugin enable request, plugin name: " + arrCommand[2],
                        Log.LogClass.ModuleMain,
                        BotCommand.PluginName);
                switch (arrCommand[2]) {
                    case "setu":
                        PicturePlugin.IsEnabled = true;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已启用");
                        break;
                    case "broadcast" :
                        Broadcast.IsEnabled = true;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已启用");
                        break;
                    case "autoaccept":
                        AutoAcceptPlugin.IsEnabled = true;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已启用");
                        break;
                    case "dailysign" :
                        DailySign.Enabled = true;
                        SenderContact.sendMessage("插件：" + arrCommand[2] + "已启用");
                    default:
                        SenderContact.sendMessage("未知插件");
                        break;
                }
                break;
            default:
                SenderContact.sendMessage("未知的命令");
                break;
        }
    }
}
