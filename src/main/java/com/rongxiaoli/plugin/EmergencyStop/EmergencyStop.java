package com.rongxiaoli.plugin.EmergencyStop;

import com.rongxiaoli.RongXiaoliBot;
import net.mamoe.mirai.contact.Contact;

import java.util.Objects;

public class EmergencyStop {
    public static void Main(String[] arrCommand, Contact SenderContact) {
        if (RongXiaoliBot.isPluginRunning) {
            if (arrCommand.length == 0) {
                return;
            }
            //Running.
            if (Objects.equals(arrCommand[0], "/stop") && arrCommand.length == 1) {
                //Received stop command.
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    //Owner command.
                    SenderContact.sendMessage("收到紧急停止消息，正在停止");
                    RongXiaoliBot.isPluginRunning = false;
                }
            } else if (Objects.equals(arrCommand[0], "/start")) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    SenderContact.sendMessage("插件正在运行");
                }
            }
        } else {
            //Not running.
            if (Objects.equals(arrCommand[0], "/stop") && arrCommand.length == 1) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    SenderContact.sendMessage("插件已停止");
                }
            } else if (Objects.equals(arrCommand[0], "/start")) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    RongXiaoliBot.isPluginRunning = true;
                    SenderContact.sendMessage("插件已重新启用");
                }
            } else {
                SenderContact.sendMessage("很抱歉，当前插件因特殊原因已被紧急停止，请等待维护");
            }
        }

    }
}
