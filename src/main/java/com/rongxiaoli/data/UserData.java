package com.rongxiaoli.data;

import java.util.HashMap;

public class UserData {
    /**
     * User list. Base class.
     */
    public HashMap<Long, User> UserList;
    public UserData() {
        UserList = new HashMap<Long, User>();
    }
}
