package com.rongxiaoli.data;

import com.rongxiaoli.backend.Log;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Data block class. This is where module data stores.
 */
public class DataBlock {
    private HashMap<String, Object> DataMap;
    public DataBlock() {
        this.DataMap = new HashMap<>();
    }
    /**
     * Read data. Null if data not exist.
     * @param DataName Name of data.
     * @return Data or null.
     */
    public Object DataReadOrNull(String DataName) {
        return DataMap.getOrDefault(DataName, null);
    }
    /**
     * Read data. Throw an exception if data block not exist.
     * @param DataName Name of data.
     * @return Data.
     * @throws NoSuchElementException This exception is thrown when the data is not recorded in database.
     */
    public Object DataReadOrException(String DataName) throws NoSuchElementException {
        if (!DataMap.containsKey(DataName)) throw new NoSuchElementException("Data block " + DataName + " not exist in database. ");
        else return DataMap.get(DataName);
    }
    /**
     * Add a data into DataBlockMap.
     * @param DataName Name of data.
     * @param NewData New data.
     * @param moduleName Name of operation executor.
     */
    public void DataAdd(String DataName, Object NewData, String moduleName) {
        if (DataMap.containsKey(DataName)) {
            Log.WriteLog(Log.Level.Info,
                    "DataBlock " + NewData + " already exists. Replacing. ",
                    Log.LogClass.Data,
                    moduleName);
            DataMap.replace(DataName, NewData);
            return;
        }
        DataMap.put(DataName, NewData);
    }
    /**
     * Refresh a data.
     * @param DataName Name of data.
     * @param NewData New data.
     * @param moduleName Name of operation executor.
     */
    public void DataRefresh(String DataName, Object NewData, String moduleName) {
        if (!DataMap.containsKey(DataName)) {
            Log.WriteLog(Log.Level.Info,
                    "Data " + DataName + "not found in database. Adding a new one. ",
                    Log.LogClass.Data,
                    moduleName);
            DataMap.put(DataName, NewData);
            return;
        }
        DataMap.replace(DataName, NewData);
    }
}
