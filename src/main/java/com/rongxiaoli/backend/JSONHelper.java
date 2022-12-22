package com.rongxiaoli.backend;

import com.google.gson.Gson;

import java.io.*;

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
        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
        }
        InputStream inStream = new FileInputStream(jsonFile);
        byte[] jsonBytes = new byte[inStream.available()];
        inStream.read(jsonBytes);
        jsonObject = gson.fromJson(new String(jsonBytes), objClass);
    }
}