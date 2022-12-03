package com.rongxiaoli;

import net.mamoe.mirai.contact.Contact;

/**
 * Module class.
 */
public abstract class Module {

    /**
     * Module initiate function.
     */
    public abstract void Init();

    /**
     * Module shutdown function.
     */
    public abstract void Shutdown();

    /**
     * Friend message process.
     */
    public abstract void FriendMain(String[] arrCommand, long Friend, Contact SenderContact);

    /**
     * Group message process.
     */
    public abstract void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact);

    private String PluginName;

    private String HelpContent;

    private boolean IsEnabled;

    private boolean DebugMode;

    /**
     * Plugin name. Use in logs.
     */
    public abstract String getPluginName();

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public abstract String getHelpContent();

    /**
     * True if enabled.
     */
    public abstract boolean isEnabled();

    /**
     * Set status.
     * @param status Status
     */
    public abstract void setEnabled(boolean status);

    /**
     * Debug mode.
     */
    public abstract boolean isDebugMode();
}
