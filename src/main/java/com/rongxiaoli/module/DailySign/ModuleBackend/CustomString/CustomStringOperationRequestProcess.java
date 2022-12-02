package com.rongxiaoli.module.DailySign.ModuleBackend.CustomString;

import com.rongxiaoli.module.DailySign.DailySign;
import net.mamoe.mirai.contact.Contact;

import java.util.Objects;

public class CustomStringOperationRequestProcess {
    public static String CommandPrefix = "/Str";
    public static void Process(String[] arrCommand, long QQID, long GroupID, Contact SenderContact) {
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
    }
}
