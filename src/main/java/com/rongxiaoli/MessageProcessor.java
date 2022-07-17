package com.rongxiaoli;

import com.rongxiaoli.plugin.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.plugin.Broadcast.Broadcast;
import com.rongxiaoli.plugin.EmergencyStop.EmergencyStop;
import com.rongxiaoli.plugin.BotCommand.BotCommand;
import com.rongxiaoli.plugin.Picture.PicturePlugin;
import net.mamoe.mirai.event.events.*;

public class MessageProcessor {
    public static void MessageProcess(String originalMessage, MessageEvent e) {
        //Variables initiate.
        String[] arrCommand;
        //Start processing.
        arrCommand = originalMessage.split(" ");
        //Invoke plugin main methods.
        //First step.
        EmergencyStop.Main(arrCommand, e.getSubject());
        BotCommand.Main(arrCommand, e.getSubject());
        //Judge if the plugin is running or not.
        if (!RongXiaoliBot.isPluginRunning) {
            return;
        }
        //Second step.
        Broadcast.Main(arrCommand, e.getSubject());
        PicturePlugin.Main(arrCommand, e.getSubject().getId(),e.getSender().getId(),e.getSubject());
    }

    /**
     * Invoke when a friend request is sent.
     * @param e Friend request event.
     */
    public static void FriendAddRequestProcess(NewFriendRequestEvent e) {
        AutoAcceptPlugin.Main(e);
    }

    /**
     * Invoke when a friend is added.
     * @param e Friend add event.
     */
    public static void FriendAddProcess(FriendAddEvent e) {
        AutoAcceptPlugin.Main(e);
    }

    /**
     * Invoke when a group request is sent.
     * @param e Group request event.
     */
    public static void GroupAddRequestProcess(BotInvitedJoinGroupRequestEvent e) {
        AutoAcceptPlugin.Main(e);
    }
    /**
     * Invoke when added in a group.
     */
    public static void GroupAddProcess(BotJoinGroupEvent e) {
        AutoAcceptPlugin.Main(e);
    }
}
