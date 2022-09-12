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

    private ServerResponse<BiliVideo> openVideoService(BiliVideo biliVideo){
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

    @RequestMapping(value = "/video/openvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> openVideo(HttpSession httpSession, BiliVideo biliVideo){
        ServerResponse<BiliVideo> serverResponse = openVideoService(biliVideo);
        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.VIDEO, serverResponse.getData());
            httpSession.setMaxInactiveInterval(60 * 60);
        }
        return serverResponse;
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
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "通过视频ID查询视频");
        }
        return serverResponse;
    }

    private ServerResponse<BiliVideo> updateVideoInfoService(BiliVideo biliVideo){
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
            biliVideoService.updateById(biliVideo);
            return ServerResponse.createRespBySuccess(biliVideo);
        }
    }

    @RequestMapping(value = "/video/updatevideoinfo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> updateVideoInfo(HttpSession httpSession, BiliVideo biliVideo){
        //权限查看
//        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
//                httpSession.getAttribute(ConstUtil.STAFF) == ""){
//            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
//        }

        ServerResponse<BiliVideo> serverResponse = updateVideoInfoService(biliVideo);
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
        ServerResponse<BiliVideo> serverResponse = getVideoInfoByIdService((BiliVideo) httpSession.getAttribute(ConstUtil.VIDEO));
        return null;
    }

    private ServerResponse<Long> getUnreviewedVideosNumberService(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditingid", 5);
        Long number = biliVideoService.count(queryWrapper);

        return ServerResponse.createRespBySuccess(number);
    }

    @RequestMapping(value = "/video/getunreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Long> getUnreviewedVideosNumber(HttpSession httpSession){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = getUnreviewedVideosNumberService();
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取未审核的视频数量");
        }
        return serverResponse;
    }

    private ServerResponse<Long> getReviewedVideosNumberService(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("auditingid", 6, 7);
        Long number = biliVideoService.count(queryWrapper);

        return ServerResponse.createRespBySuccess(number);
    }

    @RequestMapping(value = "/video/getreviewednumber", method = RequestMethod.POST)
    public ServerResponse<Long> getReviewedVideosNumber(HttpSession httpSession){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = getReviewedVideosNumberService();
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取已审核视频数量");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getVideosByUserIdService(BiliUser biliUser, Integer pageIndex, Integer pageSize){
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else{
            if(EmptyJudger.isEmpty(biliUserService.getById(biliUser))){
                return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
            }
        }
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uploaderid", biliUser.getUserid());
        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getvideosbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByUserId(HttpSession httpSession,
                                                             BiliUser biliUser,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getVideosByUserIdService(biliUser, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据用户ID查询所有相关视频");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getPreparedVideosService(Integer pageIndex, Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("grounding", 9);

        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getpreparedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getPreparedVideos(HttpSession httpSession,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getPreparedVideosService(pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有待上架视频");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getShelvedVideosService(Integer pageIndex, Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("grounding", 8);

        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getshelvedvideos", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getShelvedVideos(HttpSession httpSession,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getShelvedVideosService(pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有已上架视频");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getVideosByAuditStateService(BiliDictionary dictionary, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(dictionary.getMemo() != ConstUtil.MEMO_AUDIT_STATE){
            return ServerResponse.createByErrorMessage(ConstUtil.WRONG_MEMO);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditing", dictionary.getKey());

        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getvideosbyauditstate", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAuditState(HttpSession httpSession,
                                                                BiliDictionary dictionary,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getVideosByAuditStateService(dictionary, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据审核状态查找视频");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getVideosByAVService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());

        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getvideosbyav", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByAV(HttpSession httpSession,
                                                         BiliVideo biliVideo,
                                                         @RequestParam(defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getVideosByAVService(biliVideo, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据AV号" + biliVideo.getVideoid() + "查询视频");
        }
        return serverResponse;
    }

    private ServerResponse<List<BiliVideo>> getVideosByTitleService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(EmptyJudger.isEmpty(biliVideo.getVideotitle())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("videotitle", biliVideo.getVideotitle());

        List<BiliVideo> list = biliVideoService.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @RequestMapping(value = "/video/getvideosbytitle", method = RequestMethod.POST)
    public ServerResponse<List<BiliVideo>> getVideosByTitle(HttpSession httpSession,
                                                            BiliVideo biliVideo,
                                                            @RequestParam(defaultValue = "1") Integer pageIndex,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF))){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<List<BiliVideo>> serverResponse = getVideosByTitleService(biliVideo, pageIndex, pageSize);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "根据标题查询 " + biliVideo.getVideotitle() + " 视频");
        }
        return serverResponse;
    }
}
