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
    public abstract void Shutdown();

    /**
     * Friend message process.
     */
    public abstract void FriendMain(String[] arrCommand, long Friend, Contact SenderContact);

    /**
     * Group message process.
     */
    public abstract void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact);

    /**
     * Plugin name. Use in logs.
     */
    public String PluginName;

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String HelpContent;

    /**
     * True if enabled.
     */
    public boolean IsEnabled;

    /**
     * Debug mode.
     */
    public boolean DebugMode;
}
