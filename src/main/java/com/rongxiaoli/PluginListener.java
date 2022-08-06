package com.rongxiaoli;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import org.jetbrains.annotations.NotNull;

public class PluginListener extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        exception.printStackTrace();
    }
//    @Deprecated
//    @EventHandler
//    public void onMessage(MessageEvent e) {
//        MessageProcessor.MessageProcess(e.getMessage().contentToString(),e);
//    }
    @EventHandler
    public void onFriendMessage(FriendMessageEvent e) {
        MessageProcessor.FriendMessageProcess(e.getMessage().contentToString(), e);
    }
    @EventHandler
    public void onGroupMessage(GroupMessageEvent e) {
        MessageProcessor.GroupMessageProcess(e.getMessage().contentToString(), e);
    }
    @EventHandler
    public void onFriendAddRequest(NewFriendRequestEvent e) {
        MessageProcessor.FriendAddRequestProcess(e);
    }
    @EventHandler
    public void onFriendAdd(FriendAddEvent e) {
        MessageProcessor.FriendAddProcess(e);
    }
    @EventHandler
    public void onGroupAddRequest(BotInvitedJoinGroupRequestEvent e) {
        MessageProcessor.GroupAddRequestProcess(e);
    }
    @EventHandler
    public void onGroupAdd(BotJoinGroupEvent e) {
        MessageProcessor.GroupAddProcess(e);
    }
}
