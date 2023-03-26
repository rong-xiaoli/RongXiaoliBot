package com.rongxiaoli.module.PokeAction.backend;

import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.action.FriendNudge;
import net.mamoe.mirai.message.action.Nudge;

import java.util.Random;

public class Poke {
    /**
     * Poke back to the object.
     *
     * @param e        Nudge event.
     * @param TargetID The target to be poked.
     * @param BotID    Bot ID.
     * @param IsGroup  True if the event happened in group.
     * @param GroupID  Group ID. Zero if not a group.
     */
    private static void PokeBack(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID) {
        if (IsGroup) {
            if (GroupID == 0) {
                return;
            }
            Group group = (Group) e.getSubject();
            group.sendMessage(generatePokeMessage());
            Log.WriteLog(Log.Level.Verbose, "Try to nudge " + e.getFrom().getId(), Log.LogClass.ModuleMain, "PokeAction");
            e.getFrom().nudge().sendTo(e.getSubject());
            return;
        }
        Friend friend = (Friend) e.getFrom();
        friend.sendMessage(generatePokeMessage());
        Nudge n = new FriendNudge((Friend) e.getFrom());
        n.sendTo(e.getSubject());
    }

    private static void PokeOthers() {
        //Todo: Complete this method.
    }

    private static String generatePokeMessage() {
        Random r = new Random();
        int t = r.nextInt(3);
        switch (t) {
            case 0:
                return "看我戳回去！";
            case 1:
                return "就你会戳吗？";
            default:
                Log.WriteLog(Log.Level.Warning,
                        "Except a number from 0 to 2, received " + t,
                        Log.LogClass.ModuleMain,
                        "PokeAction");
            case 2:
                return "我要戳疼你！";
        }
    }

    public static void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID) {
        PokeBack(e, TargetID, BotID, IsGroup, GroupID);
    }
}
