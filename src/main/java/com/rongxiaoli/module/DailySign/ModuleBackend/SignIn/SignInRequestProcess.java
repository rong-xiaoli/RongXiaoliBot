package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.DailySign.DailySign;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.time.LocalDate;
import java.util.Objects;

public class SignInRequestProcess {
    public static String CommandPrefix = "Rsign";
    /**
     * Plugin main method.
     * @param arrCommand Command array.
     * @param qqID Friend QQID.
     * @param groupID Group ID.
     * @param SenderContact Contact of the sender.
     */
    public static void Process(String[] arrCommand, long qqID, long groupID, Contact SenderContact) {
        //Judge if the plugin is enabled.
        if (!DailySign.IsEnabled) {
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
        boolean isGroupExist = false;

        int Position = 0;

        //Judge if sender is from a group.
        if (groupID == 0) {
            //Friend sign in request.
            Log.WriteLog(Log.Level.Debug,
                    "Friend Sign request:" + qqID,
                    Log.LogClass.ModuleMain,
                    DailySign.PluginName);
        } else {
            //Group sign in request.
            Log.WriteLog(Log.Level.Debug,
                    "Group: " + groupID + " (Member" + qqID + "): Sign request. ",
                    Log.LogClass.ModuleMain,
                    DailySign.PluginName);
        }
        if (DailySign.SignList.FriendSignList != null) {
            for (SignObjectList.FriendSignObject SingleObject :
                    DailySign.SignList.FriendSignList) {
                if (SingleObject.QQID == qqID) {
                    //ID exists.
                    if (SingleObject.LastSignTime.Day != LocalDate.now().getDayOfMonth()) {
                        SingleObject.LastSignTime.Day = LocalDate.now().getDayOfMonth();
                        SingleObject.LastSignTime.Month = LocalDate.now().getMonthValue();
                        SingleObject.LastSignTime.Year = LocalDate.now().getYear();
                        isExist = true;
                    } else {
                        MessageChainBuilder b = new MessageChainBuilder();
                        SenderContact.sendMessage("已经签到过了哦");
                        return;
                    }
                }
            }
        }
        if (!isExist) {
            SignObjectList.FriendSignObject ObjectAdding = new SignObjectList.FriendSignObject();
            ObjectAdding.LastSignTime.Day = LocalDate.now().getDayOfMonth();
            ObjectAdding.LastSignTime.Month = LocalDate.now().getMonthValue();
            ObjectAdding.LastSignTime.Year = LocalDate.now().getYear();
            ObjectAdding.QQID = qqID;
            DailySign.SignList.FriendSignList.add(ObjectAdding);
        }
        if (groupID == 0) {
            //Friend.
            SignInMessageSender.Friend(SenderContact, isExist);
        } else {
            for (SignObjectList.GroupSignObject SingleGroupObject :
                    DailySign.SignList.GroupSignList) {
                if (SingleGroupObject.GroupID == groupID) {
                    isGroupExist = true;
                    SignInMessageSender.Group(qqID, groupID, SenderContact, isExist, SingleGroupObject.Position);
                    SingleGroupObject.Position++;
                    return;
                }
            }
            if (!isGroupExist) {
                SignObjectList.GroupSignObject ObjectAdding = new SignObjectList.GroupSignObject();
                ObjectAdding.Position = 1;
                ObjectAdding.GroupID = groupID;
                DailySign.SignList.GroupSignList.add(ObjectAdding);
                Position = ObjectAdding.Position;
                SignInMessageSender.Group(qqID,groupID,SenderContact,isExist, ObjectAdding.Position);
            }
        }
    }
    //Process end.
}