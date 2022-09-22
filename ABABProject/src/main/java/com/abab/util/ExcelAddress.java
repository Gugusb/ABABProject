package com.abab.util;

import java.util.Random;

public class ExcelAddress {
    /*
    * 生成文件存放路径
    */
    //public static final String FILE_PATH = "D:\\desk\\cocotest\\";
    public static final String FILE_PATH = "/home/ubuntu/excels/";


    /*
    *文件的默认名称
    */

    public static final String FILE_NAME = "Test.xls";

    public static String createFileName(){
        Random random = new Random();

        int length  = 10;
        String numstr = "123456789";
        String chastr_b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
        String chastr_s = "abcdefghijklmnopqrstuvwxyz";
        String specil = "_";

        String base = numstr + chastr_b + chastr_s + specil;

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(chastr_b.charAt(random.nextInt(chastr_b.length())));
        for(int i =0 ;i <length-2;i++){
            int num = random.nextInt(base.length());
            stringBuffer.append(base.charAt(num));
        }
        //追加最后一个数字
        stringBuffer.append(numstr.charAt(random.nextInt(numstr.length())));

        return stringBuffer.toString() + ".xls";
    }

}
