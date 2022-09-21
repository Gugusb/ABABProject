package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.entity.Collection;
import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_user】的数据库操作Service
* @createDate 2022-09-04 16:10:15
*/
public interface BiliUserService extends IService<BiliUser> {
    ServerResponse<BiliUser> loginService(BiliUser biliUser);

    ServerResponse<BiliUser> registerService(BiliUser biliUser, MultipartFile image);

    ServerResponse<List<BiliUser>> getUserInfoByIdService(BiliUser biliUser);

    ServerResponse<List<BiliUser>> getUserInfoByNameService(BiliUser biliUser);

    ServerResponse<BiliUser> updateUserInfoService(BiliUser biliUser);

    ServerResponse<String> cancelUserService(BiliUser biliUser);

    ServerResponse<BiliUser> toBeVIPService(BiliUser biliUser, Date startTime, Integer duration);

    ServerResponse<Boolean> checkVIPStateService(BiliUser biliUser);

    ServerResponse<BiliUser> extensionVIPService(BiliUser biliUser, Integer duration);

    ServerResponse<BiliUser> uploadUserHead(MultipartFile image);

    ServerResponse<List<Collection>> getCollectVideoId(BiliUser biliUser);
}
