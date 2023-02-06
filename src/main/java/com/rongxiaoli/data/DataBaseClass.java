package com.rongxiaoli.data;

import com.rongxiaoli.RongXiaoliBot;

import java.util.HashMap;

public class DataBaseClass {
    /**
     * This is the path where data file stores.
     */
    public String dataFilePath;
    /**
     * Hash map of Modules.
     */
    public HashMap<String, Object> ModuleDataCollection;

    /**
     * This is the base class of the
     */
    public DataBaseClass() {
        dataFilePath = RongXiaoliBot.DataPath + "/data/data.json";
        ModuleDataCollection = new HashMap<>();
    }

    /**
     * Abstract class of each ModuleData.
     */
    public abstract class ModuleData {
        /**
         * The name of the module.
         */
        private String moduleName;
        /**
         * Hash map of module data.
         */
        private HashMap<Long, Object> DataList;

        /**
         * Get the name of the module data.
         *
         * @return Module name.
         */
        public abstract String getModuleName();

        /**
         * This function is to initiate data.
         *
         * @param dataList Data list.
         */
        public abstract void dataInit(HashMap<Long, Object> dataList);

        /**
         * This function is used to read the user data.
         *
         * @param qqID User ID.
         * @return The data of the user.
         */
        public abstract Object dataRead(long qqID);

        public abstract void dataOperation(long qqID, Object newUserData) throws UnAuthorizedDataOperationException;

        /**
         * This exception is thrown when unauthorized data operation is performed.
         */
        public class UnAuthorizedDataOperationException extends Exception {
            public UnAuthorizedDataOperationException() {
                super();
            }

            public UnAuthorizedDataOperationException(String message) {
                super(message);
            }

            public UnAuthorizedDataOperationException(String message, Exception innerException) {
                super(message, innerException);
            }
        }
    }
}
