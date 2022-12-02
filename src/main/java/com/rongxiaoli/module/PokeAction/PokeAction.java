package com.rongxiaoli.module.PokeAction;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;

import java.util.Random;

public class PokeAction extends Module {

    // Module status vars.
    private String PluginName = "PokeAction";
    private String HelpContent = "戳一戳：无帮助文档。\n";
    private Boolean IsEnabled = false;
    private Boolean DebugMode = false;

    // Module private vars.
    private Random rand;
    /**
     * Module initiate function.
     */
    public void Init() {
        rand = new Random();
        long seed = rand.nextLong();
        rand.setSeed(seed);
        Log.WriteLog(Log.Level.Verbose, "Seed: " + seed, Log.LogClass.ModuleMain, PluginName);
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "PokeAction initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     *
     */
    public void Shutdown() {
        Log.WriteLog(Log.Level.Debug, "PokeAction stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SenderContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {
        return;
    }

    /**
     * Group message process.
     * Not used.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SenderContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact) {
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
        return DebugMode;
    }
}
