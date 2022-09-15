package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_video】的数据库操作Service
* @createDate 2022-09-04 16:10:23
*/
public interface BiliVideoService extends IService<BiliVideo> {

    ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo);

    ServerResponse<BiliVideo> openVideoService(BiliVideo biliVideo);

    ServerResponse<List<BiliVideo>> getVideoInfoByIdService(BiliVideo biliVideo);

    ServerResponse<BiliVideo> updateVideoInfoService(BiliVideo biliVideo);

    ServerResponse<Long> getUnreviewedVideosNumberService();

    ServerResponse<Long> getReviewedVideosNumberService();

    ServerResponse<List<BiliVideo>> getVideosByUserIdService(BiliUser biliUser, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getPreparedVideosService(Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getShelvedVideosService(Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByAuditStateService(BiliDictionary dictionary, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByAVService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByTitleService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);
}
