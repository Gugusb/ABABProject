package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliBullet;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bullet】的数据库操作Service
* @createDate 2022-09-04 16:10:31
*/
public interface BulletService extends IService<BiliBullet> {

    ServerResponse<List<BiliBullet>> getBulletsService();

    ServerResponse<List<BiliBullet>> getBulletsByVideoIdService(BiliVideo biliVideo);

    ServerResponse<List<BiliUser>> getBulletsByIdService(BiliUser biliUser);

    ServerResponse<BiliBullet> postBulletService(BiliBullet biliBullet);

    ServerResponse<BiliBullet> deleteButtleService(BiliBullet biliBullet);
}
