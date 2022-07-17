package com.rongxiaoli.plugin.BotCommand.Modules;

import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.plugin.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.plugin.Broadcast.Broadcast;
import com.rongxiaoli.plugin.Picture.PicturePlugin;
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
                switch (arrCommand[2]) {
                    case "setu":
                        PicturePlugin.Enabled = false;
                        SenderContact.sendMessage("插件：setu已禁用");
                        break;
                    case "broadcast":
                        Broadcast.Enabled = false;
                        SenderContact.sendMessage("插件：broadcast已禁用");
                        break;
                    case "autoaccept":
                        AutoAcceptPlugin.Enabled = false;
                        SenderContact.sendMessage("插件：autoaccept已禁用");
                        break;
                    default:
                        SenderContact.sendMessage("未知插件");
                        break;
                }
                break;

            // Enable plugins.
            case "启用":
                if (arrCommand.length <= 2) {
                    SenderContact.sendMessage("请输入启用的插件名");
                }
                switch (arrCommand[2]) {
                    case "setu":
                        PicturePlugin.Enabled = true;
                        SenderContact.sendMessage("插件：setu已启用");
                        break;
                    case "broadcast" :
                        Broadcast.Enabled = true;
                        SenderContact.sendMessage("插件：broadcast已启用");
                        break;
                    case "autoaccept":
                        AutoAcceptPlugin.Enabled = true;
                        SenderContact.sendMessage("插件：autoaccept已启用");
                        break;
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
