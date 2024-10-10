package com.rongxiaoli.module.EmergencyStop;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EmergencyStop extends Module {
    private final String PluginName = "EmergencyStop";
    private boolean IsEnabled = true;

    public static void UnregisteredFriendMain(String[] arrCommand, Contact SenderContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        //Start processing.
        if (RongXiaoliBot.IsEnabled) {
            if (message.length == 0) {
                return;
            }
            //Running.
            if (Objects.equals(message[0], "/stop") && message.length == 1) {
                //Received stop command.
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    //Owner command.
                    SenderContact.sendMessage("收到紧急停止消息，正在停止");
                    RongXiaoliBot.IsEnabled = false;
                }
            } else if (Objects.equals(message[0], "/start")) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    SenderContact.sendMessage("插件正在运行");
                }
            }
        } else {
            //Not running.
            if (Objects.equals(message[0], "/stop") && message.length == 1) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    SenderContact.sendMessage("插件已停止");
                }
            } else if (Objects.equals(message[0], "/start")) {
                if (SenderContact.getId() == RongXiaoliBot.Owner) {
                    RongXiaoliBot.IsEnabled = true;
                    SenderContact.sendMessage("插件已重新启用");
                }
            } else {
                SenderContact.sendMessage("很抱歉，当前插件因特殊原因已被紧急停止，请等待维护");
            }
        }
    }

    public void Init() {
        Log.WriteLog(Log.Level.Debug, "Emergency stop module initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void Shutdown() {
        Log.WriteLog(Log.Level.Debug, "EmergencyStop stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        //Use unregistered main.
        return;
    }

    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
        return;
    }

    /**
     * Plugin name. Use in logs.
     */
    public String getPluginName() {
        return PluginName;
    }

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String getHelpContent() {
        return "";
    }

    /**
     * True if enabled.
     */
    public boolean isEnabled() {
        return IsEnabled;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return false;
    }
}
