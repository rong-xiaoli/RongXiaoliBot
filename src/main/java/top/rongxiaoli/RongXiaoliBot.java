package top.rongxiaoli;

import top.rongxiaoli.backend.Log;
import top.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import top.rongxiaoli.module.Baltop.Baltop;
import top.rongxiaoli.module.BotCommand.BotCommand;
import top.rongxiaoli.module.Broadcast.Broadcast;
import top.rongxiaoli.module.DailySign.DailySign;
import top.rongxiaoli.module.EmergencyStop.EmergencyStop;
import top.rongxiaoli.module.FortuneToday.FortuneToday;
import top.rongxiaoli.module.Lottery.Lottery;
import top.rongxiaoli.module.Ping.Ping;
import top.rongxiaoli.module.PokeAction.PokeAction;
import top.rongxiaoli.module.Repeater.Repeater;
import top.rongxiaoli.module.setu.PicturePlugin;
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
        super(new JvmPluginDescriptionBuilder("top.rongxiaoli.RongXiaoliBot", PluginVersion)
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

        //Done.

        //Module init.
        BotModuleLoader.ModuleInit();
        // Data init.
        BotModuleLoader.DataInit();
        // Todo: Add a config loader.


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