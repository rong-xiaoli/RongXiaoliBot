package com.rongxiaoli.module.DailySign;
//Todo: Finish this module.
import com.rongxiaoli.Module;
import net.mamoe.mirai.contact.Contact;

public class DailySign extends Module {
    // Public vars.
    private final String PluginName = "DailySign";
    private String HelpContent = "DailySign\n" +
            "每日签到";
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    // Private vars.

    // Vars def end.
    /**
     * Module initiate function.
     */
    public void Init() {
        //Ready to read JSON.
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        //Todo: Add JSON save func.
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        //0-width.
        if (arrCommand.length == 0) {
            return;
        }
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
        //0-width.
        if (arrCommand.length == 0) {
            return;
        }
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
