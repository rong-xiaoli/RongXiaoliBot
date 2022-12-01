package com.rongxiaoli.module.DailySign;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.JSONFile;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.TimerExecute;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.Data;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignObjectList;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInRequestProcess;
import net.mamoe.mirai.contact.Contact;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DailySign extends Module {
    public static Data SignList = new Data();
    public static DateChecker DChecker = new DateChecker();
    private static JSONFile SignInDataJSONFile;
    public static final String HelpContent = "Rsign\n" +
            "签到";

    public static String PluginName = "DailySign";
    public static boolean Enabled = false;
    public static class DateChecker extends TimerExecute {
        public static int Month;
        public static int Day;

        public DateChecker() {
            super(DailySign.PluginName);
        }

        @Override
        public void run(@Nullable Object args) {
            GregorianCalendar GC = new GregorianCalendar();
            if (Month != GC.get(Calendar.MONTH) || Day != GC.get(Calendar.DAY_OF_MONTH)) {
                if (SignList == null) {
                    Month = GC.get(Calendar.MONTH);
                    Day = GC.get(Calendar.DAY_OF_MONTH);
                    return;
                }
                for (SignObjectList.GroupSignObject SingleObject :
                        SignList.GroupSignList) {
                    SingleObject.Position = 1;
                }
                Month = GC.get(Calendar.MONTH);
                Day = GC.get(Calendar.DAY_OF_MONTH);
            }
            SignInDataJSONFile.JObject = SignList;
            SignInDataJSONFile = new JSONFile(PluginName, RongXiaoliBot.DataPath.toString(),"/DailySign/SignData.json");
            SignInDataJSONFile.writeFile();
        }
    }

    /**
     * Module init func.
     */
    public void Init() {
        SignInDataJSONFile = new JSONFile(PluginName, RongXiaoliBot.DataPath.toString(),"/DailySign/SignData.json");
        SignInDataJSONFile.JObject = new Data();
        SignInDataJSONFile.readFile();
        SignInDataJSONFile.JObject = SignInDataJSONFile.toObject();
        DailySign.SignList = (Data) SignInDataJSONFile.JObject;

        Log.WriteLog(Log.Level.Verbose,
                "DailySign JSON file readed. ",
                Log.LogClass.ModuleMain,
                PluginName);

        DChecker.run(null);
        Log.WriteLog(Log.Level.Verbose,
                "DailySign DateChecker ready. ",
                Log.LogClass.ModuleMain,
                PluginName);

        if (DailySign.SignList == null) {
            Log.WriteLog(Log.Level.Warning,
                    "SignList is null. ",
                    Log.LogClass.ModuleMain,
                    DailySign.PluginName);
            SignList = new Data();
        }
        Log.WriteLog(Log.Level.Debug,
                "DailySign plugin initiated. ",
                Log.LogClass.ModuleMain,
                PluginName);
        Enabled = true;
    }

    /**
     * Module shutdown func.
     */
    public void Shutdown() {
        SignInDataJSONFile.JString = null;
        SignInDataJSONFile.JObject = SignList;
        SignInDataJSONFile.JString = new StringBuilder();
        SignInDataJSONFile.JString.append(SignInDataJSONFile);
        SignInDataJSONFile.writeFile();
        Log.WriteLog(Log.Level.Debug,
                "DailySign JSONData saved! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {
        //Judge if command is 0-width.
        if (arrCommand.length == 0) {
            return;
        }

        //Start processing.
        SignInRequestProcess.Process(arrCommand,Friend,0,SenderContact);
    }

    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact) {
        //Judge if command is 0-width.
        if (arrCommand.length == 0) {
            return;
        }

        //Start processing.
        SignInRequestProcess.Process(arrCommand,Friend,Group,SenderContact);
    }
}
