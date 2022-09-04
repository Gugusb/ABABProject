package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliDictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_Video {

    @RequestMapping(value = "/video/getvideobyid", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> getVideoInfoById(HttpSession httpSession, BiliVideo biliVideo){
        return null;
    }

    @RequestMapping(value = "/video/updatevideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> updateVideoInfo(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/video/getvideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> getVideoInfo(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/video/getunreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Integer> getUnreviewedVideosNumber(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/video/getreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Integer> getReviewedVideosNumber(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/video/getvideosbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByUserId(HttpSession httpSession,
                                                             BiliUser biliUser,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/video/getpreparedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getPreparedVideos(HttpSession httpSession,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/video/getshelvedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getShelvedVideos(HttpSession httpSession,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/video/getvideosbyauditstate", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAuditState(HttpSession httpSession,
                                                                BiliDictionary dictionary,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/video/getvideosbyav", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAV(HttpSession httpSession,
                                                         BiliVideo biliVideo,
                                                         @RequestParam(defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/video/getvideosbytitle", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByTitle(HttpSession httpSession,
                                                            BiliVideo biliVideo,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }
}
