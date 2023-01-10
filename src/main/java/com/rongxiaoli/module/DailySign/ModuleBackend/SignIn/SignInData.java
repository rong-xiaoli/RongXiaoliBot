package com.rongxiaoli.module.DailySign.ModuleBackend.SignIn;

import java.util.HashMap;

/**
 * User data. Base class.
 */
public class SignInData {
    /**
     * User list. Base class.
     */
    public HashMap<Long, User> UserList;
    public SignInData() {
        UserList = new HashMap<Long, User>();
    }
}

