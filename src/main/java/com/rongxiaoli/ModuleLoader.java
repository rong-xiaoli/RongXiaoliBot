package com.rongxiaoli;

import com.google.gson.Gson;
import com.rongxiaoli.backend.JSONHelper;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    //public Module[] ModuleList;

    public List<Module> ModuleList;
    public UserData userData;
    public void ModuleInit(){
        for (Module SingleModule :
                ModuleList) {
            SingleModule.Init();
        }
    }

    /**
     * This function is used to initiate the data of the whole bot.
     */
    public void DataInit() {
        JSONHelper helper = new JSONHelper();
        helper.filePath = RongXiaoliBot.DataPath.toString() + "data";
        try {
            helper.JSONRead(UserData.class);
            userData = (UserData) helper.jsonObject;
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
        helper.jsonObject = userData;
        try {
            helper.JSONSave();
        } catch (IOException e) {
            Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, "RongXiaoliBot");
            Log.WriteLog(Log.Level.Error, "RongXiaoliBot cannot save data file due to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, "RongXiaoliBot");
        }
    }
    public ModuleLoader(){
        ModuleList = new ArrayList<Module>();
        userData = new UserData();
    }
}
