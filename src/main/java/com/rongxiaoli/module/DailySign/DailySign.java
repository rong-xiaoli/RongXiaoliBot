package com.rongxiaoli.module.DailySign;

import com.google.gson.internal.LinkedTreeMap;
import com.rongxiaoli.Module;
import com.rongxiaoli.PluginListener;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.DataBlock;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInStruct;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignString;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DailySign extends Module {
    // Public vars.
    private final String PluginName = "DailySign";
    private final String HelpContent = "/sign\n" +
            "每日签到";
    // Private vars.
    private final String Command = "/sign";
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    private long SignInPosition = 1;
    private PositionRefreshThread refreshThread;
    //Vars def finish.

    /**
     * This class is used as a thread for refreshing sign in position.
     */
    private class PositionRefreshThread extends Thread {
        private LocalDate roamingDate;
        public PositionRefreshThread() {
            this.roamingDate = LocalDate.now();
        }
        private void refreshProcess() {
            if (!Objects.equals(roamingDate, LocalDate.now())) {
                roamingDate = LocalDate.now();
                Log.WriteLog(Log.Level.Verbose,
                        "Date changed. ",
                        Log.LogClass.Multithreading,
                        PluginName);
                for (Module singleModule :
                        RongXiaoliBot.BotModuleLoader.ModuleList) {
                    if (singleModule instanceof DailySign) {
                        ((DailySign) singleModule).SignInPosition = 1;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.Exception(e,
                        "DailySign refresh thread interrupted. ",
                        Log.LogClass.Multithreading,
                        PluginName);
            }
        }
        @Override
        public void run() {
            while (IsEnabled) {
                refreshProcess();
            }
        }
    }
    /**
     * Module initiate function.
     */
    public void Init() {
        refreshThread = new PositionRefreshThread();
        refreshThread.start();
        // Done.
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,
                "DailySign initiated! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
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

        UserDataOperation operation = new UserDataOperation(Friend);
        operation.signInProcess();
        SignString str = new SignString();
        if (operation.isSigned) {
            //Already signed.
            MessageChainBuilder builder = new MessageChainBuilder();
            builder.append("您已经签到过了哦~\n");
            builder.append(str.GetRandomString(
                    operation.requestDateTime.getYear(),
                    operation.requestDateTime.getMonthValue(),
                    operation.requestDateTime.getDayOfMonth(),
                    operation.requestDateTime.getDayOfWeek(),
                    operation.requestDateTime.getHour(),
                    operation.requestDateTime.getMinute(),
                    operation.requestDateTime.getSecond(),
                    operation.requestDateTime.getNano()));
            SubjectContact.sendMessage(builder.build());
            return;
        }
        MessageChainBuilder builder = new MessageChainBuilder();
        builder.append(str.FriendString(operation.requestDateTime, SignInPosition));
        builder.append("\n现在有").append(String.valueOf(operation.getCoin())).append("枚金币");
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

    private class UserDataOperation {
        private final long userID;
        private final LocalDateTime requestDateTime;
        private boolean isNew = false;
        private boolean isSigned = false;
        public UserDataOperation(long id) {
            this.userID = id;
            requestDateTime = LocalDateTime.now();
        }

        public void signInProcess() {
            com.rongxiaoli.data.User user = RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrNull(userID);
            if (user == null) {
                // New user.
                isNew = true;
                DataBlock block = new DataBlock();
                SignInStruct struct = new SignInStruct(
                        new SignInStruct.DateLastSignIn(
                                requestDateTime.getYear(),
                                requestDateTime.getMonthValue(),
                                requestDateTime.getDayOfMonth(),
                                requestDateTime.getHour(),
                                requestDateTime.getMinute(),
                                requestDateTime.getSecond(),
                                requestDateTime.getNano()));
                block.DataAdd("SignInStruct", struct, PluginName);
                // Ready to add user.
                user = new com.rongxiaoli.data.User();
                user.DataBlockAdd(PluginName, block, PluginName);
                // Add into database.
                RongXiaoliBot.BotModuleLoader.DataBase.UserAdd(userID, user, PluginName);
                // Done.
                return;
            }
            DataBlock block = user.DataBlockReadOrNull(PluginName);
            if (user.DataBlockReadOrNull(PluginName) == null) {
                isNew = true;
                block = new DataBlock();
                SignInStruct struct = new SignInStruct(
                        new SignInStruct.DateLastSignIn(
                                requestDateTime.getYear(),
                                requestDateTime.getMonthValue(),
                                requestDateTime.getDayOfMonth(),
                                requestDateTime.getHour(),
                                requestDateTime.getMinute(),
                                requestDateTime.getSecond(),
                                requestDateTime.getNano()));
                block.DataAdd("SignInStruct", struct, PluginName);
                user.DataBlockAdd(PluginName, block, PluginName);
                RongXiaoliBot.BotModuleLoader.DataBase.UserRefresh(userID, user, PluginName);
            } else {
                // Exist user. Reading data.
                Object structObject = block.DataReadOrNull("SignInStruct");
                // Add into database.
                RongXiaoliBot.BotModuleLoader.DataBase.UserRefresh(userID, user, PluginName);

                SignInStruct struct;
                LocalDateTime lastSignInDateTime;
                if (structObject == null) {
                    // Reset user data.
                    struct = new SignInStruct(
                            new SignInStruct.DateLastSignIn(
                                    requestDateTime.getYear(),
                                    requestDateTime.getMonthValue(),
                                    requestDateTime.getDayOfMonth(),
                                    requestDateTime.getHour(),
                                    requestDateTime.getMinute(),
                                    requestDateTime.getSecond(),
                                    requestDateTime.getNano()));
                    isNew = true;
                    lastSignInDateTime = LocalDateTime.now();
                } else {
                    try {
                        LinkedTreeMap<String, Object> map = ((LinkedTreeMap<String, Object>) structObject);
                        struct = SignInStruct.fromMap(map);
                    } catch (ClassCastException CCE) {
                        struct = ((SignInStruct) structObject);
                    }
                    lastSignInDateTime = struct.toDateTime();
                }
                // Data write.
                isSigned = lastSignInDateTime.getDayOfYear() == requestDateTime.getDayOfYear();
                if (!isSigned) {
                    struct.giveCoin(1);
                    struct.refreshDateTime(LocalDateTime.now());
                }
                block.DataRefresh("SignInStruct", struct, PluginName);
                user.DataBlockRefresh(PluginName, block, PluginName);
                RongXiaoliBot.BotModuleLoader.DataBase.UserRefresh(userID, user, PluginName);
            }
        }
        private long getCoin() {
            return ((SignInStruct) RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrException(userID).DataBlockReadOrException(PluginName).DataReadOrException("SignInStruct")).getCoin();
        }
    }
}
