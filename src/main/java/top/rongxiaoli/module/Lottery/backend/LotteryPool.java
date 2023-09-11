package top.rongxiaoli.module.Lottery.backend;

import top.rongxiaoli.backend.Log;
import top.rongxiaoli.backend.Math.NewRandom;

import java.time.LocalDate;
import java.util.ArrayList;

public class LotteryPool {
    private NewRandom random = new NewRandom();
    private long pool, finalPool;
    private boolean isEnabled;
    private RefreshThread refreshThread;
    private ArrayList<Long> boughtFriendList;
    public LotteryPool() {
        this.refreshThread = new RefreshThread();
        isEnabled = true;
        refreshThread.start();
        this.boughtFriendList = new ArrayList<>();
    }

    public void setEnabled(boolean status) {
        this.isEnabled = status;
    }

    public boolean isFriendInList(long Friend) {
        return boughtFriendList.contains(Friend);
    }

    /**
     * Throw amount in pool.
     *
     * @param amount Amount in.
     * @return True if player win the lottery.
     */
    public boolean inPool(long amount, long Friend) {
        double result = 0;
        random = new NewRandom();
        if (boughtFriendList.contains(Friend)) {
            return false;
        }
        boughtFriendList.add(Friend);
        long multiplyRate;
        if (pool == 0) {
            multiplyRate = 1;
        } else multiplyRate = amount / pool + 1;
        result = random.nextHalfRangedGaussian(0, 1);
        if (random.nextHalfRangedGaussian(0, 1) < 3) {
            Log.WriteLog(Log.Level.Verbose, "User " + Friend + " lose, result = " + result, Log.LogClass.ModuleMain, "Lottery");
            pool += amount;
            return false;
        } else {
            Log.WriteLog(Log.Level.Verbose, "User " + Friend + " won, result = " + result, Log.LogClass.ModuleMain, "Lottery");
            finalPool = pool * multiplyRate + amount;
            boughtFriendList.clear();
            return true;
        }
    }

    /**
     * Get final award amount.
     *
     * @return
     */
    public long getFinalPool() {
        long temp = finalPool;
        finalPool = 0;
        return temp;
    }

    /**
     * Get current award amount.
     *
     * @return Current amount.
     */
    public long getPool() {
        return pool;
    }

    /**
     * Start pool refresh.
     */
    public void RefreshStart() {
        if (this.refreshThread.getState().equals(Thread.State.TERMINATED)) {
            this.refreshThread = new RefreshThread();
            refreshThread.start();
            isEnabled = true;
        } else if (this.refreshThread.getState().equals(Thread.State.NEW)) {
            refreshThread.start();
            isEnabled = true;
        }
    }

    /**
     * Stop pool refresh.
     */
    public void RefreshStop() {
        isEnabled = false;
    }

    private class RefreshThread extends Thread {
        private LocalDate date = LocalDate.now();

        @Override
        public void run() {
            while (isEnabled) {
                // Check date.
                if (date.getDayOfYear() == LocalDate.now().getDayOfYear()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.Exception(e, "LotteryPool date check thread interrupted. ",
                                Log.LogClass.Multithreading,
                                "Lottery");
                    }
                } else {
                    // Date refresh.
                    date = LocalDate.now();
                    pool = 0;
                    finalPool = 0;
                    boughtFriendList.clear();
                }
            }
        }
    }
}
