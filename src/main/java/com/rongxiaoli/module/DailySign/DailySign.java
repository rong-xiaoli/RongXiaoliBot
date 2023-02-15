package com.rongxiaoli.module.DailySign;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.DataBlock;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignString;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DailySign extends Module {
    // Public vars.
    private final String PluginName = "DailySign";
    private final String HelpContent = "/sign\n" +
            "每日签到";
    private boolean IsEnabled = true;
    private boolean DebugMode = false;
    // Private vars.
    private final String Command = "/sign";
    private long SignInPosition = 1;
    //Vars def finish.

    /**
     * Module initiate function.
     */
    public void Init() {
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
                    operation.signInRequestDateTime.getYear(),
                    operation.signInRequestDateTime.getMonthValue(),
                    operation.signInRequestDateTime.getDayOfMonth(),
                    operation.signInRequestDateTime.getDayOfWeek(),
                    operation.signInRequestDateTime.getHour(),
                    operation.signInRequestDateTime.getMinute(),
                    operation.signInRequestDateTime.getSecond(),
                    operation.signInRequestDateTime.getNano()));
            SubjectContact.sendMessage(builder.build());
            return;
        }
        MessageChainBuilder builder = new MessageChainBuilder();
        builder.append(str.FriendString(operation.signInRequestDateTime, SignInPosition));
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
        private boolean isNew = false;
        private boolean isSigned = false;
        public UserDataOperation(long id) {
            this.userID = id;
            signInRequestDateTime = LocalDateTime.now();
        }
        private long userID;
        private LocalDateTime lastSignInDateTime;
        private LocalDateTime signInRequestDateTime;
        public void signInProcess() {
            com.rongxiaoli.data.User user = RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrNull(userID);
            if (user == null) {
                // New user.
                isNew = true;
                DataBlock block = new DataBlock();
                block.DataAdd("Coin", 1, PluginName);
                block.DataAdd("DateLastSignIn", LocalDateTime.now(), PluginName);
                // Ready to add user.
                user = new com.rongxiaoli.data.User();
                user.DataBlockAdd(PluginName, block, PluginName);
                // Add into database.
                RongXiaoliBot.BotModuleLoader.DataBase.UserAdd(userID, user, PluginName);
                isSigned = true;
                // Done.
                return;
            }
            DataBlock block = user.DataBlockReadOrNull(PluginName);
            if (user.DataBlockReadOrNull(PluginName) == null) {
                isNew = true;
                block = new DataBlock();
                block.DataAdd("Coin", 1, PluginName);
                block.DataAdd("DateLastSignIn", LocalDateTime.now(), PluginName);
                user.DataBlockAdd(PluginName, block, PluginName);
                isSigned = true;
            } else {
                // Exist user. Reading data.
                Object CoinObject = block.DataReadOrNull("Coin");
                Object DateLastSignInObject = block.DataReadOrNull("DateLastSignIn");

                long Coin = 0;

                if (CoinObject != null) {
                    Coin = (long) CoinObject;
                }
                if (DateLastSignInObject == null) {
                    // Reset user data.
                    Coin = 0;
                    isNew = true;
                    lastSignInDateTime = LocalDateTime.now();
                } else {
                    lastSignInDateTime = LocalDateTime.parse(((String) DateLastSignInObject), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                // Data write.
                isSigned = LocalDateTime.parse(((String) DateLastSignInObject), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getDayOfYear() == signInRequestDateTime.getDayOfYear();// Todo: bug. class java.lang.Double cannot be cast to class java.lang.String (java.lang.Double and java.lang.String
                block.DataRefresh("DateLastSignIn", signInRequestDateTime, PluginName);
                if (!isSigned) {
                    block.DataRefresh("Coin", Coin + 1, PluginName);
                }
            }
        }
        public LocalDateTime getLastSignInDateTime() {
            return (LocalDateTime) RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrException(userID).DataBlockReadOrException(PluginName).DataReadOrException("DateLastSignIn");
        }
        public long getCoin() {
            return (long) RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrException(userID).DataBlockReadOrException(PluginName).DataReadOrException("Coin");
        }
    }
}
