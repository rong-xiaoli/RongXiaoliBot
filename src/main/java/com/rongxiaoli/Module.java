package com.rongxiaoli;

import net.mamoe.mirai.contact.Contact;

/**
 * Module class.
 */
public abstract class Module {

    private String PluginName;
    private String HelpContent;
    private boolean IsEnabled;
    private boolean DebugMode;

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
    public abstract void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact);

    /**
     * Group message process.
     */
    public abstract void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact);

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
     *
     * @param status Status
     */
    public abstract void setEnabled(boolean status);

    /**
     * Debug mode.
     */
    public abstract boolean isDebugMode();

    /**
     * This class is used to store all the private data the module needs.
     */
    public static abstract class ModuleData {
        /**
         * This module is used to initiate the data.
         */
        public abstract void init();
    }
}
