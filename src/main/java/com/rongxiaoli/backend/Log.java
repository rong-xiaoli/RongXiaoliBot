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
    public enum Module {
        Network,
        Data,
        Log,
        File,
        Multithreading,
        PluginMain,
    }
    public static void Init() {
    }
    public static void WriteLog(@NotNull Level level, String msg, Module module, String PluginName) {
        switch (level) {
            case Verbose:
                System.out.println("RongXiaoliBot Logger: VER "+PluginName+"."+module+": "+msg);
                break;
            case Debug:
                System.out.println("RongXiaoliBot Logger: DBG "+PluginName+"."+module+": "+msg);
                break;
            case Info:
                System.out.println("RongXiaoliBot Logger: INF "+PluginName+"."+module+": "+msg);
                break;
            case Warning:
                System.out.println("RongXiaoliBot Logger: WRN "+PluginName+"."+module+": "+msg);
                break;
            case Error:
                System.out.println("RongXiaoliBot Logger: ERR "+PluginName+"."+module+": "+msg);
                break;
        }
    }
    public static void Exception(Exception e, String eInfo, Module module, String PluginName) {
        System.out.println("RongXiaoliBot Logger: ERR Exception occurred! " + "\n" +
                e +
                "Extra info: " + eInfo + "\n" +
                "In module: " + module + "\n" +
                "In plugin: " + PluginName);
        e.printStackTrace();
    }
}
