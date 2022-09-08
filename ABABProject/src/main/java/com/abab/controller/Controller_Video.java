package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliDictionary;
import com.abab.service.BiliUserService;
import com.abab.service.BiliVideoService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.LogAdder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.constant.Constable;
import java.util.List;

@RestController
public class Controller_Video extends LogAdder {

    @Autowired
    private BiliVideoService biliVideoService;

    @Autowired
    private BiliUserService biliUserService;

    @RequestMapping(value = "/video/openvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> openVideo(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<BiliVideo> serverResponse = biliVideoService.openVideoService(biliVideo);
        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.VIDEO, serverResponse.getData());
            httpSession.setMaxInactiveInterval(60 * 60);
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideobyid", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> getVideoInfoById(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<BiliVideo> serverResponse = biliVideoService.getVideoInfoByIdService(biliVideo);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "通过视频ID查询视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/updatevideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> updateVideoInfo(HttpSession httpSession, BiliVideo biliVideo){

        ServerResponse<BiliVideo> serverResponse = biliVideoService.updateVideoInfoService(biliVideo);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "更新视频信息");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> getVideoInfo(HttpSession httpSession){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.VIDEO))){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        ServerResponse<BiliVideo> serverResponse = biliVideoService.getVideoInfoByIdService((BiliVideo) httpSession.getAttribute(ConstUtil.VIDEO));
        return null;
    }

    @RequestMapping(value = "/video/getunreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Long> getUnreviewedVideosNumber(HttpSession httpSession){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = biliVideoService.getUnreviewedVideosNumberService();
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取未审核的视频数量");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Long> getReviewedVideosNumber(HttpSession httpSession){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = biliVideoService.getReviewedVideosNumberService();
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取已审核视频数量");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideosbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByUserId(HttpSession httpSession,
                                                             BiliUser biliUser,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideosByUserIdService(biliUser, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据用户ID查询所有相关视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getpreparedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getPreparedVideos(HttpSession httpSession,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getPreparedVideosService(pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有待上架视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getshelvedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getShelvedVideos(HttpSession httpSession,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getShelvedVideosService(pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有已上架视频");
        }
        return serverResponse;
    }


    @RequestMapping(value = "/video/getvideosbyauditstate", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAuditState(HttpSession httpSession,
                                                                BiliDictionary dictionary,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideosByAuditStateService(dictionary, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据审核状态查找视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideosbyav", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAV(HttpSession httpSession,
                                                         BiliVideo biliVideo,
                                                         @RequestParam(defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideosByAVService(biliVideo, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据AV号" + biliVideo.getVideoid() + "查询视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideosbytitle", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByTitle(HttpSession httpSession,
                                                            BiliVideo biliVideo,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideosByTitleService(biliVideo, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据标题查询 " + biliVideo.getVideotitle() + " 视频");
        }
        return serverResponse;
    }
}
