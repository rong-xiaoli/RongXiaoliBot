package com.rongxiaoli.plugin.DailySign.ModuleBackend.SignIn;

import com.rongxiaoli.plugin.DailySign.ModuleBackend.CustomString.CustomStringList;

import java.util.concurrent.CopyOnWriteArrayList;

//Todo: Complete this class.
public class SignObjectList {
    public CopyOnWriteArrayList<FriendSignObject> FriendSignList;
    public CopyOnWriteArrayList<GroupSignObject> GroupSignList;

    public static class SignObjectOperation {
    }
    /**
     * Friend sign object.
     */
    public static class FriendSignObject {
        public long QQID;
        public static class SignTime {
            public int Year;
            public int Month;
            public int Day;
        }
        public SignTime LastSignTime;
        public long Coin;
    }
    /**
     * Group member sign object.
     */
    public static class GroupSignObject {
        public int Position;
        public long GroupID;
        public CustomStringList StringList;
    }
}
