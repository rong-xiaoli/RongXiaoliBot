package com.rongxiaoli.module.Lottery;

import com.google.gson.internal.LinkedTreeMap;
import com.rongxiaoli.Module;
import com.rongxiaoli.RongXiaoliBot;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.data.DataBlock;
import com.rongxiaoli.data.User;
import com.rongxiaoli.module.DailySign.ModuleBackend.SignIn.SignInStruct;
import com.rongxiaoli.module.Lottery.backend.LotteryPool;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Lottery extends Module {
    private final String PluginName = "Lottery";
    private final String HelpContent = "/lottery [amount]\n" +
            "或/l [amount]\n" +
            "抽奖\n" +
            "抽奖随机数公式在GitHub上的/src/main/java/com.rongxiaoli/module/Lottery/backend/LotteryPool.java。\n" +
            "参数：\n" +
            "amount：抽奖时消耗的货币数。";
    /**
     * This is the data lock.
     */
    private boolean isLocked = false;
    private boolean IsEnabled = false;
    private LotteryPool lotteryPool;

    /**
     * Module initiate function.
     */
    public void Init() {
        lotteryPool = new LotteryPool();
        lotteryPool.setEnabled(true);
        lotteryPool.RefreshStart();
        this.IsEnabled = true;
        Log.WriteLog(Log.Level.Debug,
                "Lottery initiated. ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

    /**
     * Module shutdown function.
     */
    public void Shutdown() {
        lotteryPool.RefreshStop();
        this.IsEnabled = false;
        Log.WriteLog(Log.Level.Debug,
                "Lottery shutting down. ",
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

        //0-width.
        if (message.length == 0) {
            return;
        }
        if (!IsEnabled) return;
        if (!Objects.equals(message[0], "/lottery") && !Objects.equals(message[0], "/l")) return;
        // Check lock.
        if (isLocked) {
            SubjectContact.sendMessage("其他用户正在操作，请稍等。");
            return;
        }
        //Process start.
        long amount = 1;
        if (message.length == 2) {
            amount = Long.parseLong(message[1]);
        }
        if (amount < 1) {
            SubjectContact.sendMessage("数额不可小于1");
            return;
        }

        // Ready to add in pool.
        if (lotteryPool.isFriendInList(Friend)) {
            SubjectContact.sendMessage("你已经抽过了。");
            return;
        }
        User user = RongXiaoliBot.BotModuleLoader.DataBase.UserReadOrNull(Friend);
        if (user == null) {
            SubjectContact.sendMessage("您尚未创建用户");
            return;
        }
        DataBlock block = user.DataBlockReadOrNull("DailySign");
        if (block == null) {
            SubjectContact.sendMessage("您尚未创建用户");
            return;
        }
        SignInStruct struct;
        Object structObject = block.DataReadOrException("SignInStruct");
        try {
            LinkedTreeMap<String, Object> map = ((LinkedTreeMap<String, Object>) structObject);
            struct = SignInStruct.fromMap(map);
        } catch (ClassCastException CCE) {
            struct = ((SignInStruct) structObject);
        }
        if (struct.getCoin() < amount) {
            SubjectContact.sendMessage("您余额不足");
            return;
        }
        struct.revokeCoin(amount);
        block.DataRefresh("SignInStruct", struct, PluginName);
        user.DataBlockRefresh("DailySign", block, PluginName);
        RongXiaoliBot.BotModuleLoader.DataBase.UserRefresh(Friend, user, PluginName);
        // Process start. Locking.
        isLocked = true;
        boolean status = lotteryPool.inPool(amount, Friend);
        MessageChainBuilder builder = new MessageChainBuilder();
        if (!status) {
            builder.append("很遗憾，没有中奖。\n");
            builder.append("累计：").append(String.valueOf(lotteryPool.getPool()));
            SubjectContact.sendMessage(builder.build());
        } else {
            long finalPool = lotteryPool.getFinalPool();
            Log.WriteLog(Log.Level.Info,
                    Friend + "got prize. Total: " + finalPool,
                    Log.LogClass.ModuleMain,
                    PluginName);
            builder.append("恭喜，中奖了！\n");
            builder.append("累计：").append(String.valueOf(finalPool));
            SubjectContact.sendMessage(builder.build());
            struct.giveCoin(finalPool);
            block.DataRefresh("SignInStruct", struct, PluginName);
            user.DataBlockRefresh("DailySign", block, PluginName);
            RongXiaoliBot.BotModuleLoader.DataBase.UserRefresh(Friend, user, PluginName);
        }
        isLocked = false;
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
}
