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
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Broadcast extends Module {
    public static final String PluginName = "Broadcast";
    //"广播消息至所有联系人。（仅限号主可用）\n";
    private static boolean IsEnabled = true;
    public final String HelpContent = "";//"/broadcast (message) +\n" +

    public void Init() {
        Log.WriteLog(Log.Level.Debug, "Broadcast module initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void Shutdown() {
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "Broadcast module stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        // 0-width.
        if (message.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        StringBuilder BroadcastMessageBuilder;
        if (SubjectContact.getId() == RongXiaoliBot.Owner) {
            if (Objects.equals(message[0], "/broadcast")) {
                if (!IsEnabled) {
                    SubjectContact.sendMessage("功能未启用");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Received broadcast message from bot owner:" + RongXiaoliBot.Owner + Arrays.toString(message),
                        Log.LogClass.ModuleMain,
                        PluginName);
                //Process.
                BroadcastMessageBuilder = new StringBuilder();
                for (int num = 1; num <= message.length - 1; num++) {
                    BroadcastMessageBuilder.append(message[num]);
                }
                MessageChainBuilder BroadcastMessage = new MessageChainBuilder();
                BroadcastMessage.append("来自主人的消息：\n");
                BroadcastMessage.append(BroadcastMessageBuilder.toString());
                ContactList<Friend> FriendsList = SubjectContact.getBot().getFriends();
                ContactList<Group> GroupList = SubjectContact.getBot().getGroups();
                Random ran = new Random();
                for (Friend SingleFriend :
                        FriendsList) {
                    SingleFriend.sendMessage(BroadcastMessage.build());
                    Log.WriteLog(Log.Level.Verbose,
                            "Send to Friend: " + SingleFriend.getId(),
                            Log.LogClass.ModuleMain,
                            PluginName);
                    try {
                        Thread.sleep(ran.nextInt(100, 3000));
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
                        Thread.sleep(ran.nextInt(100, 3000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
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
