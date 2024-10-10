package com.rongxiaoli.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * To use this class to read, first fill the file path, then call func: JSONRead(Class objClass), finally get and cast the jsonObject.
 * To use this class to write, first fill the file path, next set the jsonObject, finally call func: JSONSave().
 */
public class JSONHelper {
    public Object jsonObject;
    public String filePath;
    private String PluginName;

    public JSONHelper(String name) {
        PluginName = name;
    }

    /**
     * Save JSON to file.
     *
     * @throws IOException Exception thrown when unexpected IOException occurred.
     */
    public void JSONSave() throws IOException {
        if (filePath.equals(null)) throw new IllegalArgumentException("filePath cannot be null! ");

        // JSON converter builder.
        GsonBuilder builder = new GsonBuilder();
        // Add Adaptor below.

//        V0.2.0-Hotfix2 removed:
//        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdaptor());
//        builder.setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE);

        builder.registerTypeAdapter(Long.class, new JSONAdaptor.LongTypeAdapter());
        // Finished. Building.

        Gson gson = builder.create();
        String jsonString = "";
        try {
            jsonString = gson.toJson(jsonObject);
        } catch (Exception e) {
            Log.Exception(e, "Cannot read JSON string. ",
                    Log.LogClass.File,
                    PluginName);
        }
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
     *
     * @throws IOException Exception thrown when unexpected IOException occurred.
     */
    public void JSONRead(Class objClass) throws IOException {
        if (filePath.equals(null)) throw new IllegalArgumentException("filePath cannot be null! ");

        // JSON converter builder.
        GsonBuilder builder = new GsonBuilder();
        // Add Adaptor below.

//        V0.2.0-Hotfix2 removed:
//        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdaptor());
//        builder.setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE);
        builder.registerTypeAdapter(Long.class, new JSONAdaptor.LongTypeAdapter());
        // Finished. Building.

        Gson gson = builder.create();
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
        try {
            jsonObject = gson.fromJson(new String(jsonBytes), objClass);
        } catch (Exception e) {
            Log.Exception(e, "Cannot read JSON string. ",
                    Log.LogClass.File,
                    PluginName);
        }

    }
}