package com.rongxiaoli.module.Ping;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;

import java.util.Arrays;
import java.util.List;

public class Ping extends Module {
    private final String PluginName = "Ping";
    private final String HelpContent = "/ping +\n" +
            "返回\"Pong!\"";
    private boolean IsEnabled = false;

    /**
     * Module initiate function.
     */
    public void Init() {
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "Ping initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "Ping shutting down. ", Log.LogClass.ModuleMain, PluginName);

    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        //0-length array.
        if (arrCommand.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (arrCommand[0].equals("ping")) SubjectContact.sendMessage("Pong! ");
    }

    /**
     * Group message process.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SubjectContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        //0-length array.
        if (arrCommand.length == 0) {
            return;
        }
        if (arrCommand[0].equals("ping")) SubjectContact.sendMessage("Pong! ");
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
        return HelpContent;
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
