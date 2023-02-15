package com.rongxiaoli.module.Lottery.backend;

import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.Math.NewRandom;
import com.rongxiaoli.module.Lottery.Lottery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LotteryPool {
    private NewRandom random = new NewRandom();
    private long pool, finalPool;
    private boolean isEnabled;
    public void setEnabled(boolean status) {
        this.isEnabled = status;
    }
    private RefreshThread refreshThread;
    private ArrayList<Long> boughtFriendList;
    public boolean isFriendInList(long Friend) {
        return boughtFriendList.contains(Friend);
    }
    /**
     * Throw amount in pool.
     * @param amount Amount in.
     * @return True if player win the lottery.
     */
    public boolean inPool(long amount, long Friend) {
        random = new NewRandom();
        if (boughtFriendList.contains(Friend)) {
            return false;
        }
        boughtFriendList.add(Friend);
        long multiplyRate;
        if (pool == 0) {
            multiplyRate = 1;
        } else multiplyRate = amount / pool + 1;
        if (random.nextHalfRangedGaussian(0, 1) < 3) {
            pool += amount;
            return false;
        } else {
            finalPool = pool * multiplyRate + amount;
            return true;
        }
    }
    /**
     * Get final award amount.
     * @return
     */
    public long getFinalPool() {
        long temp = finalPool;
        finalPool = 0;
        return temp;
    }
    /**
     * Get current award amount.
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
    public LotteryPool() {
        this.refreshThread = new RefreshThread();
        isEnabled = true;
        refreshThread.start();
        this.boughtFriendList = new ArrayList<>();
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
                }
            }
        }
    }
}
