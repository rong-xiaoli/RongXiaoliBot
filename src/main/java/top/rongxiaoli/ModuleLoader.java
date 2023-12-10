package top.rongxiaoli;

import top.rongxiaoli.backend.JSONHelper;
import top.rongxiaoli.backend.Log;
import top.rongxiaoli.data.DataBaseClass;
import top.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import top.rongxiaoli.module.Baltop.Baltop;
import top.rongxiaoli.module.BotCommand.BotCommand;
import top.rongxiaoli.module.Broadcast.Broadcast;
import top.rongxiaoli.module.DailySign.DailySign;
import top.rongxiaoli.module.EmergencyStop.EmergencyStop;
import top.rongxiaoli.module.FortuneToday.FortuneToday;
import top.rongxiaoli.module.Lottery.Lottery;
import top.rongxiaoli.module.Ping.Ping;
import top.rongxiaoli.module.PokeAction.PokeAction;
import top.rongxiaoli.module.Repeater.Repeater;
import top.rongxiaoli.module.setu.PicturePlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    public DataBaseClass DataBase;
    public List<Module> ModuleList;

    public ModuleLoader() {
        ModuleList = new ArrayList<>();
        DataBase = new DataBaseClass();

        // DAMN This shit...
        // Add every module into the array... What's wrong with it...
        ModuleList.add(new EmergencyStop());
        ModuleList.add(new BotCommand());
        ModuleList.add(new Broadcast());
        ModuleList.add(new AutoAcceptPlugin());
        ModuleList.add(new PicturePlugin());
        ModuleList.add(new DailySign());
        ModuleList.add(new PokeAction());
        ModuleList.add(new Ping());
        ModuleList.add(new Repeater());
        ModuleList.add(new FortuneToday());
        ModuleList.add(new Lottery());
        ModuleList.add(new Baltop());
    }

    public void ModuleInit() {
        for (Module SingleModule :
                ModuleList) {
            SingleModule.Init();
        }
    }

    public void ModuleShutdown() {
        for (Module module :
                RongXiaoliBot.BotModuleLoader.ModuleList) {
            module.Shutdown();
        }
    }

    /**
     * This function is used to initiate the data of the whole bot.
     */
    public void DataInit() {
        JSONHelper helper = new JSONHelper("[ModuleLoader]");
        helper.filePath = RongXiaoliBot.DataPath.toString() + "/data/Data.json";
        try {
            helper.JSONRead(DataBaseClass.class);
            DataBase = (DataBaseClass) helper.jsonObject;
        } catch (IOException IOE) {
            Log.Exception(IOE, "Unexpected IOException occurred. ", Log.LogClass.File, "RongXiaoliBot");
            Log.WriteLog(Log.Level.Error, "The whole plugin will be run in memory mode due to an IOException!!! ", Log.LogClass.File, "RongXiaoliBot");
        }
    }

    /**
     * This function is used to save data for the whole bot.
     */
    public void DataSave() {
        JSONHelper helper = new JSONHelper("[ModuleLoader]");
        helper.jsonObject = DataBase;
        helper.filePath = RongXiaoliBot.DataPath.toString() + "/data/Data.json";
        try {
            helper.JSONSave();
        } catch (IOException e) {
            Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, "RongXiaoliBot");
            Log.WriteLog(Log.Level.Error, "RongXiaoliBot cannot save data file due to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, "RongXiaoliBot");
        }
    }
}
