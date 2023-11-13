package top.rongxiaoli.module.Baltop;

import top.rongxiaoli.Module;
import top.rongxiaoli.RongXiaoliBot;
import top.rongxiaoli.backend.Log;
import top.rongxiaoli.data.User;
import top.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInStruct;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Baltop extends Module {
    private final String PluginName = "Baltop";
    private final String HelpContent = "/baltop\n" +
            "输出财富榜前10。";
    private boolean IsEnabled = true;

    /**
     * Module initiate function.
     */
    public void Init() {
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,
                "Baltop initiated. ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug,
                "Baltop shutting down. ",
                Log.LogClass.ModuleMain,
                PluginName);
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
        return this.PluginName;
    }

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String getHelpContent() {
        return this.HelpContent;
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
        this.IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return false;
    }
}
