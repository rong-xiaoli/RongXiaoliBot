package com.rongxiaoli.module.PokeAction;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.NudgeEvent;

import java.util.Random;

public class PokeAction extends Module {

    // Module status vars.
    private String PluginName = "PokeAction";
    private String HelpContent = "戳一戳：无帮助文档。\n";
    private Boolean IsEnabled = false;
    private Boolean DebugMode = false;

    //Vars declaration end.
    /**
     * Module initiate function.
     */
    public void Init() {
        IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "PokeAction initiated. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Shutdown.
     */
    public void Shutdown() {
        Log.WriteLog(Log.Level.Debug, "PokeAction stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    public void PokeMain(NudgeEvent e){
        if (!isEnabled()) {
            return;
        }
        long TargetID = e.getTarget().getId();
        long BotID = e.getBot().getId();
        if (TargetID != BotID) return;

        Class subjectClass = e.getSubject().getClass();
        boolean isFromGroup = false;
        if (subjectClass.getName().equals(Group.class.getName())) isFromGroup = true;

        long SubjectID = e.getSubject().getId();
        long FromID = e.getFrom().getId();
        //Start processing.
        long seed = BotID * 2 - FromID + SubjectID;
        Action action = new Action(seed);
        action.Main(e, e.getFrom().getId(), e.getBot().getId(),isFromGroup,e.getSubject().getId());
    }
    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SenderContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SenderContact) {
        return;
    }

    /**
     * Group message process.
     * Not used.
     *
     * @param arrCommand
     * @param Friend
     * @param Group
     * @param SenderContact
     */
    public void GroupMain(String[] arrCommand, long Friend, long Group, Contact SenderContact) {
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
