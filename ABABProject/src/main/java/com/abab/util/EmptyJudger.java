package com.abab.util;

public class EmptyJudger {
    public static boolean isEmpty(Object object){
        if(object == null){
            return true;
        }else if(object.toString() == ""){
            return true;
        }
        return false;
    }
}
