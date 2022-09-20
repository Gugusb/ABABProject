package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliBullet;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BulletService;
import com.abab.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_Bullet extends LogAdder {
    //用户发表评论，管理员查删

    @Autowired
    BulletService bulletService;

    @Autowired
    Controller_Logs controller_logs;

    @RequestMapping(value = "/buttle/getbuttles", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> getBullets(HttpSession httpSession,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        serverResponse = bulletService.getBulletsService();

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过视频ID查看相关弹幕");

            httpSession.setAttribute(ConstUtil.BULLET_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/getbuttlesbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> getBulletsByVideoId(HttpSession httpSession,
                                                                BiliVideo biliVideo,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        if(AccessJudger.isStaff(httpSession)){
            serverResponse = bulletService.getBulletsByVideoIdService(biliVideo);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过视频ID查看相关弹幕");

            httpSession.setAttribute(ConstUtil.BULLET_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/getbuttlesbyuserid", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getBulletsById(HttpSession httpSession,
                                                                BiliUser biliUser,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliUser>> serverResponse=null;

        if(AccessJudger.isStaff(httpSession)){
            serverResponse = bulletService.getBulletsByIdService(biliUser);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过用户ID查看相关弹幕");

            httpSession.setAttribute(ConstUtil.BULLET_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/postbuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> postBullet(HttpSession httpSession, BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(AccessJudger.isUser(httpSession)){
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
        if(AccessJudger.isAdmin(httpSession)){
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

    @RequestMapping(value = "/buttle/downloadbuttlesbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> downloadBulletsByVideoId(HttpSession httpSession){
        ServerResponse<List<BiliBullet>> serverResponse = null;

        if(AccessJudger.isStaff(httpSession)){

            if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.BULLET_QUERY))){

                if(ExcelDatasProduce.ProducerExcel(ConstUtil.EXCEL_BULLET_INDEX, httpSession.getAttribute(ConstUtil.BULLET_QUERY))){
                    serverResponse = ServerResponse.createRespBySuccess((List<BiliBullet>) httpSession.getAttribute(ConstUtil.BULLET_QUERY));
                }
                else serverResponse = ServerResponse.createByErrorMessage(ConstUtil.EXCEL_CREATE_FAILURE);

            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_GET_FAILURE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
        }

        return serverResponse;
    }
}
