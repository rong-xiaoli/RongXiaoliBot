package com.rongxiaoli.data.backend.DailySign;

public class CoinNotEnoughException extends Exception {
    public long Own;
    public long Require;
    public CoinNotEnoughException(String exceptionInfo) {
        super(exceptionInfo);
    }
    public CoinNotEnoughException(String exceptionInfo, Exception innerException) {
        super(exceptionInfo, innerException);
    }
}
