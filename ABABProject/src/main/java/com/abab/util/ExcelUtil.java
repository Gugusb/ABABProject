package com.abab.util;

import com.abab.entity.ExcelData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil {

    public static void ExportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception{
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名称
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
            ExportExcel(data, response.getOutputStream());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //生成表格
    public static int GenerateExcel(ExcelData excelData, String path) throws Exception{
        File file = new File(path);

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        return ExportExcel(excelData, fileOutputStream);

    }

    private static int ExportExcel(ExcelData data, OutputStream outputStream){
        XSSFWorkbook wb = new XSSFWorkbook();
        int rowIndex = 0;

        try {
            String sheetName = data.getName();

            if(EmptyJudger.isEmpty(sheetName)){
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            rowIndex = WriteExcel(wb, sheet , data);

            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭wb
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rowIndex;
    }


    //写入表格
    private static int WriteExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data){
        int rowIndex = 0;
        rowIndex = WriteTitleToExcel(wb, sheet, data.getTitles());
        rowIndex = WriteRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        AutoSizeColumns(sheet, data.getTitles().size()+1);

        return rowIndex;
    }

    //设置表头
    private static int WriteTitleToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles){
        int colIndex = 0;
        int rowIndex = 0;

        Font titleFont = wb.createFont();

        //设置字体
        titleFont.setFontName("simsun");

        //设置粗体
        titleFont.setBoldweight(Short.MAX_VALUE);

        //设置字号
        titleFont.setFontHeightInPoints((short)14);

        //设置颜色
        titleFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle xssfCellStyle = wb.createCellStyle();

        //水平居中
        xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        //垂直居中
        xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        //设置图案颜色
        xssfCellStyle.setFillBackgroundColor(new XSSFColor(new Color(182, 184, 192)));

        //设置图案样式
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        xssfCellStyle.setFont(titleFont);
        setBorder(xssfCellStyle, BorderStyle.THIN, new XSSFColor(new Color(0,0,0)));

        Row titleRow = sheet.createRow(rowIndex);
        titleRow.setHeightInPoints(25);
        colIndex = 0;
        for(String field : titles){
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(xssfCellStyle);
            colIndex++;

        }
        rowIndex++;

        return rowIndex;
    }

    //设置内容
    private static int WriteRowsToExcel(XSSFWorkbook wb, Sheet sheet , List<List<Object>> rows, int rowIndex){
        int colIndex;
        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            dataRow.setHeightInPoints(25);
            colIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    //自动调整列宽
    private static void AutoSizeColumns(Sheet sheet, int columnNumber){
        for(int i=0; i < columnNumber; i++){
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i,true);
            int newWidth = (int)(sheet.getColumnWidth(i)+100);
            if(newWidth > orgWidth){
                sheet.setColumnWidth(i, newWidth);
            }else{
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    //设置边框
    private  static void setBorder(XSSFCellStyle style, BorderStyle borderStyle, XSSFColor color){
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);

        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);

    }


}
