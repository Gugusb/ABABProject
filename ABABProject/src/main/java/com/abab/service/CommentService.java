package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliComment;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 故故sb
 * @description 针对表【comment】的数据库操作Service
 * @createDate 2022-09-04 16:10:38
 */
public interface CommentService extends IService<BiliComment> {

    ServerResponse<List<BiliComment>> getCommentsService();

    ServerResponse<List<BiliComment>> getCommentsByVideoIdService(BiliVideo biliVideo);

    ServerResponse<List<BiliUser>> getCommentsByIdService(BiliUser biliUser);

    ServerResponse<BiliComment> postCommentService(BiliComment biliComment);

    ServerResponse<BiliComment> deleteCommentService(BiliComment biliComment);

    ServerResponse<List<BiliUser>> getCommentsByIdService(BiliUser biliUser);
}
