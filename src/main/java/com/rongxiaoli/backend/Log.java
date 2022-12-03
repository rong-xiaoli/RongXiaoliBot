package com.rongxiaoli.backend;

import org.jetbrains.annotations.NotNull;

public class Log {
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
    public static void Init() {
    }
    public static void WriteLog(@NotNull Level level, String msg, LogClass logClass, String PluginName) {
        switch (level) {
            case Verbose:
                System.out.println("RongXiaoliBot Logger: VER "+PluginName+"."+ logClass +": "+msg);
                break;
            case Debug:
                System.out.println("RongXiaoliBot Logger: DBG "+PluginName+"."+ logClass +": "+msg);
                break;
            case Info:
                System.out.println("RongXiaoliBot Logger: INF "+PluginName+"."+ logClass +": "+msg);
                break;
            case Warning:
                System.out.println("RongXiaoliBot Logger: WRN "+PluginName+"."+ logClass +": "+msg);
                break;
            case Error:
                System.out.println("RongXiaoliBot Logger: ERR "+PluginName+"."+ logClass +": "+msg);
                break;
        }
    }
    public static void Exception(Exception e, String eInfo, LogClass logClass, String PluginName) {
        System.out.println("RongXiaoliBot Logger: ERR Exception occurred! " + "\n" +
                e +
                "Extra info: " + eInfo + "\n" +
                "In module: " + logClass + "\n" +
                "In plugin: " + PluginName);
        e.printStackTrace();
    }
}
