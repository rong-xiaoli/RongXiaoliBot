package com.rongxiaoli.module.Broadcast;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Broadcast extends Module {
    public static String PluginName = "Broadcast";
    public String HelpContent = "/broadcast (message) +\n" +
            "广播消息至所有联系人。";
    private static boolean IsEnabled = true;
    public static void UnregisteredFriendMain(String[] arrCommand, Contact SenderContact) {
    }

    public void Init() {
        Log.WriteLog(Log.Level.Debug, "Broadcast module initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void Shutdown() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "Broadcast module stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {
        if (arrCommand.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        StringBuilder BroadcastMessageBuilder;
        if (SenderContact.getId() == RongXiaoliBot.Owner) {
            if (Objects.equals(arrCommand[0], "/broadcast")) {
                if (!IsEnabled) {
                    SenderContact.sendMessage("功能未启用");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Received broadcast message from bot owner:" + RongXiaoliBot.Owner + Arrays.toString(arrCommand),
                        Log.LogClass.ModuleMain,
                        PluginName);
                //Process.
                BroadcastMessageBuilder = new StringBuilder();
                for (int num = 1; num <= arrCommand.length - 1; num ++) {
                    BroadcastMessageBuilder.append(arrCommand[num]);
                }
                MessageChainBuilder BroadcastMessage = new MessageChainBuilder();
                BroadcastMessage.append("来自主人的消息：\n");
                BroadcastMessage.append(BroadcastMessageBuilder.toString());
                ContactList<Friend> FriendsList = SenderContact.getBot().getFriends();
                ContactList<Group> GroupList = SenderContact.getBot().getGroups();
                Random ran = new Random();
                for (Friend SingleFriend :
                        FriendsList) {
                    SingleFriend.sendMessage(BroadcastMessage.build());
                    Log.WriteLog(Log.Level.Verbose,
                            "Send to Friend: " + SingleFriend.getId(),
                            Log.LogClass.ModuleMain,
                            PluginName);
                    try {
                        Thread.sleep(ran.nextInt(100,3000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                for (Group SingleGroup :
                        GroupList) {
                    SingleGroup.sendMessage(BroadcastMessage.build());
                    Log.WriteLog(Log.Level.Verbose,
                            "Send to Group: " + SingleGroup.getId(),
                            Log.LogClass.ModuleMain,
                            PluginName);
                    try {
                        Thread.sleep(ran.nextInt(100,3000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

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
        return false;
    }
}
