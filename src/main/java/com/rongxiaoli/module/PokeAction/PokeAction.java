package com.rongxiaoli.module.PokeAction;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.NudgeEvent;

import java.util.Random;

public class PokeAction extends Module {

    // Module status vars.
    private Action action;
    private String PluginName = "PokeAction";
    private String HelpContent = "戳一戳\n" +
            "无帮助文档。\n";
    private Boolean IsEnabled = false;
    private Boolean DebugMode = false;

    private Random random;
    //Vars declaration end.

    /**
     * Module initiate function.
     */
    public void Init() {
        action = new Action();
        random = new Random();
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "PokeAction initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Shutdown.
     */
    public void Shutdown() {
        Log.WriteLog(Log.Level.Debug, "PokeAction stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void PokeMain(NudgeEvent e) {
        if (!isEnabled()) {
            return;
        }
        long TargetID = e.getTarget().getId();
        long BotID = e.getBot().getId();
        if (TargetID != BotID) return;

        boolean isFromGroup = false;
        if (e.getSubject().getId() != e.getFrom().getId()) isFromGroup = true;

        long SubjectID = e.getSubject().getId();
        long FromID = e.getFrom().getId();
        //Start processing.
        int ReplyType = random.nextInt(0,2);
        int InnerRandom1 = random.nextInt(0, 2);
        int InnerRandom2 = random.nextInt(0, 5);
        action.Main(e, e.getFrom().getId(), e.getBot().getId(), isFromGroup, e.getSubject().getId(), ReplyType);
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        return;
    }

    /**
     * Group message process.
     * Not used.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SubjectContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SubjectContact) {
        return;
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
        return IsEnabled;
    }

    /**
     * Set status.
     *
     * @param status Status
     */
    public void setEnabled(boolean status) {
        IsEnabled = status;
    }

    /**
     * Debug mode.
     */
    public boolean isDebugMode() {
        return DebugMode;
    }
}
