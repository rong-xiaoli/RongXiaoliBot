package com.rongxiaoli.backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringProcess {
    /**
     * Turn string array into command string array.
     * @param originalMessage Original message needed to process
     * @return Imperative string.
     */
    public static String[] Imperative(String[] originalMessage) {
        // Start processing.
        String[] message = originalMessage.clone();
        // 0-width.
        if (message.length == 0) return null;
        // Remove empty space.
        List<String> messageWordList = Arrays.asList(message);
        messageWordList.removeAll(Collections.singletonList(" "));
        // Remove new lines.
        messageWordList.removeAll(Collections.singletonList("\n"));
        // Done.
        message = messageWordList.toArray(new String[0]);
        return message;
    }
}
