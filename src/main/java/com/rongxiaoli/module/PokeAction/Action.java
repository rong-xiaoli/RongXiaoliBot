package com.rongxiaoli.module.PokeAction;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.PokeAction.backend.Poke;
import com.rongxiaoli.module.PokeAction.backend.Talk;
import net.mamoe.mirai.event.events.NudgeEvent;

import java.util.Random;

public class Action {
    private Random Rand;

    /**
     * Initiate.
     */
    public Action(long seed){
        Rand = new Random(seed);
        Log.WriteLog(Log.Level.Verbose, "New seed of random is: " + seed, Log.LogClass.ModuleMain, "PokeAction");
    }

    public void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID){
        Poke.Main(e, TargetID, BotID, IsGroup, GroupID);
        return;
//        switch (Rand.nextInt(0,1)){
//            case 0:
//                Talk.Main();
//                break;
//            case 1:
//                Poke.Main(e, TargetID, BotID, IsGroup, GroupID);
//                break;
//            default:
//        }
    }
}