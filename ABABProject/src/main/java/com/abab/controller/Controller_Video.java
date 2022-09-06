package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliDictionary;
import com.abab.service.BiliVideoService;
import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.constant.Constable;
import java.util.List;

@RestController
public class Controller_Video {

    @Autowired
    private BiliVideoService biliVideoService;

    @RequestMapping(value = "/video/openvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> openVideo(HttpSession httpSession, BiliVideo biliVideo){
        return null;
    }

    private ServerResponse<BiliVideo> getVideoInfoByIdService(BiliVideo biliVideo){
        //判断id是否为空
        if(biliVideo.getVideoid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = biliVideoService.getById(biliVideo);

        if(video == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }else{
            return ServerResponse.createRespBySuccess(video);
        }
    }

    @RequestMapping(value = "/video/getvideobyid", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> getVideoInfoById(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<BiliVideo> serverResponse = getVideoInfoByIdService(biliVideo);
        return serverResponse;
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
