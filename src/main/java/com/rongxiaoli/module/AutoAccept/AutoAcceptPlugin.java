package com.rongxiaoli.module.AutoAccept;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class AutoAcceptPlugin extends Module {
    private String PluginName = "AutoAccept";
    private boolean IsEnabled = false;

    public void Main(NewFriendRequestEvent e) {
        if (!isEnabled()) {
            if (e.getFromGroup() != null) {
                e.getFromGroup().get(e.getFromId()).sendMessage("暂不支持添加好友");
            }
            e.reject(false);
            Log.WriteLog(Log.Level.Info,
                    "New friend request from: " + e.getFromId() + " rejected: Plugin disabled. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            return;
        }
        e.accept();
    }

    public void Main(FriendAddEvent e) {
        Log.WriteLog(Log.Level.Verbose,
            String.valueOf(e.getFriend().getId()),
            Log.LogClass.ModuleMain,
            PluginName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException IE) {
            Log.WriteLog(Log.Level.Error,
                    "Interrupted. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
        }
        MessageChainBuilder WelcomeMessage = new MessageChainBuilder();
        WelcomeMessage.append("欢迎使用容小狸Bot！\n");
        WelcomeMessage.append("目前主要功能：\n");
        WelcomeMessage.append("涩图，命令：setu [Keyword1] [keyword2] ...\n");
        WelcomeMessage.append("打卡，命令：sign");
        e.getBot().getFriend(e.getFriend().getId()).sendMessage(WelcomeMessage.build());
    }

    public void Main(BotInvitedJoinGroupRequestEvent e) {
        if (!IsEnabled) {
            e.ignore();
            e.getBot().getStranger(e.getInvitorId()).sendMessage("暂不支持邀请进群");
            Log.WriteLog(Log.Level.Info,
                    "New group invite request from: " + e.getGroupId() + " ignored: Plugin disabled. ",
                    Log.LogClass.ModuleMain,
                    PluginName);
            return;
        }
        e.accept();
    }

    public void Main(BotJoinGroupEvent e) {
        if (!IsEnabled) {
            e.getGroup().sendMessage("暂不支持邀请入群");
            e.getGroup().quit();
        }
        MessageChainBuilder WelcomeMessage = new MessageChainBuilder();
        WelcomeMessage.append("欢迎使用容小狸Bot！\n");
        WelcomeMessage.append("目前主要功能：\n");
        WelcomeMessage.append("涩图，命令：setu [Keyword1] [keyword2] ...\n");
        e.getGroup().sendMessage(WelcomeMessage.build());
    }

    public void Init() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "AutoAccept initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void Shutdown() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Info, "AutoAccept off. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        return;
    }

    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
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
        return "";
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
