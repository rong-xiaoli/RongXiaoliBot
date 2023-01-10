package com.rongxiaoli.module.Repeater.backend;

import com.rongxiaoli.backend.JSONHelper;
import com.rongxiaoli.backend.Log;
import com.rongxiaoli.backend.StringProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class need to check the repeating message is legal or not.
 * Consider using JSON file. Is it overkilled?
 * JSON is the simplest solution now until I finish file operation.
 * If someone is interested in it (no there will be not), you can refactor it.
 */
public class BannedWordCheck {
    private String configFile;
    /**
     * This list stores the banned word.
     */
    private List<String> BannedWordList;
    public BannedWordCheck(String conPath, String pluginName) {
        configFile = conPath + "BannedWord.txt";
        BannedWordList = new ArrayList<>();
        //Todo: Finish it.
        //Load word list.
        WordJSON wordJSON = new WordJSON();
        JSONHelper helper = new JSONHelper();
        helper.filePath = configFile;
        try {
            helper.JSONRead(wordJSON.getClass());
            BannedWordList = ((WordJSON) helper.jsonObject).BannedWordList;
        } catch (IOException e) {
            Log.Exception(e, "", Log.LogClass.File, pluginName);
            Log.WriteLog(Log.Level.Warning,
                    "Cannot load banned word due to unexpected IOException. Banned word is empty. ",
                    Log.LogClass.ModuleMain,
                    pluginName);
        }
    }
    public boolean isSuitable(String[] originalMessage) {
        // Start processing.
        // First step: check raw string.
        for(String singleString :
                originalMessage) {
            if (BannedWordList.contains(originalMessage)) {
                return false;
            }
        }

        // Second step: remove empty string and spaces.
        String[] ImpString = StringProcess.Imperative(originalMessage);
        for(String singleString :
                ImpString) {
            if (BannedWordList.contains(singleString)) {
                return false;
            }
        }

        // Finish.
        return true;

    }
}
