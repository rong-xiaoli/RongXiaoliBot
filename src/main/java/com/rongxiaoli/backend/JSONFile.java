package com.rongxiaoli.backend;

import com.alibaba.fastjson2.JSON;

import java.io.*;

public class JSONFile {
    public Object JObject;
    public StringBuilder JString;
    public File JSONFileHandler;
    public String PluginName;

    public JSONFile(String pluginName, String FilePath, String FileName) {
        //Todo: Complete this function.
        //Check file.
        JSONFileHandler = new File(FilePath, FileName);
        //Try creating file path.
        JSONFileHandler.getParentFile().mkdirs();

        JString = new StringBuilder();

        this.PluginName = pluginName;
    }

    @Override
    public String toString() {
        JString.append(JSON.toJSON(JObject));
        return JString.toString();
    }

    public Object toObject() {
        //Judge if JSON string is empty.
        if (JString.toString().equals("")) {
            //Empty.
            Log.WriteLog(Log.Level.Debug,
                    "JSON file empty. ",
                    Log.Module.File,
                    PluginName);
            return null;
        }
        JObject = JSON.parseObject(JString.toString(), JObject.getClass());
        return JObject;
    }

    public void readFile() {
        //Declare variables.
        BufferedReader reader;
        try {
            //Start reading file.
            reader = new BufferedReader(new FileReader(JSONFileHandler));
            while (reader.ready()) {
                JString.append(reader.readLine());
            }
            reader.close();
            //End reading.

        } catch (FileNotFoundException e) {
            Log.WriteLog(Log.Level.Warning,
                    "JSON file" + JSONFileHandler.getAbsolutePath() + "not found.",
                    Log.Module.File,
                    PluginName);
            try {
                JSONFileHandler.createNewFile();
            } catch (IOException ex) {
                Log.WriteLog(Log.Level.Error,
                        "Cannot create file: " + JSONFileHandler.getAbsolutePath(),
                        Log.Module.File,
                        PluginName);
            }
            Log.WriteLog(Log.Level.Verbose,
                    "JSON file" + JSONFileHandler.getAbsolutePath() + "created. ",
                    Log.Module.File,
                    PluginName);
        } catch (IOException e) {
            Log.WriteLog(Log.Level.Error,
                    "Unexpected IOException occurred! ",
                    Log.Module.File,
                    PluginName);
            Log.Exception(e,
                    "",
                    Log.Module.File,
                    PluginName);
        }
    }

    public void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(JSONFileHandler));
            writer.write(JString.toString(), 0, JString.length());
        } catch (IOException e) {
            Log.Exception(e,
                    "",
                    Log.Module.File,
                    PluginName);
        }
    }
}
