package com.rongxiaoli;
//Todo: Test the whole plugin.
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.module.AutoAccept.AutoAcceptPlugin;
import com.rongxiaoli.module.BotCommand.BotCommand;
import com.rongxiaoli.module.Broadcast.Broadcast;
import com.rongxiaoli.module.DailySign.DailySign;
import com.rongxiaoli.module.EmergencyStop.EmergencyStop;
import com.rongxiaoli.module.Picture.PicturePlugin;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.nio.file.Path;

public final class RongXiaoliBot extends JavaPlugin {
    public static final RongXiaoliBot INSTANCE = new RongXiaoliBot();
    private static final String PluginName = "RongXiaoliBot PluginMain";
    public static final long Owner = 0000000000;
    public static boolean IsEnabled = false;
    public static Path DataPath;
    public static Path ConfigPath;
    public static ModuleLoader BotModuleLoader;
    private RongXiaoliBot() {
        super(new JvmPluginDescriptionBuilder("com.rongxiaoli.RongXiaoliBot", "0.1.2")
                .name("RongXiaoli Bot")
                .author("RongXiaoli")
                .build());
    }
    @Override
    public void onEnable() {
        //Initiate all parts.
        getLogger().info("Plugin initiating. ");
        //Logger init.
        Log.Init();
        Log.WriteLog(Log.Level.Debug,
                "Log module initiated. ",
                Log.LogClass.Log,
                PluginName);

        DataPath = getDataFolderPath();
        ConfigPath = getConfigFolderPath();

        //Plugin init.
        /*

        //Version 0.1.0 removed:
        //Reason: After being banned for many times, this function is banned forever for others.
        PicturePlugin.Init();
        Log.WriteLog(Log.Level.Debug,
                "setu Plugin initiated. ",
                Log.Module.PluginMain,
                PluginName);

        DailySign.Init();
        Log.WriteLog(Log.Level.Debug,
                "DailySign Plugin initiated. ",
                Log.Module.PluginMain,
                PluginName);

        */
        BotModuleLoader = new ModuleLoader();
        BotModuleLoader.ModuleList = new Module[6];
        //Add bot module start.

        BotModuleLoader.ModuleList[0] = new EmergencyStop();
        BotModuleLoader.ModuleList[1] = new BotCommand();
        BotModuleLoader.ModuleList[2] = new Broadcast();
        BotModuleLoader.ModuleList[3] = new AutoAcceptPlugin();
        BotModuleLoader.ModuleList[4] = new PicturePlugin();
        BotModuleLoader.ModuleList[5] = new DailySign();

        //Done.
        //Module init.
        for (Module SingleModule :
                BotModuleLoader.ModuleList) {
            SingleModule.Init();
        }
        
        //Plugin init finish.
        //Register listener.
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
        //Disable all parts.
        Log.WriteLog(Log.Level.Info,
                "Plugin exiting...",
                Log.LogClass.ModuleMain,
                PluginName);

        for (Module SingleModule:
                BotModuleLoader.ModuleList) {
            SingleModule.Shutdown();
        }

        Log.WriteLog(Log.Level.Debug,
                "All modules disabled, shutting down! ",
                Log.LogClass.ModuleMain,
                PluginName);
    }

}