package com.rongxiaoli.plugin.BotCommand.Modules;

import com.rongxiaoli.plugin.Picture.PicturePlugin;
import net.mamoe.mirai.contact.Contact;

public class Status {
    public static String CommandPrefix = "status";
    public static String HelpContent =
            "/status : \n" +
            "获取各插件运作状态";
    public static void Process(String[] arrCommand, Contact SenderContact) {
        StringBuilder Message = new StringBuilder();
        if (PicturePlugin.Enabled) {
            Message.append(PicturePlugin.PluginName + ":" + "运作中");
        } else {
            Message.append(PicturePlugin.PluginName + ":" + "已关闭");
        }

        SenderContact.sendMessage(Message.toString());
    }
}
