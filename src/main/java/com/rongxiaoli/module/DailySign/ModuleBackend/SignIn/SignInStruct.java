package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

import com.google.gson.internal.LinkedTreeMap;

import java.time.LocalDateTime;

public class SignInStruct {
    private long Coin = 0;
    private DateLastSignIn dateLastSignIn;
    public SignInStruct(DateLastSignIn sign, long coin) {
        dateLastSignIn = sign;
        Coin = coin;
    }

    public SignInStruct(DateLastSignIn sign) {
        dateLastSignIn = sign;
        Coin = 1;
    }

    public SignInStruct(LocalDateTime time, long coin) {
        dateLastSignIn = new DateLastSignIn(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
        Coin = coin;
    }

    public SignInStruct(LocalDateTime time) {
        dateLastSignIn.fromDateTime(time);
        Coin = 1;
    }

    public SignInStruct(LinkedTreeMap<String, Object> map) {
        int year = (int) map.get("year"),
                month = (int) map.get("month"),
                day = (int) map.get("day"),
                hour = (int) map.get("hour"),
                minute = (int) map.get("minute"),
                second = (int) map.get("second"),
                nano = (int) map.get("nano");
        refreshDateTime(LocalDateTime.of(year, month, day, hour, minute, second, nano));
    }

    public static SignInStruct fromMap(LinkedTreeMap<String, Object> source) {
        long coin;
        double year, month, day, hour, minute, second, nano;
        coin = Double.valueOf((double) source.get("Coin")).longValue(); // WTF?? Simple way???
        LinkedTreeMap<String, Object> subMap = (LinkedTreeMap<String, Object>) source.get("dateLastSignIn");
        //year = (int) ((double) subMap.get("year"));// bug.
        year = (Double) subMap.get("year");
        month = (Double) subMap.get("month");
        day = (Double) subMap.get("day");
        hour = (Double) subMap.get("hour");
        minute = (Double) subMap.get("minute");
        second = (Double) subMap.get("second");
        nano = (Double) subMap.get("nano");
        int iYear = Double.valueOf(year).intValue(),
                iMonth = Double.valueOf(month).intValue(),
                iDay = Double.valueOf(day).intValue(),
                iHour = Double.valueOf(hour).intValue(),
                iMinute = Double.valueOf(minute).intValue(),
                iSecond = Double.valueOf(second).intValue(),
                iNano = Double.valueOf(nano).intValue();
        return new SignInStruct(LocalDateTime.of(iYear, iMonth, iDay, iHour, iMinute, iSecond, iNano), coin);
    }

    public LocalDateTime toDateTime() {
        return LocalDateTime.of(dateLastSignIn.year, dateLastSignIn.month, dateLastSignIn.day, dateLastSignIn.hour, dateLastSignIn.minute, dateLastSignIn.second, dateLastSignIn.nano);
    }

    public void refreshDateTime(LocalDateTime dateTime) {
        dateLastSignIn.year = dateTime.getYear();
        dateLastSignIn.month = dateTime.getMonthValue();
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

    public static class DateLastSignIn {
        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private int second;
        private int nano;

        public DateLastSignIn(int year, int month, int day, int hour, int minute, int second, int nano) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.nano = nano;
        }

        public LocalDateTime toDateTime() {
            return LocalDateTime.of(year, month, day, hour, minute, second, nano);
        }

        public void fromDateTime(LocalDateTime dateTime) {
            this.year = dateTime.getYear();
            this.month = dateTime.getMonthValue();
            this.day = dateTime.getDayOfMonth();
            this.hour = dateTime.getHour();
            this.minute = dateTime.getMinute();
            this.second = dateTime.getSecond();
            this.nano = dateTime.getNano();
        }
    }
}

