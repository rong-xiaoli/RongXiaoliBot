package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

/**
 * This class is for typedef.
 */
public class SignObjectList {

    /**
     * Friend sign object.
     */
    public static class FriendSignObject {
        public long QQID = 0;
        public static class SignTime {
            public int Year = 0;
            public int Month = 0;
            public int Day = 0;
        }
        public SignTime LastSignTime = new SignTime();
    }
    /**
     * Group member sign object.
     */
    public static class GroupSignObject {
        public int Position = 0;
        public long GroupID = 0;
    }
}
