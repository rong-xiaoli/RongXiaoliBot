package com.rongxiaoli.module.FortuneToday;

import com.rongxiaoli.Module;
import com.rongxiaoli.backend.Log;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class FortuneToday extends Module {
    private final String PluginName = "FortuneToday";
    private final String HelpContent = "/fortune\n" +
            "获取今日运势";
    private boolean IsEnabled = true;
    private final String Command = "/fortune";

    /**
     * Module initiate function.
     */
    public void Init() {
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug, "FortuneToday enabled. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug, "FortuneToday stopped. ", Log.LogClass.ModuleMain, PluginName);
    }

    /**
     * Friend message process.
     *
     * @param arrCommand
     * @param Friend
     * @param SubjectContact
     */
    public void FriendMain(String[] arrCommand, long Friend, Contact SubjectContact) {
        String[] message = arrCommand.clone();
        // 0-width.
        if (message.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (!message[0].equals(Command)) return;
        // Process start.

        int fortRandom = FortuneRandom.GetRandom(Friend);
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.append("今日运势为：").append(String.valueOf(fortRandom)).append("\n");
        mcb.append(FortuneRandom.GetFortuneString(fortRandom));
        SubjectContact.sendMessage(mcb.build());
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
        String[] message = arrCommand.clone();
        // 0-width.
        if (message.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (!message[0].equals(Command)) return;
        // Process start.

        int fortRandom = FortuneRandom.GetRandom(Friend);
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.append("今日运势为：").append(String.valueOf(fortRandom)).append("\n");
        mcb.append(FortuneRandom.GetFortuneString(fortRandom));
        SubjectContact.sendMessage(mcb.build());
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
        return false;
    }

    /**
     * Fortune random related.
     */
    private static class FortuneRandom {
        public static int GetRandom(long ID) {
            Calendar cal = new GregorianCalendar();
            int year, month, day;
            year = cal.get(Calendar.YEAR) * 10000;
            month = cal.get(Calendar.MONTH) * 100;
            day = cal.get(Calendar.DAY_OF_YEAR);
            Random rand = new Random(ID + year + month + day);
            return rand.nextInt(0, 100);
        }

        public static String GetFortuneString(int fortuneRandom) {
            if (fortuneRandom == 0) {
                return "哇，你也太非了吧？";
            } else if (0 < fortuneRandom && fortuneRandom <= 10) {
                return "今天运气不太好哦，但是也要加油呀～";
            } else if (10 < fortuneRandom && fortuneRandom <= 25) {
                return "今天有点非哦，但也是美好的一天～";
            } else if (25 < fortuneRandom && fortuneRandom <= 40) {
                return "今天有点小非呢";
            } else if (40 < fortuneRandom && fortuneRandom <= 55) {
                return "今天运气平平，也是平常的一天～";
            } else if (55 < fortuneRandom && fortuneRandom <= 70) {
                return "今天有点小幸运呢";
            } else if (70 < fortuneRandom && fortuneRandom <= 85) {
                return "幸运的 一天～";
            } else if (85 < fortuneRandom && fortuneRandom <= 99) {
                return "Lucky~今天好幸运啊!";
            } else if (fortuneRandom == 100) {
                return "哇！欧皇！";
            } else return "";
        }
    }
}
