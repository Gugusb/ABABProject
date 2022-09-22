package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliComment;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.CommentService;
import com.abab.util.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class Controller_Comment extends LogAdder {
    //发评论的是用户，查删评论的是管理员

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment/getcomments", method = RequestMethod.POST)
    public ServerResponse<List<BiliComment>> getComments(HttpSession httpSession,
                                                                  @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                  @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliComment>> serverResponse = null;

        PageHelper.startPage(pageIndex, pageSize);

        serverResponse = commentService.getCommentsService();


        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.COMMENT_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }


    @RequestMapping(value = "/comment/getcommentsbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliComment>> getCommentsByVideoId(HttpSession httpSession,
                                                                  BiliVideo biliVideo,
                                                                  @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                  @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliComment>> serverResponse = null;

        PageHelper.startPage(pageIndex, pageSize);

        if(true){
            serverResponse = commentService.getCommentsByVideoIdService(biliVideo);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过视频ID查看相关评论");

            httpSession.setAttribute(ConstUtil.COMMENT_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/getcommentsbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getCommentsById(HttpSession httpSession,
                                                                  BiliUser biliUser,
                                                                  @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                  @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliUser>> serverResponse = null;

        PageHelper.startPage(pageIndex, pageSize);

        if(AccessJudger.isStaff(httpSession)){
            serverResponse = commentService.getCommentsByIdService(biliUser);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过用户ID查看相关评论");

            httpSession.setAttribute(ConstUtil.COMMENT_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/postcomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> postComment(HttpSession httpSession, BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(AccessJudger.isUser(httpSession)){

            //将用户id赋予评论对象
            biliComment.setUserid(((BiliUser)httpSession.getAttribute(ConstUtil.USER)).getUserid());
            //赋予当前时间
            biliComment.setCommenttime(new Date());

            serverResponse = commentService.postCommentService(biliComment);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/deletecomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> deleteComment(HttpSession httpSession, BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(AccessJudger.isStaff(httpSession)){
            serverResponse = commentService.deleteCommentService(biliComment);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"删除一条评论");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/downloadcommentsbyvideoid", method = RequestMethod.POST)
    public ServerResponse<String> downloadCommentsByVideoId(HttpSession httpSession){
        ServerResponse<String> serverResponse = null;

        if(AccessJudger.isStaff(httpSession)){

            if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.COMMENT_QUERY))){

                String filepath = ExcelDatasProduce.ProducerExcel(ConstUtil.EXCEL_COMMENT_INDEX, httpSession.getAttribute(ConstUtil.COMMENT_QUERY));
                serverResponse = ServerResponse.createRespBySuccess(filepath);
                System.out.println("评论下载: "+filepath);// /excels/filename.xls;

            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_GET_FAILURE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/getcommentsbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getCommentsById(HttpSession httpSession,
                                                          BiliUser biliUser,
                                                          @RequestParam(defaultValue = "1") Integer pageIndex,
                                                          @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliUser>> serverResponse = null;

        PageHelper.startPage(pageIndex, pageSize);

        if(AccessJudger.isStaff(httpSession)){
            serverResponse = commentService.getCommentsByIdService(biliUser);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过用户ID查看相关评论");

            httpSession.setAttribute(ConstUtil.COMMENT_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

}
