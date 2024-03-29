package com.rongxiaoli.module.Baltop;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.User;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInStruct;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Baltop extends Module {
    private final String PluginName = "Baltop";
    private final String HelpContent = "/baltop\n" +
            "输出财富榜前10。";
    private boolean IsEnabled = true;

    /**
     * Module initiate function.
     */
    public void Init() {
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,
                "Baltop initiated. ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug,
                "Baltop shutting down. ",
                Log.LogClass.ModuleMain,
                PluginName);
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

        //0-length array.
        if (arrCommand.length == 0) {
            return;
        }
        if (!IsEnabled) {
            SubjectContact.sendMessage("当前插件未启用");
            return;
        }

        // Process start.
        if (!Objects.equals(message[0], "/baltop")) {
            return;
        }
        // Querying every single user's coin.
        SubjectContact.sendMessage("正在查询，请稍后");
        HashMap<Long, User> userList = RongXiaoliBot.BotModuleLoader.DataBase.UserListDeepCopy();
        List<Long> topList = userList.entrySet().stream().sorted((Map.Entry<Long, User> e1, Map.Entry<Long, User> e2) -> {
                    return ((int) ((long) ((SignInStruct) e2.getValue().DirectDataRead("DailySign", "SignInStruct")).getCoin() - ((long) ((SignInStruct) e1.getValue().DirectDataRead("DailySign", "SignInStruct")).getCoin())));
                })
                .map(userEntry -> userEntry.getKey()).collect(Collectors.toList())
                .subList(0, 10);

        // Query finished.
        MessageChainBuilder builder = new MessageChainBuilder();
        int count = 0;
        for (long userID :
                topList) {
            count++;
            builder.append("第" + count + ": " + userID + ": " + ((long) userList.get(userID).DirectDataRead("DailySign", "Coin")) + "\n");
        }

        builder.append("队列末");
        SubjectContact.sendMessage(builder.build());
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
        return this.PluginName;
    }

    /**
     * Help content. Used in BotCommand.Modules.Help.
     */
    public String getHelpContent() {
        return this.HelpContent;
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
        this.IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return false;
    }

    private class TopList extends Thread {
        private boolean status;
        private short LifeSpawn;
        private Queue<Long> topUserID;
        private Queue<Long> topUserAmount;

        public TopList() {
            topUserID = new LinkedList<>();
            topUserAmount = new LinkedList<>();
            LifeSpawn = 360;
        }

        public TopList(short spawn) {
            topUserID = new LinkedList<>();
            topUserAmount = new LinkedList<>();
            LifeSpawn = spawn;
        }

        public void stopLifeSpawn() {
            // Todo: finish this class.
        }

        public void Query() {
            // Querying every single user's coin.
            HashMap<Long, User> userList = RongXiaoliBot.BotModuleLoader.DataBase.UserListDeepCopy();
            List<Long> topList = userList.entrySet().stream().sorted((Map.Entry<Long, User> e1, Map.Entry<Long, User> e2) -> {
                        return ((int) ((long) ((SignInStruct) e2.getValue().DirectDataRead("DailySign", "SignInStruct")).getCoin() - ((long) ((SignInStruct) e1.getValue().DirectDataRead("DailySign", "SignInStruct")).getCoin())));
                    })
                    .map(userEntry -> userEntry.getKey()).collect(Collectors.toList())
                    .subList(0, 10);
            for (long userID :
                    topList) {
                topUserID.add(userID);
                topUserAmount.add(((SignInStruct) RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrNull(userID).DirectDataRead("DailySign", "SignInStruct")).getCoin());
            }
        }

        public long getUserID() {
            return topUserID.poll();
        }

        public long getUserAmount() {
            return topUserAmount.poll();
        }

        @Override
        public void run() {
        }
    }
}
