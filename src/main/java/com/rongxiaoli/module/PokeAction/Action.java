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
    public Action(){
        Rand = new Random();
        long newSeed = Rand.nextLong();
        Rand.setSeed(newSeed);
        Log.WriteLog(Log.Level.Verbose, "New seed of random is: " + newSeed, Log.LogClass.ModuleMain, "PokeAction");
    }

    public void Main(){
        switch (Rand.nextInt(0,1)){
            case 0:
                Talk.Main();
                break;
            case 1:
                Poke.Main();
                break;
            default:
        }
    }
}