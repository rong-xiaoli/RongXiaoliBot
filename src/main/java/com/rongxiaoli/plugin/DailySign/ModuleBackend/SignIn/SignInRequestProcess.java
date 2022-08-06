package com.rongxiaoli.plugin.DailySign.ModuleBackend.SignIn;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.plugin.DailySign.DailySign;
import net.mamoe.mirai.contact.Contact;

import java.time.LocalDate;
import java.util.Objects;

public class SignInRequestProcess {
    public static String CommandPrefix = "/sign";
    /**
     * Plugin main method.
     * @param arrCommand Command array.
     * @param qqID Friend QQID.
     * @param groupID Group ID.
     * @param SenderContact Contact of the sender.
     */
    public static void Process(String[] arrCommand, long qqID, long groupID, Contact SenderContact) {
        //Judge if the plugin is enabled.
        if (!DailySign.Enabled) {
            SenderContact.sendMessage("当前插件未启用");
            return;
        }
        //Process start.
        //Judge if prefix is correct.
        if (!Objects.equals(arrCommand[0], CommandPrefix)) {
            return;
        }

        //Define variables.
        boolean isExist = false;
        long coin = 0;

        //Judge if sender is from a group.
        if (groupID == 0) {
            Log.WriteLog(Log.Level.Debug,
                    "Friend Sign request:" + qqID,
                    Log.Module.PluginMain,
                    DailySign.PluginName);
        } else {
            Log.WriteLog(Log.Level.Debug,
                    "Group: " + groupID + " (Member" + qqID + "): Sign request. ",
                    Log.Module.PluginMain,
                    DailySign.PluginName);
        }
        for (SignObjectList.FriendSignObject SingleObject :
                DailySign.SignList.FriendSignList) {
            if (SingleObject.QQID == qqID) {
                //ID exists.
                SingleObject.Coin += 1;
                coin = SingleObject.Coin;
                SingleObject.LastSignTime.Day = LocalDate.now().getDayOfMonth();
                SingleObject.LastSignTime.Month = LocalDate.now().getMonthValue();
                SingleObject.LastSignTime.Year = LocalDate.now().getYear();
                isExist = true;
            }
        }
        if (!isExist) {
            SignObjectList.FriendSignObject ObjectAdding = new SignObjectList.FriendSignObject();
            ObjectAdding.LastSignTime.Day = LocalDate.now().getDayOfMonth();
            ObjectAdding.LastSignTime.Month = LocalDate.now().getMonthValue();
            ObjectAdding.LastSignTime.Year = LocalDate.now().getYear();
            ObjectAdding.QQID = qqID;
            ObjectAdding.Coin = 1;
            DailySign.SignList.FriendSignList.add(ObjectAdding);
            coin = ObjectAdding.Coin;
        }
        if (groupID == 0) {
            //Friend.
            SignInMessageSender.Friend(qqID, SenderContact, isExist, coin);
        } else {
            for (SignObjectList.GroupSignObject SingleGroupObject :
                    DailySign.SignList.GroupSignList) {
                if (SingleGroupObject.GroupID == groupID) {
                    SignInMessageSender.Group(qqID, groupID, SenderContact, isExist, coin, SingleGroupObject.Position);
                    return;
                }
            }
            SignObjectList.GroupSignObject ObjectAdding = new SignObjectList.GroupSignObject();
            ObjectAdding.Position = 2;
            ObjectAdding.GroupID = groupID;
            DailySign.SignList.GroupSignList.add(ObjectAdding);
        }
    }
    //Process end.
}