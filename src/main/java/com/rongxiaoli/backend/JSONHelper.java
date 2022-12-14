package com.rongxiaoli.backend;

import com.google.gson.Gson;

import java.io.*;

/**
 * To use this class to read, first fill the file path, then call func: JSONRead(Class objClass), finally get the jsonObject.
 * To use this class to write, first fill the file path, next set the jsonObject, finally call func: JSONSave().
 */
public class JSONHelper {
    public Object jsonObject;
    public String filePath;

    /**
     * Save JSON to file.
     * @throws IOException Exception thrown when unexpected IOException occurred.
     */
    public void JSONSave() throws IOException {
        if (filePath.equals(null)) {
            throw new IllegalArgumentException("filePath cannot be null! ");
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(jsonObject);
        File jsonFile = new File(filePath);
        File jsonFileRoot = jsonFile.getParentFile();
        if (jsonFileRoot.mkdirs()) {
            throw new IOException("Cannot create path: " + jsonFileRoot.getAbsolutePath());
        }
        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
        }
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    /**
     * Read JSON from file and convert to Object.
     * @throws IOException Exception thrown when unexpected IOException occurred.
     */
    public void JSONRead(Class objClass) throws IOException {
        if (filePath.equals(null)) throw new IllegalArgumentException("filePath cannot be null! ");
        Gson gson = new Gson();
        File jsonFile = new File(filePath);
        File jsonFileRoot = jsonFile.getParentFile();
        if (!jsonFileRoot.exists()) {
            if (jsonFileRoot.mkdirs()) {
                throw new IOException("Cannot create path: " + jsonFileRoot.getAbsolutePath());
            }
        }
        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
        }
        InputStream inStream = new FileInputStream(jsonFile);
        byte[] jsonBytes = new byte[inStream.available()];
        inStream.read(jsonBytes);
        jsonObject = gson.fromJson(new String(jsonBytes), objClass);
    }
}