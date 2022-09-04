package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class Controller_User {

    @Autowired
    BiliUserService biliUserService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ServerResponse<BiliUser> login(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = null;

        //业务层逻辑
        BiliUser user = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());

        user = biliUserService.getOne(queryWrapper);

        if(user==null){
            return ServerResponse.createByErrorMessage("无此用户！");
        }else{
            if(user.getPassword().equals(biliUser.getPassword())){
                serverResponse = ServerResponse.createRespBySuccess(user);
            }else{
                serverResponse = ServerResponse.createByErrorMessage("密码错误！");
            }
        }

        //控制层逻辑
        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }
        return serverResponse;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ServerResponse<BiliUser> register(HttpSession httpSession, BiliUser biliUser){
        return null;
    }

    @RequestMapping(value = "/user/submitvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> submitVideo(HttpSession httpSession, BiliVideo biliVideo){
        return null;
    }

    @RequestMapping(value = "/user/getusernumber", method = RequestMethod.POST)
    public ServerResponse<Integer> getUserNumber(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/user/getusers", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUsers(HttpSession httpSession,
                                                   @RequestParam(defaultValue = "1") Integer pageIndex,
                                                   @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);
        return null;
    }

    @RequestMapping(value = "/user/getuserinfo", method = RequestMethod.POST)
    public ServerResponse<BiliUser> getUserInfo(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/user/getuserinfobyid", method = RequestMethod.POST)
    public ServerResponse<BiliUser> getUserInfoById(HttpSession httpSession, BiliUser biliUser){
        return null;
    }

    @RequestMapping(value = "/user/updateuserinfo", method = RequestMethod.POST)
    public ServerResponse<BiliUser> updateUserInfo(HttpSession httpSession, BiliUser biliUser){
        return null;
    }

    @RequestMapping(value = "/user/canceluser", method = RequestMethod.POST)
    public ServerResponse<String> cancelUser(HttpSession httpSession){
        return null;
    }


}
