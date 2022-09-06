package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliComment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_Comment {

    @RequestMapping(value = "/comment/getcommentsbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliComment>> getCommentsByVideoId(HttpSession httpSession,
                                                                  BiliVideo biliVideo,
                                                                  @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                  @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/comment/postcomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> postComment(HttpSession httpSession, BiliComment biliComment){
        return null;
    }

    @RequestMapping(value = "/comment/deletecomment", method = RequestMethod.POST)
    public ServerResponse<BiliComment> deleteComment(HttpSession httpSession, BiliComment biliComment){
        return null;
    }
}
