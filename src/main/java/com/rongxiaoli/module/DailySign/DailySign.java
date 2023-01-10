package com.rongxiaoli.module.DailySign;

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
            "每日签到\n";
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    // Private vars.
    private final String JSONFilePath = RongXiaoliBot.DataPath.toString() + "/DailySign/DailySignData.json";
    private final String Command = "/sign";
    private Timer DailyRefreshTimer;
    private DateRefresher refresher;
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
        // Init DateRefresher.
        refresher = new DateRefresher();
        refresher.start();
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
        // Remove empty spaces.
        String[] message = arrCommand.clone();
        List<String> emptyStringRemover = Arrays.asList(message);
        emptyStringRemover.removeAll(Arrays.asList(""));
        message = emptyStringRemover.toArray(new String[0]);

        //0-width.
        if (message.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (!message[0].equals(Command)) return;
        //Process start.
        boolean isNew = false;
        GregorianCalendar presentTime = new GregorianCalendar();
        User friendUser = signInData.UserList.get(Friend);
        SignString str = new SignString();
        //Null judgement.
        if (friendUser == null) {
            isNew = true;
            friendUser = new User(Friend);
            signInData.UserList.put(Friend, friendUser);
        }
        
        int lastYear = friendUser.getDateLastSignIn().get(Calendar.YEAR),
                lastMonth = friendUser.getDateLastSignIn().get(Calendar.MONTH),
                lastDay = friendUser.getDateLastSignIn().get(Calendar.DAY_OF_MONTH);
        int year = presentTime.get(Calendar.YEAR),
                month = presentTime.get(Calendar.MONTH),
                day = presentTime.get(Calendar.DAY_OF_MONTH);
        if (year == lastYear && month == lastMonth && day == lastDay && !isNew) {
            //Already signed.
            MessageChainBuilder builder = new MessageChainBuilder();
            builder.append("您已经签到过了哦~\n");
            builder.append(str.GetRandomString(
                    presentTime.get(Calendar.YEAR),
                    presentTime.get(Calendar.MONTH) + 1,
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
        friendUser.refreshDateLastSignIn();
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
        if (status) {
            this.Init();
        } else {
            this.Shutdown();
        }
        this.IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return DebugMode;
    }

    private class DateRefresher extends Thread {
        private int dayOfYear = -1;
        @Override
        public void run() {
            GregorianCalendar gc;
            while (isEnabled()) {
                try {
                    Thread.sleep(1000);
                    gc = new GregorianCalendar();
                    if (gc.get(Calendar.DAY_OF_YEAR) != dayOfYear) {
                        dayOfYear = gc.get(Calendar.DAY_OF_YEAR);
                        Log.WriteLog(Log.Level.Verbose, "DailyRefresher start. ", Log.LogClass.Multithreading, PluginName);
                        SignInPosition = 1;
                        try {
                            json.JSONSave();
                        } catch (IOException e) {
                            Log.Exception(e, "Unexpected IOError occurred. ", Log.LogClass.File, PluginName);
                            Log.WriteLog(Log.Level.Error, "DailySign cannot save file do to unexpected IOException. Quitting without saving data!!! ", Log.LogClass.ModuleMain, PluginName);
                        }
                        // Done.
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
