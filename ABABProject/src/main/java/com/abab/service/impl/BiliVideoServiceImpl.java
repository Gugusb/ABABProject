package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliUser;
import com.abab.service.BiliUserService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliVideoService;
import com.abab.mapper.BiliVideoMapper;
import com.github.pagehelper.PageHelper;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    @Override
    public ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo){
        //检查用户信息是否全面
        ArrayList<Pair<Object, String>> l = new ArrayList<>();
        l.add(new Pair<>(biliVideo.getVideotitle(), "视频标题"));
        l.add(new Pair<>(biliVideo.getVideopath(), "视频源"));
        l.add(new Pair<>(biliVideo.getCoverimage(), "视频封面"));
        for (Pair<Object, String> p : l){
            if(p.getFirst() == null){
                return ServerResponse.createByErrorMessage(p.getSecond() + ConstUtil.NOTALLOW_EMPTY);
            }
        }

        //检查信息是否合法
        ArrayList<Pair<Object, Integer>> Legality = new ArrayList<>();
        Legality.add(new Pair<>(biliVideo.getVideotitle(), 50));
        Legality.add(new Pair<>(biliVideo.getVideointrbriefing(), 200));
        Legality.add(new Pair<>(biliVideo.getVideopath(), 500));
        Legality.add(new Pair<>(biliVideo.getCoverimage(), 300));
        Legality.add(new Pair<>(biliVideo.getMemo(), 255));
        for (Pair<Object, Integer> p : Legality){
            if(p.getFirst() != null){
                if(p.getFirst().toString().length() >= p.getSecond()){
                    return ServerResponse.createByErrorMessage(p.getFirst().toString() + ConstUtil.OVERLIMITED_LENGTH);
                }
            }
        }

        //数据补全
        //随机生成AV号
        Random random = new Random();
        int randav = 1000000 + random.nextInt(900000);
        biliVideo.setVideoid("AV" + randav);
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
    public ServerResponse<BiliVideo> getVideoInfoByIdService(BiliVideo biliVideo){
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
    public ServerResponse<List<BiliVideo>> getPreparedVideosService(Integer pageIndex, Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("grounding", 9);

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getShelvedVideosService(Integer pageIndex, Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("grounding", 8);

        List<BiliVideo> list = this.list(queryWrapper);

        return ServerResponse.createRespBySuccess(list);
    }

    @Override
    public ServerResponse<List<BiliVideo>> getVideosByAuditStateService(BiliDictionary dictionary, Integer pageIndex, Integer pageSize){
        //检查是否是相关描述
        if(dictionary.getMemo() != ConstUtil.MEMO_AUDIT_STATE){
            return ServerResponse.createByErrorMessage(ConstUtil.WRONG_MEMO);
        }
        //查找
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditing", dictionary.getKey());

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
}




