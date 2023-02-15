package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

import java.util.GregorianCalendar;

/**
 * Single user.
 */
public class User {
    /**
     * User QQ ID.
     */
    private long ID;
    /**
     * Coin.
     */
    private long Coin;
    /**
     * Last time user signed in.
     */
    private GregorianCalendar DateLastSignIn;

    public User(long id) {
        ID = id;
        Coin = 0;
        DateLastSignIn = new GregorianCalendar();
    }

    public void giveCoin(long coin) {
        Coin += coin;
    }

    public void revokeCoin(long coin) throws CoinNotEnoughException {
        if ((Coin - coin) < 0) {
            CoinNotEnoughException CNEE = new CoinNotEnoughException("Coin not enough, own: " + Coin + ", require: " + coin);
            CNEE.Own = Coin;
            CNEE.Require = coin;
            throw CNEE;
        }
        Coin -= coin;
    }

    public long getCoin() {
        return Coin;
    }

    public long getID() {
        return ID;
    }

    public void refreshDateLastSignIn() {
        DateLastSignIn = new GregorianCalendar();
    }

    public GregorianCalendar getDateLastSignIn() {
        return DateLastSignIn;
    }
}
