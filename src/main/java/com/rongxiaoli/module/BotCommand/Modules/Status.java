package com.rongxiaoli.module.BotCommand.Modules;

import com.rongxiaoli.module.DailySign.DailySign;
import com.rongxiaoli.module.Picture.PicturePlugin;
import net.mamoe.mirai.contact.Contact;

public class Status {
    public static String CommandPrefix = "status";
    public static String HelpContent =
            "/status : \n" +
            "获取各插件运作状态";
    public static void Process(String[] arrCommand, Contact SenderContact) {
        StringBuilder Message = new StringBuilder();
        if (PicturePlugin.IsEnabled) {
            Message.append(PicturePlugin.PluginName + ":" + "运作中");
        } else {
            Message.append(PicturePlugin.PluginName + ":" + "已关闭");
        }

        if (DailySign.Enabled) {
            Message.append(DailySign.PluginName + ":" + "运作中");
        } else {
            Message.append(DailySign.PluginName + ":" + "已关闭");
        }

        SenderContact.sendMessage(Message.toString());
    }
}
