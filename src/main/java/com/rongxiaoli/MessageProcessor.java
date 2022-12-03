package com.rongxiaoli;

import com.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.module.Broadcast.Broadcast;
import com.rongxiaoli.module.DailySign.DailySign;
import com.rongxiaoli.module.EmergencyStop.EmergencyStop;
import com.rongxiaoli.module.BotCommand.BotCommand;
import com.rongxiaoli.module.Picture.PicturePlugin;
import com.rongxiaoli.module.PokeAction.PokeAction;
import net.mamoe.mirai.event.events.*;

public class MessageProcessor {

    /**
     * Invoke when a friend message is sent.
     * @param originalMessage Original message.
     * @param e FriendMessageEvent.
     */
    public void FriendMessageProcess(String originalMessage, FriendMessageEvent e) {
        //Variables initiate.
        String[] arrCommand;
        //Start processing.
        arrCommand = originalMessage.split(" ");
        //Invoke plugin main methods.
        //First step.

        EmergencyStop.UnregisteredFriendMain(arrCommand, e.getSubject());
        Broadcast.UnregisteredFriendMain(arrCommand, e.getSubject());
        BotCommand.UnregisteredFriendMain(arrCommand, e.getSubject());

        //Judge if the plugin is running or not.
        if (!RongXiaoliBot.IsEnabled) {
            return;
        }

        //Second step.
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            SingleModule.FriendMain(arrCommand,e.getSubject().getId(),e.getSender());
        }
    }

    /**
     * Invoke when a group message is sent.
     * @param originalMessage Original message.
     * @param e GroupMessageEvent.
     */
    public void GroupMessageProcess(String originalMessage, GroupMessageEvent e) {
        //Variables initiate.
        String[] arrCommand;
        //Start processing.
        arrCommand = originalMessage.split(" ");
        //Invoke plugin main methods.
        //First step: unregistered modules.

        //BotCommand.UnregisteredFriendMain(arrCommand, e.getSubject());

        //Judge if the plugin is running or not.
        if (!RongXiaoliBot.IsEnabled) {
            return;
        }
        //Second step: registered modules.
        for (Module SingleModule:
                RongXiaoliBot.BotModuleLoader.ModuleList
             ) {
            SingleModule.GroupMain(arrCommand, e.getSender().getId(), e.getSubject().getId(), e.getSubject());
        }
    }

    /**
     * Invoke when a friend request is sent.
     * @param e Friend request event.
     */
    public void FriendAddRequestProcess(NewFriendRequestEvent e) {
        AutoAcceptPlugin AA = null;
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            if (SingleModule.getPluginName().equals("AutoAccept")) {
                AA = (AutoAcceptPlugin) SingleModule;
            }
        }
        AA.Main(e);
    }

    /**
     * Invoke when a friend is added.
     * @param e Friend add event.
     */
    public void FriendAddProcess(FriendAddEvent e) {
        AutoAcceptPlugin AA = null;
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            if (SingleModule.getPluginName().equals("AutoAccept")) {
                AA = (AutoAcceptPlugin) SingleModule;
            }
        }
        AA.Main(e);
    }

    /**
     * Invoke when a group request is sent.
     * @param e Group request event.
     */
    public void GroupAddRequestProcess(BotInvitedJoinGroupRequestEvent e) {
        AutoAcceptPlugin AA = null;
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            if (SingleModule.getPluginName().equals("AutoAccept")) {
                AA = (AutoAcceptPlugin) SingleModule;
            }
        }
        AA.Main(e);
    }
    /**
     * Invoke when added in a group.
     */
    public void GroupAddProcess(BotJoinGroupEvent e) {
        AutoAcceptPlugin AA = null;
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            if (SingleModule.getPluginName().equals("AutoAccept")) {
                AA = (AutoAcceptPlugin) SingleModule;
            }
        }
        AA.Main(e);
    }

    public void PokeProcess(NudgeEvent e){
        PokeAction PA = null;
        for (Module SingleModule :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            if (SingleModule.getPluginName().equals("PokeAction")) {
                PA = (PokeAction) SingleModule;
            }
        }
        PA.PokeMain(e);
    }
}
