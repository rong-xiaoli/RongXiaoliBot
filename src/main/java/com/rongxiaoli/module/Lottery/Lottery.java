package com.rongxiaoli.module.Lottery;

import com.rongxiaoli.Module;
import net.mamoe.mirai.contact.Contact;

public class Lottery extends Module {
    private final String PluginName = "Lottery";
    private boolean IsEnabled = false;
    private final String HelpContent = "/lottery (或/l)\n" +
            "抽奖\n" +
            "抽奖随机数公式在GitHub主页放出。";
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
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {

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
