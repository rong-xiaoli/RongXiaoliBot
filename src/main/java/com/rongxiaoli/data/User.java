package com.rongxiaoli.data;

import com.rongxiaoli.backend.Log;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * User class. This is where the data stores.
 */
public class User {
    private HashMap<String, DataBlock> DataBlockMap;
    /**
     * New user constructing function.
     */
    public User() {
        this.DataBlockMap = new HashMap<>();
    }

    /**
     * Add a data block into DataBlockMap.
     * @param DataBlockName Name of data block.
     * @param NewDataBlock New data block.
     * @param moduleName Name of operation executor.
     */
    public void DataBlockAdd(String DataBlockName, DataBlock NewDataBlock, String moduleName) {
        if (DataBlockMap.containsKey(DataBlockName)) {
            Log.WriteLog(Log.Level.Info,
                    "DataBlock " + NewDataBlock + " already exists. Replacing. ",
                    Log.LogClass.Data,
                    moduleName);
            DataBlockMap.replace(DataBlockName, NewDataBlock);
            return;
        }
        DataBlockMap.put(DataBlockName, NewDataBlock);
    }

    /**
     * Refresh a data block.
     * @param DataBlockName Name of data block.
     * @param NewDataBlock New data block.
     * @param moduleName Name of operation executor.
     */
    public void DataBlockRefresh(String DataBlockName, DataBlock NewDataBlock, String moduleName) {
        if (!DataBlockMap.containsKey(DataBlockName)) {
            Log.WriteLog(Log.Level.Info,
                    "Data " + DataBlockName + "not found in database. Adding a new one. ",
                    Log.LogClass.Data,
                    moduleName);
            DataBlockMap.put(DataBlockName, NewDataBlock);
            return;
        }
        DataBlockMap.replace(DataBlockName, NewDataBlock);
    }

    /**
     * Read data block. Null if data block not exist.
     * @param DataBlockName Name of data block.
     * @return Data block or null.
     */
    public DataBlock DataBlockReadOrNull(String DataBlockName) {
        return DataBlockMap.getOrDefault(DataBlockName, null);
    }

    /**
     * Read data block. Throw an exception if data block not exist.
     * @param DataBlockName Name of data block.
     * @return Data block.
     * @throws NoSuchElementException This exception is thrown when the data block is not recorded in database.
     */
    public DataBlock DataBlockReadOrException(String DataBlockName) throws NoSuchElementException {
        if (!DataBlockMap.containsKey(DataBlockName)) throw new NoSuchElementException("Data block " + DataBlockName + " not exist in database. ");
        else return DataBlockMap.get(DataBlockName);
    }

    /**
     * Direct operation to data.
     * @param DataBlockName Name of operating data block.
     * @param DataName Name of operating data.
     * @param Data New data.
     * @param moduleName Name of operating executor.
     * @throws NoSuchElementException This exception is thrown when data block or data is not found in database.
     */
    public void DirectDataRefresh(String DataBlockName, String DataName, Object Data, String moduleName) throws NoSuchElementException {
        DataBlock targetDataBlock = DataBlockReadOrException(DataBlockName);
        targetDataBlock.DataRefresh(DataName, Data, moduleName);
    }

    /**
     * Direct read from data.
     * @param DataBlockName Name of reading data block.
     * @param DataName Name of reading data.
     * @throws NoSuchElementException This exception is thrown when data block or data is not found in database.
     */
    public Object DirectDataRead(String DataBlockName, String DataName) throws NoSuchElementException{
        DataBlock targetDataBlock = DataBlockReadOrException(DataBlockName);
        return targetDataBlock.DataReadOrException(DataName);
    }
}
