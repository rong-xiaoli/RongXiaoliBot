package com.rongxiaoli.module.PokeAction;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.PokeAction.backend.Poke;
import com.rongxiaoli.module.PokeAction.backend.Talk;
import net.mamoe.mirai.event.events.NudgeEvent;

public class Action {

    public void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID, int ReplyType){
        switch (ReplyType){
            default:
                Log.Exception(new IndexOutOfBoundsException("Expected rand to give out numbers from 0 to 1, but given " + ReplyType), "", Log.LogClass.Data, "PokeAction");
            case 0:
                Talk.Main(e, TargetID, BotID, IsGroup, GroupID);
                break;
            case 1:
                Poke.Main(e, TargetID, BotID, IsGroup, GroupID);
                break;
        }
    }
}