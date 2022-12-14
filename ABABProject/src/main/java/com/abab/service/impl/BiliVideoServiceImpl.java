package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.Collection;
import com.abab.service.BiliUserService;
import com.abab.service.CollectionService;
import com.abab.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliVideoService;
import com.abab.mapper.BiliVideoMapper;
import com.github.pagehelper.PageHelper;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_video】的数据库操作Service实现
* @createDate 2022-09-04 16:10:23
*/
@Service
public class BiliVideoServiceImpl extends ServiceImpl<BiliVideoMapper, BiliVideo>
    implements BiliVideoService{
    @Autowired
    BiliUserService biliUserService;

    @Autowired
    CollectionService collectionService;

    @Override
    public ServerResponse<BiliVideo> uploadVideo(MultipartFile video, MultipartFile image) {
        if(video.getSize() > VideoConstUtil.MAX_VIDEO_SIZE){
            return ServerResponse.createByErrorMessage("过大的视频文件");
        }
        if(image.getSize() > VideoConstUtil.MAX_IMAGE_SIZE){
            return ServerResponse.createByErrorMessage("过大的封面文件");
        }
        ServerResponse<String> image_response = FileLoader.uploadImage(image, VideoConstUtil.IMAGE_PATH);
        ServerResponse<String> video_response = FileLoader.uploadVideo(video, VideoConstUtil.VIDEO_PATH);

        if(!image_response.isSuccess()){
            return ServerResponse.createByErrorMessage("视频封面文件上传失败");
        }
        if(!video_response.isSuccess()){
            return ServerResponse.createByErrorMessage("视频文件上传失败");
        }

        //返回封面和视频的路径
        BiliVideo biliVideo = new BiliVideo();
        biliVideo.setVideopath(video_response.getData());
        biliVideo.setCoverimage(image_response.getData());
        return ServerResponse.createRespBySuccess(biliVideo);
    }

    @Override
    public ServerResponse<String> uploadVideoFile(MultipartFile video) {
        if(video.getSize() > VideoConstUtil.MAX_VIDEO_SIZE){
            return ServerResponse.createByErrorMessage("过大的视频文件");
        }
        ServerResponse<String> video_response = FileLoader.uploadVideo(video, VideoConstUtil.VIDEO_PATH);

        if(!video_response.isSuccess()){
            return ServerResponse.createByErrorMessage("视频文件上传失败");
        }

        //返回封面和视频的路径
        return ServerResponse.createRespBySuccess(video_response.getData());
    }

    @Override
    public ServerResponse<String> uploadImageFile(MultipartFile image) {
        if(image.getSize() > VideoConstUtil.MAX_IMAGE_SIZE){
            return ServerResponse.createByErrorMessage("过大的封面文件");
        }
        ServerResponse<String> image_response = FileLoader.uploadImage(image, VideoConstUtil.IMAGE_PATH);

        if(!image_response.isSuccess()){
            return ServerResponse.createByErrorMessage("视频封面文件上传失败");
        }

        //返回路径
        return ServerResponse.createRespBySuccess(image_response.getData());

    }

    @Override
    public ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo, String imagePath, String videoPath){
        //检查用户信息是否全面
        ArrayList<Pair<Object, String>> l = new ArrayList<>();
        l.add(new Pair<>(biliVideo.getVideotitle(), "视频标题"));
        //l.add(new Pair<>(biliVideo.getVideopath(), "视频源"));
        //l.add(new Pair<>(biliVideo.getCoverimage(), "视频封面"));
        for (Pair<Object, String> p : l){
            if(p.getFirst() == null){
                return ServerResponse.createByErrorMessage(p.getSecond() + ConstUtil.NOTALLOW_EMPTY);
            }
        }

        //检查信息是否合法
        ArrayList<Pair<Object, Integer>> Legality = new ArrayList<>();
        Legality.add(new Pair<>(biliVideo.getVideotitle(), 50));
        Legality.add(new Pair<>(biliVideo.getVideointrbriefing(), 200));
        //Legality.add(new Pair<>(biliVideo.getVideopath(), 500));
        //Legality.add(new Pair<>(biliVideo.getCoverimage(), 300));
        Legality.add(new Pair<>(biliVideo.getMemo(), 255));
        for (Pair<Object, Integer> p : Legality){
            if(p.getFirst() != null){
                if(p.getFirst().toString().length() >= p.getSecond()){
                    return ServerResponse.createByErrorMessage(p.getFirst().toString() + ConstUtil.OVERLIMITED_LENGTH);
                }
            }
        }

        //数据补全
        //上传成功 填充视频路径以及封面路径
        biliVideo.setCoverimage(imagePath);
        biliVideo.setVideopath(videoPath);
        //随机生成AV号
        String av = UUIDMaker.generationAV();
        biliVideo.setVideoid(av);
        //初始化三连数量
        biliVideo.setThumbs(0L);
        biliVideo.setCoin(0l);
        biliVideo.setCoin(0L);
        biliVideo.setForwarding(0L);
        biliVideo.setBullet(0L);
        biliVideo.setComment(0L);
        //初始化提交时间
        biliVideo.setUploadtime(new Date());
        //设置提交用户
        biliVideo.setUploaderid(biliUser.getUserid());
        //初始化审核/上架状态
        biliVideo.setAuditingid(5);
        biliVideo.setGrounding(9);

        //存储视频数据
        this.save(biliVideo);
        return ServerResponse.createRespBySuccess(biliVideo);
    }

    @Override
    public ServerResponse<BiliVideo> openVideoService(BiliVideo biliVideo){
        if(biliVideo.getVideoid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getById(biliVideo);

        if(video == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }else{
            return ServerResponse.createRespBySuccess(video);
        }
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideoInfoByIdService(BiliVideo biliVideo){
        //判断id是否为空
        if(biliVideo.getVideoid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());
        List<BiliVideo> video = this.list(queryWrapper);

        if(video == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }else{
            return ServerResponse.createRespBySuccess(video);
        }
    }

    @Override
    public ServerResponse<BiliVideo> updateVideoInfoService(BiliVideo biliVideo){
        //判断id是否为空
        if(biliVideo.getVideoid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getById(biliVideo);

        if(video == null){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }else{
            this.updateById(biliVideo);
            return ServerResponse.createRespBySuccess(biliVideo);
        }
    }

    @Override
    public ServerResponse<Long> getUnreviewedVideosNumberService(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditingid", 5);
        Long number = this.count(queryWrapper);

        return ServerResponse.createRespBySuccess(number);
    }

    @Override
    public ServerResponse<Long> getReviewedVideosNumberService(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("auditingid", 6, 7);
        Long number = this.count(queryWrapper);

        return ServerResponse.createRespBySuccess(number);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosByUserIdService(BiliUser biliUser, Integer pageIndex, Integer pageSize){
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
        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getPreparedVideosService(String byId,
                                                                    String byTitle,
                                                                    String byUser,
                                                                    Integer pageIndex,
                                                                    Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        //组装qw
        queryWrapper.eq("grounding", 9);
        queryWrapper.eq("auditingid", 7);
        //按照ID查找
        if(byId != null){
            queryWrapper.eq("videoid", byId);
        }
        //按照用户查找
        if(byUser != null){
            System.out.println("用户id" + byUser);
            queryWrapper.eq("uploaderid", byUser);
        }
        //按照标题查找
        if(byTitle != null){
            queryWrapper.like("videotitle", byTitle);
        }

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getShelvedVideosService(String byId,
                                                                   String byTitle,
                                                                   String byUser,
                                                                   Integer pageIndex,
                                                                   Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("grounding", 8);
        queryWrapper.eq("auditingid", 7);
        //按照ID查找
        if(byId != null){
            queryWrapper.eq("videoid", byId);
        }
        //按照用户查找
        if(byUser != null){
            System.out.println("用户id" + byUser);
            queryWrapper.eq("uploaderid", byUser);
        }
        //按照标题查找
        if(byTitle != null){
            queryWrapper.like("videotitle", byTitle);
        }

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosByAuditStateService(Integer auditState,
                                                                        String byId,
                                                                        String byTitle,
                                                                        String byUser,
                                                                        Integer pageIndex,
                                                                        Integer pageSize){
        int aud = 5;
        if(auditState == 1){
            aud = 7;
        }else if(auditState == 2){
            aud = 6;
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditingid", aud);

        if(byId != null){
            queryWrapper.eq("videoid", byId);
        }
        //按照用户查找
        if(byUser != null){
            System.out.println("用户id" + byUser);
            queryWrapper.eq("uploaderid", byUser);
        }
        //按照标题查找
        if(byTitle != null){
            queryWrapper.like("videotitle", byTitle);
        }

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosByAVService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("videoid", biliVideo.getVideoid());

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosByTitleService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(EmptyJudger.isEmpty(biliVideo.getVideotitle())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("videotitle", biliVideo.getVideotitle());

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<Long> likeVideo(BiliVideo biliVideo, BiliUser biliUser) {
        //检查
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliUserService.getById(biliUser.getUserid()))){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);

        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        Long num = 0l;
        if(video.getThumbs() == null){
            video.setThumbs(1l);
            num = 1l;
        }else {
            video.setThumbs(video.getThumbs() + 1);
            num = video.getThumbs();
        }

        this.updateById(video);

        return ServerResponse.createRespBySuccess(num);
    }

    @Override
    public ServerResponse<Long> coinVideo(BiliVideo biliVideo, BiliUser biliUser, Long coinCount) {
        //检查
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliUserService.getById(biliUser.getUserid()))){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);

        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        Long num = 0l;
        if(video.getCoin() == null){
            video.setCoin(Long.valueOf((coinCount)));
            num = coinCount;
        }else {
            video.setCoin(video.getCoin() + Long.valueOf((coinCount)));
            num = video.getCoin();
        }

        this.updateById(video);

        return ServerResponse.createRespBySuccess(num);
    }

    @Override
    public ServerResponse<Long> collectVideo(BiliVideo biliVideo, BiliUser biliUser) {
        //检查
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliUserService.getById(biliUser.getUserid()))){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);
        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        //联表查找
        QueryWrapper qw2 = new QueryWrapper();
        qw2.eq("userid", biliUser.getUserid());
        qw2.eq("videoid", video.getVideoid());
        Long count = collectionService.count(qw2);
        if(count > 0){
            return ServerResponse.createByErrorMessage("该用户已经收藏过此视频");
        }

        Long num = 0l;
        if(video.getCollection() == null){
            video.setCollection(1l);
            num = 1l;
        }else {
            video.setCollection(video.getCollection() + 1);
            num = video.getCollection();
        }
        Collection collection = new Collection();
        collection.setVideoid(video.getVideoid());
        collection.setUserid(biliUser.getUserid());
        collection.setCollecttime(new Date());

        //更新视频信息
        this.updateById(video);
        //更新联表信息
        collectionService.save(collection);

        return ServerResponse.createRespBySuccess(num);
    }

    @Override
    public ServerResponse<BiliVideo> auditVideo(BiliVideo biliVideo, Integer auditState) {
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);

        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        if(video.getAuditingid() != 5){
            return ServerResponse.createByErrorMessage("视频已审核，无需再次审核");
        }

        if(auditState == 1){
            video.setAuditingid(7);
            video.setGrounding(9);
        }else{
            video.setAuditingid(6);
        }

        this.updateById(video);

        return ServerResponse.createRespBySuccess(video);
    }

    @Override
    public ServerResponse<BiliVideo> onShelveVideo(BiliVideo biliVideo) {
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);

        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        if(video.getAuditingid() != 7){
            return ServerResponse.createByErrorMessage("视频未审核或审核未通过，无法上架");
        }

        if(video.getGrounding() == 8){
            return ServerResponse.createByErrorMessage("视频已上架，无法重复上架");
        }

        video.setGrounding(8);

        this.updateById(video);

        return ServerResponse.createRespBySuccess(video);
    }

    @Override
    public ServerResponse<BiliVideo> downShelveVideo(BiliVideo biliVideo) {
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("videoid", biliVideo.getVideoid());
        BiliVideo video = this.getOne(qw);

        if(EmptyJudger.isEmpty(video)){
            return ServerResponse.createByErrorMessage(ConstUtil.VIDEO_UNEXIST);
        }

        if(video.getAuditingid() != 7){
            return ServerResponse.createByErrorMessage("这个视频未审核或审核未通过，你是怎么搞到他的？？？？？？");
        }

        if(video.getGrounding() == 9){
            return ServerResponse.createByErrorMessage("视频已下架，无法重复下架");
        }

        video.setGrounding(9);

        this.updateById(video);

        return ServerResponse.createRespBySuccess(video);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosWithOrderd(String orderType, Boolean isAsc) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(isAsc){
            queryWrapper.orderByAsc(orderType);
        }else{
            queryWrapper.orderByDesc(orderType);
        }
        List<BiliVideo> list = this.list(queryWrapper);
        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> toBeVideos(List<Collection> cols){
        List<BiliVideo> videos = new ArrayList<>();
        for(Collection i : cols){
            String av = i.getVideoid();
            QueryWrapper qw2 = new QueryWrapper();
            qw2.eq("videoid", av);
            BiliVideo video = this.getOne(qw2);
            if(!EmptyJudger.isEmpty(video)){
                videos.add(video);
            }
        }
        return ServerResponse.createRespBySuccess(videos);
    }

}




