package com.rongxiaoli;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.plugin.DailySign.DailySign;
import com.rongxiaoli.plugin.Picture.PicturePlugin;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.nio.file.Path;

public final class RongXiaoliBot extends JavaPlugin {
    public static final RongXiaoliBot INSTANCE = new RongXiaoliBot();
    private static final String PluginName = "RongXiaoliBot PluginMain";
    public static final long Owner = 1751362263;
    public static boolean isPluginRunning = false;
    public static Path DataPath;
    public static Path ConfigPath;
    private RongXiaoliBot() {
        super(new JvmPluginDescriptionBuilder("com.rongxiaoli.RongXiaoliBot", "0.1.1")
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
                Log.Module.Log,
                PluginName);

        DataPath = getDataFolderPath();
        ConfigPath = getConfigFolderPath();

        //Plugin init.
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
        GlobalEventChannel.INSTANCE.registerListenerHost(new PluginListener());

        isPluginRunning = true;
        //Done!
        Log.WriteLog(Log.Level.Info,
                "Plugin initiated!",
                Log.Module.PluginMain,
                PluginName);
    }

    @Override
    public void onDisable() {
        //Disable all parts.
        Log.WriteLog(Log.Level.Info,
                "Plugin exiting...",
                Log.Module.PluginMain,
                PluginName);

        //Disabling plugins.
        PicturePlugin.isRunning = false;
        PicturePlugin.CThread.interrupt();
        Log.WriteLog(Log.Level.Debug,
                "setu Plugin shutting down. ",
                Log.Module.PluginMain,
                PluginName);

        DailySign.Shutdown();

        Log.WriteLog(Log.Level.Debug,
                "DailySign Plugin shutting down. ",
                Log.Module.PluginMain,
                PluginName);

        Log.WriteLog(Log.Level.Debug,
                "All modules disabled! ",
                Log.Module.PluginMain,
                PluginName);
    }

}