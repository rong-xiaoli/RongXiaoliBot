package com.rongxiaoli.module.DailySign;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Timer;
import net.mamoe.mirai.contact.Contact;
import org.jetbrains.annotations.Nullable;

public class DailySign extends Module {
    // Public vars.
    private String PluginName = "DailySign";
    private String HelpContent;
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    // Private vars.

    // Vars def end.
    /**
     * Module initiate function.
     */
    public void Init() {

    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {

    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SenderContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {

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
        return this.IsEnabled;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        this.IsEnabled = isEnabled();
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return DebugMode;
    }
}
