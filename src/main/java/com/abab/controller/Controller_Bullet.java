package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliBullet;
import com.abab.entity.BiliVideo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_Bullet {

    @RequestMapping(value = "/buttle/getbuttlesbyvideoid", method = RequestMethod.POST)
    public ServerResponse<List<BiliBullet>> getBulletsByVideoId(HttpSession httpSession,
                                                                BiliVideo biliVideo,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/buttle/postbuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> postBullet(HttpSession httpSession, BiliBullet biliBullet){
        return null;
    }

    @RequestMapping(value = "/buttle/deletebuttle", method = RequestMethod.POST)
    public ServerResponse<BiliBullet> deleteButtle(HttpSession httpSession, BiliBullet biliBullet){
        return null;
    }
}
