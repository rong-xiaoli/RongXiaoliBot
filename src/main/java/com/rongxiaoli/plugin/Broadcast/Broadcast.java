package com.rongxiaoli.plugin.Broadcast;

import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Arrays;
import java.util.Objects;

public class Broadcast {
    public static String PluginName = "Broadcast";

    public static boolean Enabled = true;
    public static void Main(String[] arrCommand, Contact SenderContact) {
        if (arrCommand.length == 0) {
            return;
        }
        StringBuilder BroadcastMessageBuilder;
        if (SenderContact.getId() == RongXiaoliBot.Owner) {
            if (Objects.equals(arrCommand[0], "广播")) {
                if (!Enabled) {
                    SenderContact.sendMessage("功能未启用");
                    return;
                }
                Log.WriteLog(Log.Level.Info,
                        "Received broadcast message from bot owner:" + RongXiaoliBot.Owner + Arrays.toString(arrCommand),
                        Log.Module.PluginMain,
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
                for (Friend SingleFriend :
                        FriendsList) {
                    SingleFriend.sendMessage(BroadcastMessage.build());
                    Log.WriteLog(Log.Level.Verbose,
                            "Send to Friend: " + SingleFriend.getId(),
                            Log.Module.PluginMain,
                            PluginName);
                }
                for (Group SingleGroup :
                        GroupList) {
                    SingleGroup.sendMessage(BroadcastMessage.build());
                    Log.WriteLog(Log.Level.Verbose,
                            "Send to Group: " + SingleGroup.getId(),
                            Log.Module.PluginMain,
                            PluginName);
                }
            }
        }
    }
}
