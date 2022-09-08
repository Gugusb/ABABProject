package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
* @author 故故sb
* @description 针对表【bili_user】的数据库操作Service
* @createDate 2022-09-04 16:10:15
*/
public interface BiliUserService extends IService<BiliUser> {
    ServerResponse<BiliUser> loginService(BiliUser biliUser);

    ServerResponse<BiliUser> registerService(BiliUser biliUser);

    ServerResponse<BiliUser> getUserInfoByIdService(BiliUser biliUser);

    ServerResponse<BiliUser> updateUserInfoService(BiliUser biliUser);

    ServerResponse<String> cancelUserService(BiliUser biliUser);

    ServerResponse<BiliUser> toBeVIPService(BiliUser biliUser, Date startTime, Integer duration);

    ServerResponse<Boolean> checkVIPStateService(BiliUser biliUser);
}
