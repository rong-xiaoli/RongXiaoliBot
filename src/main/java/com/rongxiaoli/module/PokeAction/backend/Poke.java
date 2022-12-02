package com.rongxiaoli.module.PokeAction.backend;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.action.FriendNudge;
import net.mamoe.mirai.message.action.MemberNudge;
import net.mamoe.mirai.message.action.Nudge;

public class Poke {
    private static void PokeBack(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID){
        if (IsGroup) {
            if (GroupID == 0){
                return;
            }
            MemberNudge memberNudge = new MemberNudge((NormalMember) e.getFrom());
            return;
        }
        Nudge n = new FriendNudge((Friend) e.getFrom());
        n.sendTo(e.getSubject());
    }
    private static void PokeOthers(){}
    public static void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID){
        PokeBack(e, TargetID, BotID, IsGroup, GroupID);
    }
}
