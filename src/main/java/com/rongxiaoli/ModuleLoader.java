package com.rongxiaoli;

import com.rongxiaoli.backend.JSONHelper;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.DataBaseClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    public DataBaseClass DataBase;
    public List<Module> ModuleList;

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

    public ModuleLoader() {
        ModuleList = new ArrayList<>();
        DataBase = new DataBaseClass();
    }

    /**
     * This function is used to initiate the data of the whole bot.
     */
    public void DataInit() {
        JSONHelper helper = new JSONHelper();
        helper.filePath = RongXiaoliBot.DataPath.toString() + "data";
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
        JSONHelper helper = new JSONHelper();
        helper.jsonObject = DataBase;
        try {
            helper.JSONSave();
        } catch (IOException e) {
            Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, "RongXiaoliBot");
            Log.WriteLog(Log.Level.Error, "RongXiaoliBot cannot save data file due to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, "RongXiaoliBot");
        }
    }
}
