package com.rongxiaoli.module.Repeater.backend;

import java.util.ArrayList;
import java.util.List;

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

    }
    public boolean isSuitable(String[] originalMessage) {
        //Todo: Finish it.
        return false;
    }
}
