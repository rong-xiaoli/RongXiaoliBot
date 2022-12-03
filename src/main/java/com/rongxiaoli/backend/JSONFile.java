package com.rongxiaoli.backend;

import com.alibaba.fastjson2.JSON;

import java.io.*;

public class JSONFile {
    public Object JObject;
    public StringBuilder JString;
    public File JSONFileHandler;
    public String PluginName;

    public JSONFile(String pluginName, String FilePath, String FileName) {
        //Check file.
        JSONFileHandler = new File(FilePath, FileName);
        //Try creating file path.
        JSONFileHandler.getParentFile().mkdirs();
        if (!JSONFileHandler.exists()) {
            try {
                JSONFileHandler.createNewFile();
            } catch (IOException e) {
                Log.Exception(e,
                        "",
                        Log.LogClass.File,
                        PluginName);
            }
        }

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
                    Log.LogClass.File,
                    PluginName);
            return null;
        }
        JObject = JSON.parseObject(JString.toString(), JObject.getClass());
        return JObject;
    }

    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(JSONFileHandler));
            String buf;
            while ((buf = reader.readLine()) != null) {
                JString.append(buf);
            }
        } catch (IOException e) {
            Log.Exception(e,
                    "",
                    Log.LogClass.File,
                    PluginName);
        }
    }

    public void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(JSONFileHandler));
            writer.write(JString.toString());
            writer.flush();
        } catch (IOException e) {
            Log.Exception(e,
                    "",
                    Log.LogClass.File,
                    PluginName);
        }
    }
}
