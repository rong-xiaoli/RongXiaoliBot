package com.rongxiaoli.module.Repeater.backend;

import com.rongxiaoli.backend.StringProcess;

import java.io.File;
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
    public BannedWordCheck(String conPath) {
        configFile = conPath + "BannedWord.txt";
        BannedWordList = new ArrayList<>();
        //Todo: Finish it.
        //Load word list.
        File bannedWordFile = new File(configFile);
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
