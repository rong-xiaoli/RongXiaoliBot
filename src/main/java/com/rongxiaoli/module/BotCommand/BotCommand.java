package com.rongxiaoli.module.BotCommand;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.BotCommand.Modules.Help;
import com.rongxiaoli.module.BotCommand.Modules.Management;
import com.rongxiaoli.module.BotCommand.Modules.Status;
import net.mamoe.mirai.contact.Contact;

import java.util.Objects;

public class BotCommand extends Module {
    public static String PluginName = "BotCommand";
    public static void UnregisteredFriendMain(String[] arrCommand, Contact SenderContact) {
        //Judge if message is 0 width.
        if (arrCommand.length == 0) {
            return;
        }
        String[] message = arrCommand.clone();
        if (message[0].startsWith("/")) {
            //Pre-process.
            message[0] = message[0].replaceFirst("/","");

            if (Objects.equals(message[0], Management.CommandPrefix)) {
                //Management.
                if (SenderContact.getId() != RongXiaoliBot.Owner) {
                    return;
                }
                Management m = new Management();
                m.Process(message, SenderContact);
                //End.
            } else if (Objects.equals(message[0], Help.CommandPrefix)) {
                //Help message.
                Help h = new Help();
                h.Process(message, SenderContact);
                //End.
            } else if (Objects.equals(message[0], Status.CommandPrefix)) {
                //Status.
                Status.Process(message, SenderContact);
                //End.
            }
        }
    }
    public static void UnregisteredGroupMain(String[] arrCommand, Contact SenderContact) {
        //Judge if message is 0 width.
        if (arrCommand.length == 0) {
            return;
        }
        String[] message = arrCommand.clone();
        if (message[0].startsWith("/")) {
            //Pre-process.
            message[0] = message[0].replaceFirst("/","");
            if (Objects.equals(message[0], Help.CommandPrefix)) {
                //Help message.
                Help h = new Help();
                h.Process(message, SenderContact);
                //End.
            } else if (Objects.equals(message[0], Status.CommandPrefix)) {
                //Status.
                Status.Process(message, SenderContact);
                //End.
            }
        }
    }

    public void Init() {
        Log.WriteLog(Log.Level.Debug, "BotCommand initiated. ", Log.LogClass.ModuleMain, PluginName);
    }
    public void Shutdown() {
        Log.WriteLog(Log.Level.Debug, "BotCommand stopped. ", Log.LogClass.ModuleMain, PluginName);
    }
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
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
        return true;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        return;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return false;
    }
}
