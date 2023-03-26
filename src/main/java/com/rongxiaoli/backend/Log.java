package com.rongxiaoli.backend;

import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

public class Log {
    private static MiraiLogger logger;
    private static boolean isMiraiLoggerMode = false;

    public static void Init(MiraiLogger log) {
        if (log != null) {
            logger = log;
            isMiraiLoggerMode = true;
        }

    }

    public static void WriteLog(@NotNull Level level, String msg, LogClass logClass, String PluginName) {
        switch (level) {
            case Verbose:
                if (isMiraiLoggerMode) logger.verbose(PluginName + "." + logClass + ": " + msg);
                else System.out.println("RongXiaoliBot Logger: VER " + PluginName + "." + logClass + ": " + msg);
                break;
            case Debug:
                if (isMiraiLoggerMode) logger.debug(PluginName + "." + logClass + ": " + msg);
                else System.out.println("RongXiaoliBot Logger: DBG " + PluginName + "." + logClass + ": " + msg);
                break;
            case Info:
                if (isMiraiLoggerMode) logger.info(PluginName + "." + logClass + ": " + msg);
                else System.out.println("RongXiaoliBot Logger: INF " + PluginName + "." + logClass + ": " + msg);
                break;
            case Warning:
                if (isMiraiLoggerMode) logger.warning(PluginName + "." + logClass + ": " + msg);
                else System.out.println("RongXiaoliBot Logger: WRN " + PluginName + "." + logClass + ": " + msg);
                break;
            case Error:
                if (isMiraiLoggerMode) logger.error(PluginName + "." + logClass + ": " + msg);
                else System.out.println("RongXiaoliBot Logger: ERR " + PluginName + "." + logClass + ": " + msg);
                break;
        }
    }

    public static void Exception(Exception e, String eInfo, LogClass logClass, String PluginName) {
        if (isMiraiLoggerMode) {
            logger.error("Exception occurred!" + "\n" +
                    "In module:" + logClass + "\n" +
                    "In plugin:" + PluginName);
            logger.error(eInfo, e);
            return;
        }
        System.out.println("RongXiaoliBot Logger: ERR Exception occurred! " + "\n" +
                e +
                "Extra info: " + eInfo + "\n" +
                "In module: " + logClass + "\n" +
                "In plugin: " + PluginName);
        e.printStackTrace();
    }

    public enum Level {
        Verbose,
        Debug,
        Info,
        Warning,
        Error,
    }

    public enum LogClass {
        Network,
        Data,
        Log,
        File,
        Multithreading,
        ModuleMain,
    }
}
