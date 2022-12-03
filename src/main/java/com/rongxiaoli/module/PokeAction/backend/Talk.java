package com.rongxiaoli.module.PokeAction.backend;

import com.rongxiaoli.backend.Log;

import java.util.Random;

public class Talk {
    private static String Generate() {
        Random rand = new Random();
        /*
        0: Moe
        1:

         */
        int type = rand.nextInt(0, 1);
        int number = rand.nextInt(0, 3);
        switch (type) {
            case 0:
                switch (number) {
                    default:
                        Log.Exception(new IndexOutOfBoundsException("Expected rand return a number from 0 to 1, but rand returned" + number), "", Log.LogClass.Data, "PokeAction");
                    case 0:
                        return "awa";
                    case 1:
                        return "QwQ";
                    case 2:
                        return "OvO";
                    case 3:
                        return "-ω-";
                }
            case 1:
                switch (number){
                    default:
                        Log.Exception(new IndexOutOfBoundsException("Expected rand return a number from 0 to 1, but rand returned" + number), "", Log.LogClass.Data, "PokeAction");
                    case 0:
                        return "别戳啦～(抱头蹲防)";
                    case 1:
                        return "别戳了别戳了";
                }
            default:
                Log.Exception(new IndexOutOfBoundsException("Expected rand return a number from 0 to 1, but rand returned" + type), "", Log.LogClass.Data, "PokeAction");
                return "你干吗～哈哈，哎呦";
        }
    }
    public static void Main(){}
}
