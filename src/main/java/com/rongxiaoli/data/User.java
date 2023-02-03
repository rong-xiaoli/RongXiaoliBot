package com.rongxiaoli.data;

import com.rongxiaoli.data.backend.DailySign.CoinNotEnoughException;

import java.util.GregorianCalendar;

/**
 * Single user.
 */
public class User {
    public long ID;
    public class DailySignData {
        /**
         * Coin.
         */
        private long Coin;
        /**
         * Last time user signed in.
         */
        private GregorianCalendar DateLastSignIn;

        public DailySignData(long id) {
            ID = id;
            Coin = 0;
            DateLastSignIn = new GregorianCalendar();
        }
        public void giveCoin(long coin) {
            Coin += coin;
        }
        public void revokeCoin(long coin) throws CoinNotEnoughException {
            if ((Coin - coin) < 0) {
                CoinNotEnoughException CNEE =  new CoinNotEnoughException("Coin not enough, own: " + Coin +", require: " + coin);
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
    public User(long id) {
        ID = id;
    }
}
