package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliBullet;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BulletService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_Bullet {
    //用户发表评论，管理员查删

    @Autowired
    BulletService bulletService;

    public ServerResponse<List<BiliBullet>> getBulletsByVideoIdService(BiliVideo biliVideo){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        List<BiliBullet> biliBulletList=null;


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorname", biliVideo.getVideoid());
        biliBulletList = bulletService.list(queryWrapper);

        if(EmptyJudger.isEmpty(biliBulletList)){
            serverResponse=ServerResponse.createRespBySuccess(biliBulletList);

        }
        else{
            serverResponse=ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
        }

        return serverResponse;
    }

    public ServerResponse<BiliBullet> postBulletService(BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(EmptyJudger.isEmpty(biliBullet.getUserid())){
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }else if(EmptyJudger.isEmpty(biliBullet.getVideoid())){
            serverResponse = ServerResponse.createByErrorMessage("视频ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliBullet.getContent())){
            serverResponse = ServerResponse.createByErrorMessage("弹幕文本" + ConstUtil.NOTALLOW_EMPTY);
        }else if(biliBullet.getContent().length()>200) {
            serverResponse = ServerResponse.createByErrorMessage("弹幕文本" + ConstUtil.OVERLIMITED_LENGTH);
        }else if(biliBullet.getMemo().length()>200){
            serverResponse = ServerResponse.createByErrorMessage("弹幕备注" + ConstUtil.OVERLIMITED_LENGTH);
        }else{
            bulletService.save(biliBullet);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }

    public ServerResponse<BiliBullet> deleteButtleService(BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(EmptyJudger.isEmpty(biliBullet.getId())){
            serverResponse = ServerResponse.createByErrorMessage("弹幕id"+ConstUtil.NOTALLOW_EMPTY);
        }else{
            QueryWrapper qe = new QueryWrapper();
            qe.eq("id",biliBullet.getId());
            bulletService.remove(qe);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/getbuttlesbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> getBulletsByVideoId(HttpSession httpSession,
                                                                BiliVideo biliVideo,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            serverResponse = getBulletsByVideoIdService(biliVideo);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/buttle/postbuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> postBullet(HttpSession httpSession, BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.USER)!=null){
            //为弹幕设置当前用户id
            biliBullet.setUserid(((BiliUser)httpSession.getAttribute(ConstUtil.USER)).getUserid());

            serverResponse = postBulletService(biliBullet);
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
            serverResponse = deleteButtleService(biliBullet);

        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }


        return serverResponse;
    }
}
