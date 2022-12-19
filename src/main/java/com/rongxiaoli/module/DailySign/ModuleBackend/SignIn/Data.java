package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;
//Todo: Not finished. 
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * User data. Base class.
 */
public class Data {
    /**
     * User list. Base class.
     */
    public HashMap<Long, User> UserList = new HashMap<Long, User>();
}

/**
 * Single user.
 */
class User {
    /**
     * User QQ ID.
     */
    private long ID;
    /**
     * User coin.
     */
    private long Coin;
    private GregorianCalendar DateLastSignIn;
    public User(long id) {
        ID = id;
        Coin = 0;
        DateLastSignIn = new GregorianCalendar();
    }

    public void giveCoin(long coin) {
        Coin += coin;
    }
    public void revokeCoin(long coin) throws CoinNotEnoughException{
        if ((Coin - coin) < 0) {
            CoinNotEnoughException CNEE =  new CoinNotEnoughException("Coin not enough, own: " + Coin +", require: " + coin);
            CNEE.Own = Coin;
            CNEE.Require = coin;
            throw CNEE;
        }
        Coin -= coin;
    }

    /**
     * Get user's current coin.
     * @return User's coin.
     */
    public long getCoin() {
        return Coin;
    }

    /**
     * Get user's QQ ID.
     * @return QQ ID.
     */
    public long getID() {
        return ID;
    }

    /**
     * Refresh the last sign in date.
     */
    public void RefreshDateLastSignIn() {
        DateLastSignIn = new GregorianCalendar();
    }

    /**
     * Get the last sign in date.
     * @return Last login date.
     */
    public GregorianCalendar getDateLastSignIn() {
        return DateLastSignIn;
    }
}
class CoinNotEnoughException extends RuntimeException {
    public long Own;
    public long Require;
    public CoinNotEnoughException(String exceptionInfo) {
        super(exceptionInfo);
    }
    public CoinNotEnoughException(String exceptionInfo, Exception innerException) {
        super(exceptionInfo, innerException);
    }
}