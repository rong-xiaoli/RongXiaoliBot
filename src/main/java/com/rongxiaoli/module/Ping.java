package com.rongxiaoli.module;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;

public class Ping extends Module {
    private String PluginName = "Ping";
    private String HelpContext = "/ping +\n" +
            "返回\"Pong!\"";
    private boolean IsEnabled = false;
    /**
     * Module initiate function.
     */
    public void Init() {
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,"Ping initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug,"Ping shutting down. ", Log.LogClass.ModuleMain, PluginName);

    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SenderContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {
        //0-length array.
        if (arrCommand.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (arrCommand[0].equals("ping")) SenderContact.sendMessage("Pong! ");
    }

    /**
     * Group message process.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SenderContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact) {
        //0-length array.
        if (arrCommand.length == 0) {
            return;
        }
        if (arrCommand[0].equals("ping")) SenderContact.sendMessage("Pong! ");
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
        return HelpContext;
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
