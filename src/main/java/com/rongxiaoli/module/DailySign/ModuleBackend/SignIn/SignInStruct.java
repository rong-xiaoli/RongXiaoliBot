package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

import java.time.LocalDateTime;

public class SignInStruct {
    private long Coin = 0;
    private DateLastSignIn dateLastSignIn;
    public static class DateLastSignIn {
        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private int second;
        private int nano;
        public LocalDateTime toDateTime() {
            return LocalDateTime.of(year, month, day, hour, minute, second, nano);
        }
        public void fromDateTime(LocalDateTime dateTime) {
            this.year = dateTime.getYear();
            this.month = dateTime.getDayOfMonth();
            this.day = dateTime.getDayOfMonth();
            this.hour = dateTime.getHour();
            this.minute = dateTime.getMinute();
            this.second = dateTime.getSecond();
            this.nano = dateTime.getNano();
        }
        public DateLastSignIn(int year, int month, int day, int hour, int minute, int second, int nano) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.nano = nano;
        }
    }
    public SignInStruct(DateLastSignIn sign, long coin) {
        dateLastSignIn = sign;
        Coin = coin;
    }
    public SignInStruct(DateLastSignIn sign) {
        dateLastSignIn = sign;
        Coin = 1;
    }
    public SignInStruct(LocalDateTime time, long coin) {
        dateLastSignIn.fromDateTime(time);
        Coin = coin;
    }
    public SignInStruct(LocalDateTime time) {
        dateLastSignIn.fromDateTime(time);
        Coin = 1;
    }
    public LocalDateTime toDateTime() {
        return LocalDateTime.of(dateLastSignIn.year, dateLastSignIn.month, dateLastSignIn.day, dateLastSignIn.hour, dateLastSignIn.minute, dateLastSignIn.second, dateLastSignIn.nano);
    }
    public void refreshDateTime(LocalDateTime dateTime) {
        dateLastSignIn.year = dateTime.getYear();
        dateLastSignIn.month = dateTime.getDayOfMonth();
        dateLastSignIn.day = dateTime.getDayOfMonth();
        dateLastSignIn.hour = dateTime.getHour();
        dateLastSignIn.minute = dateTime.getMinute();
        dateLastSignIn.second = dateTime.getSecond();
        dateLastSignIn.nano = dateTime.getNano();
    }

    public long getCoin() {
        return Coin;
    }
    public void giveCoin(long amount) {
        this.Coin += amount;
    }
    public void revokeCoin(long amount) {
        this.Coin -= amount;
    }
}

