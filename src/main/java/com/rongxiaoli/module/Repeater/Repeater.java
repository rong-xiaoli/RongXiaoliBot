package com.rongxiaoli.module.Repeater;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.StringProcess;
import com.rongxiaoli.module.Repeater.backend.BannedWordCheck;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Repeater extends Module {
    private boolean IsEnabled = true;
    private List<Long> RepeatGroup;
    private List<Long> RepeatFriend;
    private final String PluginName = "Repeater";
    private final String HelpContent = "复读姬\n" +
            "发送/repeat启动复读模式\n" +
            "再次发送/repeat关闭复读模式\n";
    private final String BannedWordConfigPath = RongXiaoliBot.ConfigPath.toString() + "/Repeater/BannedWord/";

    /**
     * Module initiate function.
     */
    public void Init() {
        RepeatFriend = new CopyOnWriteArrayList<>();
        RepeatGroup = new CopyOnWriteArrayList<>();
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "Repeat initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "Repeat shut down. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        if (!IsEnabled) return;
        // Start processing.
        boolean isInRepeatList = false;
        for (long SingleFriendID :
                RepeatFriend) {
            if (SingleFriendID == Friend) {
                isInRepeatList = true;
                break;
            }
        }
        if (!Objects.equals(Objects.requireNonNull(StringProcess.Imperative(arrCommand))[0], "/repeat")) {
            if (isInRepeatList) {
                //In repeat list, not a command. Repeat.
                // The process starts here.
                Process p = new Process();
                p.start(arrCommand, SubjectContact);
            }
            //Not in the list, not a command. Return.
            return;
        }
        if (isInRepeatList) {
            RepeatFriend.remove(Friend);
            SubjectContact.sendMessage("不复读啦！累了");
        } else {
            RepeatFriend.add(SubjectContact.getId());
            SubjectContact.sendMessage("我是复读姬！");
        }
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

    public class Process {
        /**
         * Start processing the repeating sentence.
         */
        public void start(String[] splitMessage, Contact SubjectContact) {
            // First step: check inappropriate word.
            BannedWordCheck check = new BannedWordCheck(BannedWordConfigPath);
            if (!check.isSuitable(splitMessage)) {
                SubjectContact.sendMessage("我才不复读这个呢，哼");
                return;
            }
            MessageChainBuilder b = new MessageChainBuilder();
            for (String singleString :
                    splitMessage) {
                b.append(singleString);
                b.append(" ");
            }
            SubjectContact.sendMessage(b.build());
        }
    }
}
