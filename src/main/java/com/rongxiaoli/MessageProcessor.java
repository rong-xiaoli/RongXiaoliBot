package com.rongxiaoli;

import com.rongxiaoli.plugin.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.plugin.Broadcast.Broadcast;
import com.rongxiaoli.plugin.DailySign.DailySign;
import com.rongxiaoli.plugin.EmergencyStop.EmergencyStop;
import com.rongxiaoli.plugin.BotCommand.BotCommand;
import com.rongxiaoli.plugin.Picture.PicturePlugin;
import net.mamoe.mirai.event.events.*;

public class MessageProcessor {

    /**
     * Invoke when a friend message is sent.
     * @param originalMessage Original message.
     * @param e FriendMessageEvent.
     */
    public static void FriendMessageProcess(String originalMessage, FriendMessageEvent e) {
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
        //Plugin name: broadcast.
        Broadcast.Main(arrCommand, e.getSubject());

        //Version 0.1.0 removed:
        //Reason: After being banned for many times, this function is banned forever for others.
        //Plugin name: setu.
        PicturePlugin.Main(arrCommand, e.getSubject().getId(),e.getSubject());

        //Plugin name: dailysign.
        DailySign.Main(arrCommand,e.getSubject().getId(),0,e.getSubject());
    }

    /**
     * Invoke when a group message is sent.
     * @param originalMessage Original message.
     * @param e GroupMessageEvent.
     */
    public static void GroupMessageProcess(String originalMessage, GroupMessageEvent e) {
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

        //Version 0.1.0 removed:
        //Reason: After being banned for many times, this function is banned forever for others.
        //Plugin name: setu.
        PicturePlugin.Main(arrCommand, e.getSender().getId(),e.getSubject().getId(),e.getSubject());

        //Plugin name: dailysign.
        DailySign.Main(arrCommand, e.getSender().getId(), e.getSubject().getId(),e.getSubject());
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
