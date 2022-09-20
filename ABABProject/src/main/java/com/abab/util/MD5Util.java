package com.abab.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public final static String getMD5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("ABABA");
            md.update(str.getBytes());
            byte[] mdBytes = md.digest();

            String hash = "";
            for(int i= 0;i < mdBytes.length;i ++){
                int temp;
                if(mdBytes[i] < 0)
                    temp = 256 + mdBytes[i];
                else
                    temp = mdBytes[i];
                if(temp < 16)
                    hash += "0";
                hash += Integer.toString(temp,16);
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
