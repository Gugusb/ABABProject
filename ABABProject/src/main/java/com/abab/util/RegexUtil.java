package com.abab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String getImageRelativePath(String absolutePath){
        String pattern="//photos//.*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(absolutePath);
        if(m.find()){
            return m.group();
        }
        return null;
    }
    public static String getVideoRelativePath(String absolutePath){
        String pattern="\\/videos\\/.*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(absolutePath);
        if(m.find()){
            return m.group();
        }
        return null;
    }
}
