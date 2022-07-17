package com.rongxiaoli.plugin.AutoAccept;

import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class AutoAcceptPlugin {
    public static String PluginName = "AutoAccept";
    public static boolean Enabled = false;
    public static void Main(NewFriendRequestEvent e) {
        if (!Enabled) {
            if (e.getFromGroup() != null) {
                e.getFromGroup().get(e.getFromId()).sendMessage("暂不支持添加好友");
            }
            e.reject(false);
            Log.WriteLog(Log.Level.Info,
                    "New friend request from: " + e.getFromId() +" rejected: Plugin disabled. ",
                    Log.Module.PluginMain,
                    PluginName);
            return;
        }
        e.accept();
    }
    public static void Main(FriendAddEvent e) {
        for (Friend SingleFriend :
                e.getBot().getFriends()) {
            Log.WriteLog(Log.Level.Verbose,
                    String.valueOf(SingleFriend.getId()),
                    Log.Module.PluginMain,
                    PluginName);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException IE) {
            Log.WriteLog(Log.Level.Error,
                    "Interrupted. ",
                    Log.Module.PluginMain,
                    PluginName);
        }
        MessageChainBuilder WelcomeMessage = new MessageChainBuilder();
        WelcomeMessage.append("欢迎使用容小狸Bot！\n");
        WelcomeMessage.append("目前主要功能：\n");
        WelcomeMessage.append("涩图，命令：setu [Keyword1] [keyword2] ...\n");
        WelcomeMessage.append("打卡，命令：sign");
        e.getBot().getFriend(e.getFriend().getId()).sendMessage(WelcomeMessage.build());
    }
    public static void Main(BotInvitedJoinGroupRequestEvent e) {
        if (!Enabled) {
            e.ignore();
            e.getBot().getStranger(e.getInvitorId()).sendMessage("暂不支持邀请进群");
            Log.WriteLog(Log.Level.Info,
                    "New group invite request from: " + e.getGroupId() +" ignored: Plugin disabled. ",
                    Log.Module.PluginMain,
                    PluginName);
            return;
        }
        e.accept();
    }
    public static void Main(BotJoinGroupEvent e) {
        if (!Enabled) {
            e.getGroup().sendMessage("暂不支持邀请入群");
            e.getGroup().quit();
        }
        MessageChainBuilder WelcomeMessage = new MessageChainBuilder();
        WelcomeMessage.append("欢迎使用容小狸Bot！\n");
        WelcomeMessage.append("目前主要功能：\n");
        WelcomeMessage.append("涩图，命令：setu [Keyword1] [keyword2] ...\n");
        e.getGroup().sendMessage(WelcomeMessage.build());
    }
}
