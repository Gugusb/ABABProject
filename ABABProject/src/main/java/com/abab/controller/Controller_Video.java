package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.BiliDictionary;
import com.abab.service.BiliUserService;
import com.abab.service.BiliVideoService;
import com.abab.util.AccessJudger;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.LogAdder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.lang.constant.Constable;
import java.util.List;

@RestController
@Api("视频控制类")
public class Controller_Video extends LogAdder {

    @Autowired
    private BiliVideoService biliVideoService;

    @RequestMapping(value = "/video/upload_video_forpath", method = RequestMethod.POST)
    public String uploadVideoForPath(HttpSession httpSession, MultipartFile videoFile){
        //尝试上传文件
        ServerResponse<String> upload_response = biliVideoService.uploadVideoFile(videoFile);

        return upload_response.getData();
    }

    @RequestMapping(value = "/video/upload_image_forpath", method = RequestMethod.POST)
    public String uploadImageForPath(HttpSession httpSession, MultipartFile imageFile){
        //尝试上传文件
        ServerResponse<String> upload_response = biliVideoService.uploadImageFile(imageFile);

        return upload_response.getData();
    }

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
    public ServerResponse<List<BiliVideo>> getVideoInfoById(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideoInfoByIdService(biliVideo);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "通过视频ID查询视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/updatevideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> updateVideoInfo(HttpSession httpSession, BiliVideo biliVideo){
        //权限查看
//        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
//                httpSession.getAttribute(ConstUtil.STAFF) == ""){
//            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
//        }

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
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideoInfoByIdService((BiliVideo) httpSession.getAttribute(ConstUtil.VIDEO));
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

    @RequestMapping(value = "/video/getvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideos(HttpSession httpSession,
                                                     @RequestParam(defaultValue = "1") Integer pageIndex,
                                                     @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        return ServerResponse.createRespBySuccess(biliVideoService.list());
    }

    @RequestMapping(value = "/video/getvideosbyorder", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosWithOrderd(HttpSession httpSession,
                                                               @RequestParam(value = "orderType") String orderType,
                                                               @RequestParam(value = "isAsc", defaultValue = "true")Boolean isAsc,
                                                               @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                                               @RequestParam(defaultValue = "5", required = false) Integer pageSize){
        return biliVideoService.getVideosWithOrderd(orderType, isAsc);
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
                                                             String byId,
                                                             String byTitle,
                                                             String byUser,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getPreparedVideosService(byId, byTitle, byUser, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有待上架视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getshelvedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getShelvedVideos(HttpSession httpSession,
                                                            String byId,
                                                            String byTitle,
                                                            String byUser,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getShelvedVideosService(byId, byTitle, byUser, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有已上架视频");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/video/getvideosbyauditstate", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAuditState(HttpSession httpSession,
                                                                 Integer auditState,
                                                                 String byId,
                                                                 String byTitle,
                                                                 String byUser,
                                                                 @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                 @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = biliVideoService.getVideosByAuditStateService(auditState, byId, byTitle, byUser, pageIndex, pageSize);
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

    @RequestMapping(value = "/video/deletevideo", method = RequestMethod.POST)
    public ServerResponse<Boolean> cancelVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage("需要传入videoid");
        }
        if(EmptyJudger.isEmpty(biliVideoService.getById(biliVideo.getVideoid()))){
            return ServerResponse.createByErrorMessage("无效的视频id");
        }
        biliVideoService.removeById(biliVideo.getVideoid());
        return ServerResponse.createRespBySuccess(true);
    }

    @RequestMapping(value = "/video/likevideo", method = RequestMethod.POST)
    public ServerResponse<Long> likeVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(!AccessJudger.isUser(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = biliVideoService.likeVideo(biliVideo, (BiliUser) httpSession.getAttribute(ConstUtil.USER));

        return serverResponse;
    }

    @RequestMapping(value = "/video/coinvideo", method = RequestMethod.POST)
    public ServerResponse<Long> coinVideo(HttpSession httpSession, BiliVideo biliVideo, @RequestParam(defaultValue = "1")Long coinCount){
        if(!AccessJudger.isUser(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = biliVideoService.coinVideo(biliVideo, (BiliUser) httpSession.getAttribute(ConstUtil.USER), coinCount);

        return serverResponse;
    }

    @RequestMapping(value = "/video/collectvideo", method = RequestMethod.POST)
    public ServerResponse<Long> collectVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(!AccessJudger.isUser(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = biliVideoService.collectVideo(biliVideo, (BiliUser) httpSession.getAttribute(ConstUtil.USER));

        return serverResponse;
    }

    @RequestMapping(value = "/video/auditvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> auditVideo(HttpSession httpSession, BiliVideo biliVideo, @RequestParam(defaultValue = "1")Integer auditState){
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<BiliVideo> serverResponse = biliVideoService.auditVideo(biliVideo, auditState);

        return serverResponse;
    }

    @RequestMapping(value = "/video/onshelvevideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> onShelveVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<BiliVideo> serverResponse = biliVideoService.onShelveVideo(biliVideo);

        return serverResponse;
    }

    @RequestMapping(value = "/video/downshelvevideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> downShelveVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<BiliVideo> serverResponse = biliVideoService.downShelveVideo(biliVideo);

        return serverResponse;
    }
}
