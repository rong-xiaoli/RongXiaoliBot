package com.rongxiaoli.module.DailySign;
//Todo: Finish this module.
import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.JSONHelper;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInData;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignString;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.User;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.*;
import java.time.DayOfWeek;
import java.util.*;

public class DailySign extends Module {
    // Public vars.
    private final String PluginName = "DailySign";
    private final String HelpContent = "DailySign\n" +
            "每日签到";
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    // Private vars.
    private String JSONFilePath = RongXiaoliBot.DataPath.toString() + "/DailySign/DailySignData.json";
    private String Command = "/sign";
    private Timer DailyRefreshTimer;
    private SignInData signInData;
    private JSONHelper json;
    private long SignInPosition = 1;
    //Vars def finish.

    /**
     * Module initiate function.
     */
    public void Init() {
        // Ready to read JSON.
        json = new JSONHelper();
        json.filePath = JSONFilePath;

        // Init signInData.
        try {
            json.JSONRead(SignInData.class);
            signInData = (SignInData) json.jsonObject;
        } catch (IOException IOE) {
            Log.Exception(IOE, "Unexpected IOException. ", Log.LogClass.File, PluginName);
            Log.WriteLog(Log.Level.Error, "DailySign will be run in memory mode due to an IOException. ", Log.LogClass.File, PluginName);
        }
        if (signInData == null) {
            signInData = new SignInData();
            signInData.UserList = new HashMap<>();
        }
        //Init DailyRefreshTimer.
        DailyRefreshTimer = new Timer();
        GregorianCalendar scheduleTime = new GregorianCalendar();
        scheduleTime.set(Calendar.HOUR, 0);
        scheduleTime.set(Calendar.MINUTE, 0);
        scheduleTime.set(Calendar.SECOND, 0);
        scheduleTime.set(Calendar.MILLISECOND, 0);
        scheduleTime.add(Calendar.DAY_OF_MONTH, 1);
        DailyRefreshTimer.schedule(new DailyRefresher(), scheduleTime.getTime(),86400000);


        // Done.
        Log.WriteLog(Log.Level.Debug,
                "DailySign initiated! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        json.jsonObject = signInData;
        try {
            json.JSONSave();
        } catch (IOException e) {
            Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, PluginName);
            Log.WriteLog(Log.Level.Error, "DailySign cannot save file do to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, PluginName);
        }
        IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "DailySign shut down. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        //0-width.
        if (arrCommand.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (!arrCommand[0].equals(Command)) return;
        //Process start.
        boolean isNew = false;
        GregorianCalendar presentTime = new GregorianCalendar();
        Date presentDate = presentTime.getTime();
        User friendUser = signInData.UserList.get(Friend);
        SignString str = new SignString();
        //Null judgement.
        if (friendUser == null) {
            isNew = true;
            friendUser = new User(Friend);
            signInData.UserList.put(Friend, friendUser);
        }
        int year = friendUser.getDateLastSignIn().get(Calendar.YEAR),
                month = friendUser.getDateLastSignIn().get(Calendar.MONTH),
                day = friendUser.getDateLastSignIn().get(Calendar.DAY_OF_MONTH);
        if (year == presentTime.get(Calendar.YEAR) && month == presentTime.get(Calendar.MONTH) && day == presentTime.get(Calendar.DAY_OF_MONTH) && !isNew) {
            //Already signed.
            MessageChainBuilder builder = new MessageChainBuilder();
            builder.append("您已经签到过了哦~\n");
            builder.append(str.GetRandomString(
                    presentTime.get(Calendar.YEAR),
                    presentTime.get(Calendar.MONTH),
                    presentTime.get(Calendar.DAY_OF_MONTH),
                    DayOfWeek.of(presentTime.get(Calendar.DAY_OF_WEEK)),
                    presentTime.get(Calendar.HOUR_OF_DAY),
                    presentTime.get(Calendar.MINUTE),
                    presentTime.get(Calendar.SECOND),
                    presentTime.get(Calendar.MILLISECOND)));
            SubjectContact.sendMessage(builder.build());
            return;
        }
        friendUser.giveCoin(1);
        signInData.UserList.replace(Friend,friendUser);
        MessageChainBuilder builder = new MessageChainBuilder();
        builder.append(str.FriendString(presentTime, SignInPosition));
        builder.append("\n现在有").append(String.valueOf(friendUser.getCoin())).append("枚金币");
        SubjectContact.sendMessage(builder.build());
        SignInPosition++;
    }

    /**
     * Group message process.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SubjectContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
        FriendMain(arrCommand, Friend, SubjectContact);
    }

    /**
     * Plugin name. Use in logs.
     */
    public String getPluginName() {
        return PluginName;
    }

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String getHelpContent() {
        return HelpContent;
    }

    /**
     * True if enabled.
     */
    public boolean isEnabled() {
        return this.IsEnabled;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        this.IsEnabled = isEnabled();
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return DebugMode;
    }

    private class DailyRefresher extends TimerTask {
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            //Todo: Finish this.
            Log.WriteLog(Log.Level.Verbose, "DailyRefresher start. ", Log.LogClass.Multithreading, PluginName);
            SignInPosition = 1;
            try {
                json.JSONSave();
            } catch (IOException e) {
                Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, PluginName);
                Log.WriteLog(Log.Level.Error, "DailySign cannot save file do to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, PluginName);
            }
        }
    }
}
