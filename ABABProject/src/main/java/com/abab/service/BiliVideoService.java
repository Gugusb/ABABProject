package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_video】的数据库操作Service
* @createDate 2022-09-04 16:10:23
*/
public interface BiliVideoService extends IService<BiliVideo> {

    ServerResponse<String> uploadVideoFile(MultipartFile video);

    ServerResponse<BiliVideo> uploadVideo(MultipartFile video, MultipartFile image);

    ServerResponse<String> uploadImageFile(MultipartFile image);

    ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo, String imagePath, String videoPath);

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

    ServerResponse<List<BiliVideo>> getVideosByAuditStateService(Integer auditState,
                                                                 String byId,
                                                                 String byTitle,
                                                                 String byUser,
                                                                 Integer pageIndex,
                                                                 Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByAVService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);

    ServerResponse<List<BiliVideo>> getVideosByTitleService(BiliVideo biliVideo, Integer pageIndex, Integer pageSize);

    ServerResponse<Long> likeVideo(BiliVideo biliVideo, BiliUser biliUser);

    ServerResponse<Long> coinVideo(BiliVideo biliVideo, BiliUser biliUser, Long coinCount);

    ServerResponse<Long> collectVideo(BiliVideo biliVideo, BiliUser biliUser);

    ServerResponse<BiliVideo> auditVideo(BiliVideo biliVideo, Integer auditState);

    ServerResponse<BiliVideo> onShelveVideo(BiliVideo biliVideo);

    ServerResponse<BiliVideo> downShelveVideo(BiliVideo biliVideo);

    ServerResponse<List<BiliVideo>> getVideosWithOrderd(String orderType, Boolean isAsc);

    ServerResponse<List<BiliVideo>> toBeVideos(List<Collection> cols);
}
