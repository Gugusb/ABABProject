package com.abab.util;

import java.util.Date;
import java.util.UUID;

public class UUIDMaker {
    public static String generationAV() {
        UUID uuid = UUID.randomUUID();
        int maxSize = 11;
        if(uuid.toString().length() < 11){
            maxSize = uuid.toString().length();
        }
        return "AV" + uuid.toString().substring(3, maxSize);
    }

    public static String generationFileName() {
        Date date = new Date();
        String ds = date.toString()
            .replace(" ", "")
            .replace(":", "")
            .replace("/", "");
        UUID uuid = UUID.randomUUID();
        return ds + uuid;
    }
}
