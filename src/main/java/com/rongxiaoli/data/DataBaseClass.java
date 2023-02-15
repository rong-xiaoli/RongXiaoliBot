package com.rongxiaoli.data;

import com.rongxiaoli.backend.Log;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Base class of all data.
 */
public class DataBaseClass {

    private HashMap<Long, User> UserList;

    /**
     * Database class initiate.
     */
    public DataBaseClass() {
        UserList = new HashMap<>();
//        // Init start.
//        Log.WriteLog(Log.Level.Info,
//                "Database initializing. ",
//                Log.LogClass.Data,
//                "RongXiaoliBot");
//
//        JSONHelper helper = new JSONHelper();
//        helper.filePath = RongXiaoliBot.DataPath.toString() + "/data/RongXiaoliBotData.json";
//        try {
//            helper.JSONRead(UserList.getClass());
//        } catch (IOException e) {
//            Log.Exception(e,
//                    "Database cannot initiate. Running in memory-only mode!!!",
//                    Log.LogClass.Data,
//                    "RongXiaoliBot");
//            UserList = new HashMap<>();
//        }
//
//        // File is empty.
//        if (helper.jsonObject == null) {
//            Log.WriteLog(Log.Level.Warning,
//                    "Database is null! Initiating a new one. ",
//                    Log.LogClass.Data,
//                    "RongXiaoliBot");
//            // Initiate UserList.
//            UserList = new HashMap<>();
//            // Done.
//            return;
//        }
//
//        // UserList is empty.
//        if (((HashMap<Long, User>) helper.jsonObject).isEmpty()) {
//            Log.WriteLog(Log.Level.Info,
//                    "No user is recorded. ",
//                    Log.LogClass.Data,
//                    "RongXiaoliBot");
//            return;
//        }
//        UserList = (HashMap<Long, User>) helper.jsonObject;
    }

    /**
     * Adding a new user.
     * @param userID New user ID.
     * @param newUser New user data.
     * @param moduleName Name of operation executor.
     */
    public void UserAdd(long userID, User newUser, String moduleName) {
        if (UserList.containsKey(userID)) {
            Log.WriteLog(Log.Level.Warning,
                    "User ID " + userID + " exists but creating a new one, replacing old one instead. ",
                    Log.LogClass.Data,
                    moduleName);
            UserList.replace(userID, newUser);
            return;
        }
        UserList.put(userID, newUser);
    }

    /**
     * Refresh an existing user.
     * @param userID ID of refreshing user.
     * @param newUser New user data.
     * @param moduleName Name of operation executor.
     */
    public void UserRefresh(long userID, User newUser, String moduleName) {
        if (!UserList.containsKey(userID)) {
            Log.WriteLog(Log.Level.Warning,
                    "User " + userID + " not exist. Creating a new one. ",
                    Log.LogClass.Data,
                    moduleName);
            UserList.put(userID, newUser);
            return;
        }
        UserList.replace(userID, newUser);
    }
    /**
     * Read an existing user. Null if user not exist.
     * @param userID User ID.
     * @return User data or null.
     */
    public User UserReadOrNull(long userID) {
        return UserList.getOrDefault(userID, null);
    }

    /**
     * Read an existing user. Throw an exception if user not exist.
     * @param userID User ID.
     * @return User data.
     * @throws NoSuchElementException This exception is thrown when the user is not recorded in database.
     */
    public User UserReadOrException(long userID) throws NoSuchElementException {
        if (!UserList.containsKey(userID)) throw new NoSuchElementException("User " + userID + "not exist in database. ");
        else return UserList.get(userID);
    }

    /**
     * Get a deep copy of UserList.
     * @return User list.
     */
    public HashMap<Long, User> UserListDeepCopy() {
        return (HashMap<Long, User>) UserList.clone();
    }
}