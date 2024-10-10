package com.rongxiaoli;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import org.jetbrains.annotations.NotNull;

public class PluginListener extends SimpleListenerHost {
    public MessageProcessor MP;

    public PluginListener() {
        MP = new MessageProcessor();
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        exception.printStackTrace();
    }

    @EventHandler
    public void onPoke(NudgeEvent e) {
        MP.PokeProcess(e);
    }

    @EventHandler
    public void onFriendMessage(FriendMessageEvent e) {
        MP.FriendMessageProcess(e.getMessage().contentToString(), e);
    }

    @EventHandler
    public void onGroupMessage(GroupMessageEvent e) {
        MP.GroupMessageProcess(e.getMessage().contentToString(), e);
    }

    @EventHandler
    public void onFriendAddRequest(NewFriendRequestEvent e) {
        MP.FriendAddRequestProcess(e);
    }

    @EventHandler
    public void onFriendAdd(FriendAddEvent e) {
        MP.FriendAddProcess(e);
    }

    @EventHandler
    public void onGroupAddRequest(BotInvitedJoinGroupRequestEvent e) {
        MP.GroupAddRequestProcess(e);
    }

    @EventHandler
    public void onGroupAdd(BotJoinGroupEvent e) {
        MP.GroupAddProcess(e);
    }
}
