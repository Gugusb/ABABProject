package com.abab.util;
import com.abab.entity.*;

import java.util.Arrays;
import java.util.List;

public class ExcelDatasProduce {
    private static String sheetAuditor = "Auditor";

    private static String sheetComment = "Comment";

    private static String sheetBullet = "Bullet";

    private static String sheetLogs = "Logs";

    private static String[] titleAuditor = {"auditorid", "auditorname", "password", "auditorauthor", "phone",
            "gender", "birthday", "auditorrole", "auditoravatar", "memo"};

    private static String[] titleComment = {"id", "content", "userid", "videoid", "commenttime" ,"memo"};

    private static String[] titleBullet = {"id", "content", "userid", "videoid", "memo"};

    private static String[] titleLogs = {"id", "userid", "username", "matter", "optime"};

    private static String setSheetName(Integer index) {
        String sheetName = null;

        if(ConstUtil.EXCEL_COMMENT_INDEX == index){
            sheetName = sheetComment;
        }
        else if(ConstUtil.EXCEL_AUDITOR_INDEX == index){
            sheetName = sheetAuditor;
        }
        else if(ConstUtil.EXCEL_BULLET_INDEX == index){
            sheetName = sheetBullet;
        }
        else if(ConstUtil.EXCEL_LOG_INDEX == index){
            sheetName = sheetLogs;
        }

        return sheetName;
    }

    private static String[] setTitleName(Integer index){
        String[] titleName = null;

        if(ConstUtil.EXCEL_COMMENT_INDEX == index){
            titleName = titleComment;
        }
        else if(ConstUtil.EXCEL_AUDITOR_INDEX == index){
            titleName = titleAuditor;
        }
        else if(ConstUtil.EXCEL_BULLET_INDEX == index){
            titleName = titleBullet;
        }
        else if(ConstUtil.EXCEL_LOG_INDEX == index){
            titleName = titleLogs;
        }

        return titleName;
    }

    private static String CreateExcel(ExcelData excelData){
        int rowIndex = 0;

        if(!EmptyJudger.isEmpty(excelData.getTitles()) && !EmptyJudger.isEmpty(excelData.getName()) && !EmptyJudger.isEmpty(excelData.getRows())){
            try {
                String file_name = ExcelAddress.FILE_PATH + ExcelAddress.createFileName();
                rowIndex = ExcelUtil.GenerateExcel(excelData, file_name);
                return file_name;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static List<String> toTitles(Integer index){
        String[] sheetName = setTitleName(index);

        List<String> titles = Arrays.asList(sheetName);

        return titles;
    }

    public static String ProducerExcel(Integer index, Object data) {

        ExcelData excelData = new ExcelData();

        String sheetName = null;
        List<String> titles = null;
        List<List<Object>> rows = null;

        sheetName = setSheetName(index);
        titles = toTitles(index);

        if(ConstUtil.EXCEL_COMMENT_INDEX == index){
            BiliComment cur = new BiliComment();
            rows = cur.toRows((List<BiliComment>) data);
        }
        else if(ConstUtil.EXCEL_AUDITOR_INDEX == index){
            BiliAuditor cur = new BiliAuditor();
            rows = cur.toRows((List<BiliAuditor>) data);
        }
        else if(ConstUtil.EXCEL_BULLET_INDEX == index){
            BiliBullet cur = new BiliBullet();
            rows = cur.toRows((List<BiliBullet>) data);
        }
        else if(ConstUtil.EXCEL_LOG_INDEX == index){
            BiliLogs cur = new BiliLogs();
            rows = cur.toRows((List<BiliLogs>) data);
        }


        excelData.setName(sheetName);
        excelData.setTitles(titles);
        excelData.setRows(rows);

        return CreateExcel(excelData);
    }

}
