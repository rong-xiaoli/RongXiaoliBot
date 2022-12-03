package com.rongxiaoli.module.PokeAction.backend;

import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.action.FriendNudge;
import net.mamoe.mirai.message.action.MemberNudge;
import net.mamoe.mirai.message.action.Nudge;

public class Poke {
    /**
     * Poke back to the object.
     * @param e
     * @param TargetID
     * @param BotID
     * @param IsGroup
     * @param GroupID
     */
    private static void PokeBack(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID){
        if (IsGroup) {
            if (GroupID == 0){
                return;
            }
            Group group = (Group) e.getSubject();
            group.sendMessage("就你会戳吗？");
            Log.WriteLog(Log.Level.Verbose, "Try to nudge " + e.getFrom().getId(), Log.LogClass.ModuleMain, "PokeAction");
            e.getFrom().nudge().sendTo(e.getSubject());
            return;
        }
        Friend friend = (Friend) e.getFrom();
        friend.sendMessage("就你会戳吗？");
        Nudge n = new FriendNudge((Friend) e.getFrom());
        n.sendTo(e.getSubject());
    }
    private static void PokeOthers(){
        //Todo: Complete this method.
    }
    public static void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID){
        PokeBack(e, TargetID, BotID, IsGroup, GroupID);
    }
}
