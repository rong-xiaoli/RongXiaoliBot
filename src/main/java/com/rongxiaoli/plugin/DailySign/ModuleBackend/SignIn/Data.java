package com.rongxiaoli.plugin.DailySign.ModuleBackend.SignIn;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {
    public List<SignObjectList.FriendSignObject> FriendSignList = new CopyOnWriteArrayList<>();
    public List<SignObjectList.GroupSignObject> GroupSignList = new CopyOnWriteArrayList<>();
}
