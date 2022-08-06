package com.rongxiaoli.plugin.BotCommand;

import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.plugin.BotCommand.Modules.Help;
import com.rongxiaoli.plugin.BotCommand.Modules.Management;
import com.rongxiaoli.plugin.BotCommand.Modules.Status;
import net.mamoe.mirai.contact.Contact;

import java.util.Objects;

public class BotCommand {
    public static String PluginName = "BotCommand";
    public static void Main(String[] arrCommand, Contact SenderContact) {
        //Judge if message is 0 width.
        if (arrCommand.length == 0) {
            return;
        }
        if (arrCommand[0].startsWith("/")) {
            //Pre-process.
            arrCommand[0] = arrCommand[0].replaceFirst("/","");

            if (Objects.equals(arrCommand[0], Management.CommandPrefix)) {
                //Management.
                if (SenderContact.getId() != RongXiaoliBot.Owner) {
                    return;
                }
                Management.Process(arrCommand, SenderContact);
                //End.
            } else if (Objects.equals(arrCommand[0], Help.CommandPrefix)) {
                //Help message.
                Help.Process(arrCommand,SenderContact);
                //End.
            } else if (Objects.equals(arrCommand[0], Status.CommandPrefix)) {
                //Status.
                Status.Process(arrCommand, SenderContact);
                //End.
            }
        }
    }
}
