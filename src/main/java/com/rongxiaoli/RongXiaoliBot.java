package com.rongxiaoli;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.module.Baltop.Baltop;
import com.rongxiaoli.module.BotCommand.BotCommand;
import com.rongxiaoli.module.Broadcast.Broadcast;
import com.rongxiaoli.module.DailySign.DailySign;
import com.rongxiaoli.module.EmergencyStop.EmergencyStop;
import com.rongxiaoli.module.FortuneToday.FortuneToday;
import com.rongxiaoli.module.Lottery.Lottery;
import com.rongxiaoli.module.Ping.Ping;
import com.rongxiaoli.module.PokeAction.PokeAction;
import com.rongxiaoli.module.Repeater.Repeater;
import com.rongxiaoli.module.setu.PicturePlugin;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.nio.file.Path;

public final class RongXiaoliBot extends JavaPlugin {
    public static final String PluginVersion = "0.2.1-Preview1";
    public static final RongXiaoliBot INSTANCE = new RongXiaoliBot();
    public static final long Owner = 1751362263;
    private static final String PluginName = "RongXiaoliBot PluginMain";
    public static boolean IsEnabled = false;
    public static Path DataPath;
    public static Path ConfigPath;
    public static ModuleLoader BotModuleLoader;

    private RongXiaoliBot() {
        super(new JvmPluginDescriptionBuilder("com.rongxiaoli.RongXiaoliBot", PluginVersion)
                .name("RongXiaoli Bot")
                .author("RongXiaoli")
                .build());
    }

    @Override
    public void onEnable() {
        //Initiate all parts.
        getLogger().info("Plugin initiating. ");
        //Logger init.
        Log.Init(getLogger());
        Log.WriteLog(Log.Level.Debug,
                "Log module initiated. ",
                Log.LogClass.Log,
                PluginName);

        DataPath = getDataFolderPath();
        ConfigPath = getConfigFolderPath();

        //Plugin init.
        BotModuleLoader = new ModuleLoader();
        //Adding bot module.

        BotModuleLoader.ModuleList.add(new EmergencyStop());
        BotModuleLoader.ModuleList.add(new BotCommand());
        BotModuleLoader.ModuleList.add(new Broadcast());
        BotModuleLoader.ModuleList.add(new AutoAcceptPlugin());
        BotModuleLoader.ModuleList.add(new PicturePlugin());
        BotModuleLoader.ModuleList.add(new DailySign());
        BotModuleLoader.ModuleList.add(new PokeAction());
        BotModuleLoader.ModuleList.add(new Ping());
        BotModuleLoader.ModuleList.add(new Repeater());
        BotModuleLoader.ModuleList.add(new FortuneToday());
        BotModuleLoader.ModuleList.add(new Lottery());
        BotModuleLoader.ModuleList.add(new Baltop());
        //Done.

        //Module init.
        BotModuleLoader.ModuleInit();
        // Data init.
        BotModuleLoader.DataInit();

        // Plugin init finish.
        //Todo: Read bot owner from a file. 
        // Register listener.
        GlobalEventChannel.INSTANCE.registerListenerHost(new PluginListener());
        IsEnabled = true;
        Log.WriteLog(Log.Level.Info,
                "Plugin initiated!",
                Log.LogClass.ModuleMain,
                PluginName);
        //Done!
    }

    @Override
    public void onDisable() {
        //Disabling all parts.
        Log.WriteLog(Log.Level.Debug,
                "Saving data...",
                Log.LogClass.ModuleMain,
                PluginName);

        BotModuleLoader.DataSave();

        Log.WriteLog(Log.Level.Info,
                "Plugin exiting...",
                Log.LogClass.ModuleMain,
                PluginName);
        BotModuleLoader.ModuleShutdown();

        Log.WriteLog(Log.Level.Debug,
                "All modules disabled. shutting down! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }
}