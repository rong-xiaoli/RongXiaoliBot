package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

public class CoinNotEnoughException extends RuntimeException {
    public long Own;
    public long Require;

    public CoinNotEnoughException(String exceptionInfo) {
        super(exceptionInfo);
    }

    public CoinNotEnoughException(String exceptionInfo, Exception innerException) {
        super(exceptionInfo, innerException);
    }
}
