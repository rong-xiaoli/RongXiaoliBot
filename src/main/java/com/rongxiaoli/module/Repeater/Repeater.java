package com.rongxiaoli.module.Repeater;

import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.StringProcess;
import com.rongxiaoli.module.Repeater.backend.BannedWordCheck;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Repeater extends Module {
    private final String PluginName = "Repeater";
    private final String HelpContent = "复读姬\n" +
            "发送/repeat启动复读模式\n" +
            "再次发送/repeat关闭复读模式\n" +
            "注：该功能对于多个空行等情况并不能准确复读\n";
    private final String BannedWordConfigPath = RongXiaoliBot.ConfigPath.toString() + "/Repeater/BannedWord/";
    private boolean IsEnabled = true;
    private BannedWordCheck check;
    private CopyOnWriteArrayList<Long> RepeatGroup;
    private CopyOnWriteArrayList<Long> RepeatFriend;

    /**
     * Module initiate function.
     */
    public void Init() {
        check = new BannedWordCheck(BannedWordConfigPath, PluginName);
        RepeatFriend = new CopyOnWriteArrayList<>();
        RepeatGroup = new CopyOnWriteArrayList<>();
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "Repeat initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        check.saveBannedWord();
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "Repeat shutting down. ", Log.LogClass.ModuleMain, PluginName);
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
        String[] ImpString = StringProcess.Imperative(arrCommand);
        if (ImpString != null) {
            if (Objects.equals(ImpString[0], "/ban")) {
                operateBannedWord(ImpString, Friend, SubjectContact);
                return;
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
        if (!IsEnabled) return;
        // Start processing.
        boolean isInRepeatList = false;
        for (long SingleGroupID :
                RepeatGroup) {
            if (SingleGroupID == Group) {
                isInRepeatList = true;
                break;
            }
        }
        String[] ImpString = StringProcess.Imperative(arrCommand);
        if (ImpString != null) {
            if (Objects.equals(ImpString[0], "/ban")) {
                operateBannedWord(ImpString, Friend, SubjectContact);
                return;
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
            RepeatGroup.remove(Friend);
            SubjectContact.sendMessage("不复读啦！累了");
        } else {
            RepeatGroup.add(SubjectContact.getId());
            SubjectContact.sendMessage("我是复读姬！");
        }
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

    private void operateBannedWord(String[] impString, long Friend, Contact SubjectContact) {
        if (Friend == RongXiaoliBot.Owner) {
            if (impString.length == 1) {
                SubjectContact.sendMessage(check.getBannedWord());
                return;
            }
            if (impString.length == 2) {
                if (Objects.equals(impString[1], "add")) {
                    SubjectContact.sendMessage("请输入要添加的禁用词");
                    return;
                }
                if (Objects.equals(impString[1], "del")) {
                    SubjectContact.sendMessage("请输入要删除的禁用词");
                    return;
                }
            }
            if (impString.length == 3) {
                if (Objects.equals(impString[1], "add")) {
                    if (check.addBannedWord(impString[2])) {
                        SubjectContact.sendMessage("该关键词已存在");
                        return;
                    }
                    SubjectContact.sendMessage("已添加");
                    return;
                } else if (Objects.equals(impString[1], "del")) {
                    if (check.delBannedWord(impString[2])) {
                        SubjectContact.sendMessage("已删除");
                        return;
                    } else {
                        SubjectContact.sendMessage("未找到该关键词");
                        return;
                    }
                } else {
                    SubjectContact.sendMessage("未知指令");
                }
            }
            SubjectContact.sendMessage("请输入禁用词");
        }
    }

    private class Process {
        /**
         * Start processing the repeating sentence.
         */
        public void start(String[] splitMessage, Contact SubjectContact) {
            // First step: check inappropriate word.
            if (!check.isSuitable(splitMessage)) {
                SubjectContact.sendMessage("我才不复读这个呢，哼");
                return;
            }
            // Done. Building message.
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
