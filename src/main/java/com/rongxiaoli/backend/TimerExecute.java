package com.rongxiaoli.backend;

import org.jetbrains.annotations.Nullable;

public abstract class TimerExecute {
    public Object Args;
    public boolean Running = false;
    public String PluginName;
    public abstract void run(@Nullable Object args);
    public TimerExecute(String PluginName) {
        this.PluginName = PluginName;
    }

    public final class Timer extends Thread {
        public void run() {
            Running = true;
            while (Running) {
                TimerExecute.this.run(Args);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.Exception(e,"Timer interrupted.", Log.LogClass.Multithreading,PluginName);
                }
            }
        }
    }
}
