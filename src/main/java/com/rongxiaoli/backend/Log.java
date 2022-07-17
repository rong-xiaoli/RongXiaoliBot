package com.rongxiaoli.backend;

import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

public class Log {
    private static MiraiLogger logger;
    public enum Level {
        Verbose,
        Debug,
        Info,
        Warning,
        Error,
    }
    public enum Module {
        Network,
        Log,
        File,
        Multithreading,
        PluginMain,
    }
    public static void Init(MiraiLogger l) {
        logger=l;
    }
    public static void WriteLog(@NotNull Level level, String msg, Module module, String PluginName) {
        switch (level) {
            case Verbose:
                logger.verbose(PluginName+"."+module+": "+msg);
                break;
            case Debug:
                logger.debug(PluginName+"."+module+": "+msg);
                break;
            case Info:
                logger.info(PluginName+"."+module+": "+msg);
                break;
            case Warning:
                logger.warning(PluginName+"."+module+": "+msg);
                break;
            case Error:
                logger.error(PluginName+"."+module+": "+msg);
                break;
        }
    }
    public static void Exception(Exception e, String eInfo, Module module, String PluginName) {
        logger.error("Exception occurred! " + "\n" +
                e +
                "Extra info: " + eInfo + "\n" +
                "In module: " + module + "\n" +
                "In plugin: " + PluginName);
        e.printStackTrace();
    }
}
