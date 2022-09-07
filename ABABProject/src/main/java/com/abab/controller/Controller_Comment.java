package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliComment;
import com.abab.service.CommentService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class Controller_Comment {
    //发评论的是用户，查删评论的是管理员

    @Autowired
    CommentService commentService;

    @Autowired
    Controller_Logs controller_logs;

    private ServerResponse<List<BiliComment>> getCommentsByVideoIdService(BiliVideo biliVideo){
        ServerResponse<List<BiliComment>> serverResponse = null;

        List<BiliComment> biliCommentList=null;

        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            serverResponse =ServerResponse.createByErrorMessage("videoid" + ConstUtil.NOTALLOW_EMPTY);
        }
        else{
            QueryWrapper qe = new QueryWrapper();
            qe.eq("videoid" , biliVideo.getVideoid());
            biliCommentList=commentService.list(qe);

            if(!EmptyJudger.isEmpty(biliCommentList)){
                serverResponse = ServerResponse.createRespBySuccess(biliCommentList);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
            }
        }

        return serverResponse;
    }

    private ServerResponse<BiliComment> postCommentService(BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(EmptyJudger.isEmpty(biliComment.getUserid())){
            serverResponse = ServerResponse.createByErrorMessage("用户ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliComment.getContent())){
            serverResponse = ServerResponse.createByErrorMessage("评论内容" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliComment.getVideoid())) {
            serverResponse = ServerResponse.createByErrorMessage("视频ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(biliComment.getContent().length() > 200) {
            serverResponse = ServerResponse.createByErrorMessage("评论" + ConstUtil.OVERLIMITED_LENGTH);
        }else if(biliComment.getMemo().length() > 200){
            serverResponse = ServerResponse.createByErrorMessage("评论备注" + ConstUtil.OVERLIMITED_LENGTH);
        }else{
            commentService.save(biliComment);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }

    private ServerResponse<BiliComment> deleteCommentService(BiliComment biliComment){
        //按照评论id删除评论
        ServerResponse<BiliComment> serverResponse = null;

        if(!EmptyJudger.isEmpty(biliComment.getId())){
            QueryWrapper qe = new QueryWrapper();
            qe.eq("id",biliComment.getId());

            commentService.remove(qe);

            serverResponse = ServerResponse.createRespBySuccess();
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
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

        if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            serverResponse = getCommentsByVideoIdService(biliVideo);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            controller_logs.addLogsForBack(httpSession,"通过视频ID查看相关评论");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/postcomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> postComment(HttpSession httpSession, BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.USER))){

            //将用户id赋予评论对象
            biliComment.setUserid(((BiliUser)httpSession.getAttribute(ConstUtil.USER)).getUserid());
            //赋予当前时间
            biliComment.setCommenttime(new Date());

            serverResponse = postCommentService(biliComment);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/comment/deletecomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> deleteComment(HttpSession httpSession, BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            serverResponse = deleteCommentService(biliComment);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            controller_logs.addLogsForBack(httpSession,"删除一条评论");
        }

        return serverResponse;
    }
}
