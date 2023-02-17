package com.rongxiaoli.module.Repeater.backend;

import com.rongxiaoli.backend.JSONHelper;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.StringProcess;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class need to check the repeating message is legal or not.
 * Consider using JSON file. Is it overkilled?
 * JSON is the simplest solution now until I finish file operation.
 * If someone is interested in it (no there will be not), you can refactor it.
 */
public class BannedWordCheck {
    private final String configFile;
    /**
     * This list stores the banned word.
     */
    private CopyOnWriteArrayList<String> BannedWordList;
    private final String PluginName;
    public BannedWordCheck(String conPath, String pluginName) {
        // Load word list on init.
        configFile = conPath + "BannedWord.txt";
        BannedWordList = new CopyOnWriteArrayList<>();
        this.PluginName = pluginName;
        //Load word list.
        WordJSON wordJSON = new WordJSON();
        JSONHelper helper = new JSONHelper("[BannedWordCheck]");
        helper.filePath = configFile;
        try {
            helper.JSONRead(wordJSON.getClass());
            if (helper.jsonObject == null) {
                BannedWordList = new CopyOnWriteArrayList<>();
            } else  {
                BannedWordList = ((WordJSON) helper.jsonObject).BannedWordList;
            }
        } catch (IOException e) {
            Log.Exception(e, "", Log.LogClass.File, pluginName);
            Log.WriteLog(Log.Level.Warning,
                    "Cannot load banned word due to unexpected IOException. Banned word is empty. ",
                    Log.LogClass.ModuleMain,
                    pluginName);
        }
    }
    public void saveBannedWord() {
        JSONHelper helper = new JSONHelper("[BannedWordCheck]");
        WordJSON json = new WordJSON();
        json.BannedWordList = this.BannedWordList;
        helper.jsonObject = json;
        helper.filePath = configFile;
        try {
            helper.JSONSave();
        } catch (IOException e) {
            Log.Exception(e, "", Log.LogClass.File, PluginName);
        }
    }
    public boolean isSuitable(String[] originalMessage) {
        // Start processing.
        // Clone target string.
        String[] message = originalMessage.clone();
        // First step: check raw string.
        for(String singleString :
                message) {
            if (BannedWordList.contains(singleString)) {
                return false;
            }
        }

        // Second step: remove empty string and spaces.
        String[] ImpString = StringProcess.Imperative(message);
        if (ImpString == null) {
            return true;
        }
        for(String singleString :
                ImpString) {
            if (BannedWordList.contains(singleString)) {
                return false;
            }
        }
        // Third step: append all strings into one single string.
        String removeSpace = StringProcess.RemoveSpace(message);
        if (BannedWordList.contains(removeSpace)) return false;
        // Finish.
        return true;

    }

    /**
     * Add a ban word.
     * @param bannedWord The key word.
     * @return True if the banned word already exists. False if the banned word is not in the list.
     */
    public boolean addBannedWord(String bannedWord) {
        for(String singleBannedWord :
                BannedWordList) {
            if (Objects.equals(singleBannedWord, bannedWord)) {
                return true;
            }
        }
        this.BannedWordList.add(bannedWord);
        return false;
    }
    public boolean delBannedWord(String bannedWord) {
        for(String singleString :
                BannedWordList) {
            if (singleString.equals(bannedWord)) {
                this.BannedWordList.remove(singleString);
                return true;
            }

        }
        return false;
    }
    public String getBannedWord() {
        if (BannedWordList.isEmpty()) return "无禁用词";
        StringBuilder stb = new StringBuilder();
        stb.append("禁用词：\n");
        for(String singleBannedWord :
                BannedWordList) {
            stb.append(singleBannedWord + "\n");
        }
        return stb.toString();
    }
}
