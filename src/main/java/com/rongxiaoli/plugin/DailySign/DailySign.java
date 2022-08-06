package com.rongxiaoli.plugin.DailySign;

import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.JSONFile;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.TimerExecute;
import com.rongxiaoli.plugin.DailySign.ModuleBackend.CustomString.CustomStringList;
import com.rongxiaoli.plugin.DailySign.ModuleBackend.SignIn.SignObjectList;
import com.rongxiaoli.plugin.DailySign.ModuleBackend.SignIn.SignInRequestProcess;
import net.mamoe.mirai.contact.Contact;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DailySign {
    public static SignObjectList SignList = new SignObjectList();
    public static CustomStringList StringList = new CustomStringList();
    public static DateChecker DChecker = new DateChecker();
    private static JSONFile SignInDataJSONFile;
    private static JSONFile CustomStringDataJSONFile;

    public static String PluginName = "DailySign";
    public static boolean Enabled = true;
    public static void Main(String[] arrCommand, long QQID, long GroupID, Contact SenderContact) {
        //Judge if command is 0-width.
        if (arrCommand.length == 0) {
            return;
        }

        //Start processing.
        SignInRequestProcess.Process(arrCommand,QQID,GroupID,SenderContact);
//        CustomStringOperationRequestProcess.Process(arrCommand, QQID, GroupID, SenderContact);
    }
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
        }
    }

    /**
     * Module init func.
     */
    public static void Init() {
        SignInDataJSONFile = new JSONFile(PluginName, RongXiaoliBot.ConfigPath.toString(),"/DailySign/SignData.json");
        SignInDataJSONFile.JObject = new SignObjectList();
        SignInDataJSONFile.readFile();
        SignInDataJSONFile.JObject = SignInDataJSONFile.toObject();
        DailySign.SignList = (SignObjectList) SignInDataJSONFile.JObject;

        CustomStringDataJSONFile = new JSONFile(PluginName, RongXiaoliBot.ConfigPath.toString(),"/DailySign/CustomStringData.json");
        CustomStringDataJSONFile.JObject = new CustomStringList();
        CustomStringDataJSONFile.readFile();
        CustomStringDataJSONFile.JObject = CustomStringDataJSONFile.toObject();
        StringList = (CustomStringList) CustomStringDataJSONFile.JObject;
        Log.WriteLog(Log.Level.Verbose,
                "DailySign JSON file readed. ",
                Log.Module.PluginMain,
                PluginName);

        DChecker.run(null);
        Log.WriteLog(Log.Level.Verbose,
                "DailySign DateChecker ready. ",
                Log.Module.PluginMain,
                PluginName);
        Log.WriteLog(Log.Level.Debug,
                "DailySign plugin initiated. ",
                Log.Module.PluginMain,
                PluginName);
    }

    /**
     * Module shutdown func.
     */
    public static void Shutdown() {
        SignInDataJSONFile.JString = null;
        SignInDataJSONFile.JString = new StringBuilder();
        SignInDataJSONFile.JString.append(SignInDataJSONFile.toString());
        SignInDataJSONFile.writeFile();

        CustomStringDataJSONFile.JString = null;
        CustomStringDataJSONFile.JString = new StringBuilder();
        CustomStringDataJSONFile.JString.append(CustomStringDataJSONFile.toString());
        CustomStringDataJSONFile.writeFile();
        Log.WriteLog(Log.Level.Debug,
                "DailySign JSONData saved! ",
                Log.Module.PluginMain,
                PluginName);
    }
}
