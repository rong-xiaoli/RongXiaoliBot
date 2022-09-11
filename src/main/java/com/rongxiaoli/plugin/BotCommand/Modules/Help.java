package com.rongxiaoli.plugin.BotCommand.Modules;

import com.rongxiaoli.plugin.DailySign.DailySign;
import com.rongxiaoli.plugin.Picture.PicturePlugin;
import net.mamoe.mirai.contact.Contact;

public class Help {
    public static String CommandPrefix = "help";
    public static String HelpContent =
            "/help [command]: \n" +
            "获取帮助\n" +
            "参数: \n" +
            "command: 要查看的命令的帮助";

    public static void Process(String[] arrCommand, Contact SenderContact) {
        StringBuilder HelpMessage = new StringBuilder();
        if (arrCommand.length == 1) {
            HelpMessage.append("容小狸Bot帮助: \n");
            HelpMessage.append(HelpContent + "\n");
            HelpMessage.append(DailySign.HelpContent + "\n");
            HelpMessage.append(PicturePlugin.HelpContent + "\n");
            HelpMessage.append(Status.HelpContent + "\n");
            SenderContact.sendMessage(HelpMessage.toString());
        } else if (arrCommand.length == 2) {
            switch (arrCommand[1]) {
                case "RSign":
                    SenderContact.sendMessage(DailySign.HelpContent);
                    break;
                case "setu":
                    SenderContact.sendMessage(PicturePlugin.HelpContent);
                    break;
                case "help":
                    SenderContact.sendMessage(HelpContent);
                    break;
                case "status":
                    SenderContact.sendMessage(Status.HelpContent);
                    break;
                default:
                    SenderContact.sendMessage("未知命令");
                    break;
            }
        }
    }
}
