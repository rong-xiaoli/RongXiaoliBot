package com.rongxiaoli.module.PokeAction.backend;

import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.NudgeEvent;

import java.util.Random;

public class Talk {
    private static String Generate(int type, int number) {
        switch (type) {
            case 0:
                switch (number) {
                    default:
                        Log.Exception(new IndexOutOfBoundsException("Expected rand return a number from 0 to 4, but rand returned" + number), "", Log.LogClass.Data, "PokeAction");
                    case 0:
                        return "awa";
                    case 1:
                        return "QwQ";
                    case 2:
                        return "OvO";
                    case 3:
                        return "-ω-";
                    case 4:
                        return "oxO";
                }
            case 1:
                switch (number) {
                    default:
                        Log.WriteLog(Log.Level.Warning, "Expected number to be from 0 to 3, got " + number, Log.LogClass.Data, "PokeAction");
                    case 0:
                        return "别戳啦～(抱头蹲防)";
                    case 1:
                        return "别戳了别戳了`(*>﹏<*)′";
                    case 2:
                        return "你把我戳疼了(*。>Д<)o゜";
                    case 3:
                        return "再戳我要哭了o(TヘTo)";
                }
            default:
                Log.Exception(new IndexOutOfBoundsException("Expected rand return a number from 0 to 1, but rand returned" + type), "", Log.LogClass.Data, "PokeAction");
                return "你干吗～哈哈，哎呦";
        }
    }

    public static void Main(NudgeEvent e, long TargetID, long BotID, boolean IsGroup, long GroupID, int type, int number) {

        if (IsGroup) {
            if (GroupID == 0) {
                return;
            }
            e.getSubject().sendMessage(Generate(type, number));
            return;
        }
        Friend friend = (Friend) e.getFrom();
        friend.sendMessage(Generate(type, number));
    }
}
