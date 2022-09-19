package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_video】的数据库操作Service
* @createDate 2022-09-04 16:10:23
*/
public interface BiliVideoService extends IService<BiliVideo> {

    ServerResponse<BiliVideo> uploadVideo(MultipartFile video, MultipartFile image);

    ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo, MultipartFile videoFile, MultipartFile imageFile);

    ServerResponse<BiliVideo> openVideoService(BiliVideo biliVideo);

    ServerResponse<List<BiliVideo>> getVideoInfoByIdService(BiliVideo biliVideo);

    ServerResponse<BiliVideo> updateVideoInfoService(BiliVideo biliVideo);

    ServerResponse<Long> getUnreviewedVideosNumberService();

    ServerResponse<Long> getReviewedVideosNumberService();

    ServerResponse<List<BiliVideo>> getVideosByUserIdService(BiliUser biliUser, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getPreparedVideosService(String byId,
                                                             String byTitle,
                                                             String byUser,
                                                             Integer pageIndex,
                                                             Integer pageSize);

    ServerResponse<List<BiliVideo>> getShelvedVideosService(String byId,
                                                            String byTitle,
                                                            String byUser,
                                                            Integer pageIndex,
                                                            Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByAuditStateService(BiliDictionary dictionary,
                                                                 String byId,
                                                                 String byTitle,
                                                                 String byUser,
                                                                 Integer pageIndex,
                                                                 Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByAVService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByTitleService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);


}
