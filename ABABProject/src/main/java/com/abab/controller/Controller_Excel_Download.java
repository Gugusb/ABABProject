package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliComment;

import com.abab.entity.BiliVideo;
import com.abab.entity.ExcelData;
import com.abab.service.CommentService;

import com.abab.util.ExcelAddress;
import com.abab.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller_Excel_Download {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/excel/Test", method = RequestMethod.POST)
    public ServerResponse<List<BiliComment>> test(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<List<BiliComment>> serverResponse = commentService.getCommentsByVideoIdService(biliVideo);

        List<BiliComment> list=serverResponse.getData();

        int rowIndex = 0;
        ExcelData excelData = new ExcelData();
        excelData.setName("hello");

        List<String> titles = new ArrayList<>();
        titles.add("id");
        titles.add("content");
        titles.add("userid");
        titles.add("videoid");
        titles.add("commenttime");
        titles.add("memo");

        excelData.setTitles(titles);

        List<List<Object>> rows = new ArrayList<>();
        for(int i=0,length = list.size(); i < length; i++){
            BiliComment biliComment = list.get(i);
            List<Object> row = new ArrayList<>();
            row.add(biliComment.getId());
            row.add(biliComment.getContent());
            row.add(biliComment.getUserid());
            row.add(biliComment.getVideoid());
            row.add(biliComment.getCommenttime());
            row.add(biliComment.getMemo());

            rows.add(row);
        }
        excelData.setRows(rows);

        try {
            rowIndex = ExcelUtil.GenerateExcel(excelData, ExcelAddress.FILE_PATH + ExcelAddress.FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServerResponse.createRespBySuccess();
    }

}
