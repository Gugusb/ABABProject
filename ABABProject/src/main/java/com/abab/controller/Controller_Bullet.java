package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliBullet;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BulletService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.LogAdder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.annotation.ElementType;
import java.util.List;

@RestController
public class Controller_Bullet extends LogAdder {
    //用户发表评论，管理员查删

    @Autowired
    BulletService bulletService;

    @Autowired
    Controller_Logs controller_logs;



    @RequestMapping(value = "/buttle/getbuttlesbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> getBulletsByVideoId(HttpSession httpSession,
                                                                BiliVideo biliVideo,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            serverResponse = bulletService.getBulletsByVideoIdService(biliVideo);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过视频ID查看相关弹幕");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/postbuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> postBullet(HttpSession httpSession, BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.USER)!=null){
            //为弹幕设置当前用户id
            biliBullet.setUserid(((BiliUser)httpSession.getAttribute(ConstUtil.USER)).getUserid());

            serverResponse = bulletService.postBulletService(biliBullet);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }


        return serverResponse;
    }

    @RequestMapping(value = "/buttle/deletebuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> deleteButtle(HttpSession httpSession, BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        //管理员已登录状态下进行
        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            serverResponse = bulletService.deleteButtleService(biliBullet);

        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"删除一条弹幕");
        }

        return serverResponse;
    }
}
