package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.abab.service.BiliVideoService;
import com.abab.util.AccessJudger;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.LogAdder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class Controller_User extends LogAdder {

    @Autowired
    BiliUserService biliUserService;

    @Autowired
    BiliVideoService biliVideoService;

    /**
     * 登录
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ServerResponse<BiliUser> login(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = biliUserService.loginService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    /**
     * 注册
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ServerResponse<BiliUser> register(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = biliUserService.registerService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    /**
     * 提交视频
     *
     * @param httpSession http会话
     * @param biliVideo   箱内视频
     * @return {@link ServerResponse}<{@link BiliVideo}>
     */
    @RequestMapping(value = "/user/submitvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> submitVideo(HttpSession httpSession, BiliVideo biliVideo, MultipartFile videoFile, MultipartFile imageFile){
        if(!AccessJudger.isUser(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }
        ServerResponse<BiliVideo> serverResponse = biliVideoService.submitVideoService((BiliUser) httpSession.getAttribute(ConstUtil.USER), biliVideo, videoFile, imageFile);
        return serverResponse;
    }

    /**
     * 获取用户数量
     *
     * @param httpSession http会话
     * @return {@link ServerResponse}<{@link Long}>
     */
    @RequestMapping(value = "/user/getusernumber", method = RequestMethod.POST)
    public ServerResponse<Long> getUserNumber(HttpSession httpSession){
        //权限查看
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<Long> serverResponse = ServerResponse.createRespBySuccess(biliUserService.count());
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取用户数量");
        }
        return serverResponse;
    }

    /**
     * 让用户
     *
     * @param httpSession http会话
     * @param pageIndex   页面索引
     * @param pageSize    页面大小
     * @return {@link ServerResponse}<{@link List}<{@link BiliUser}>>
     */
    @RequestMapping(value = "/user/getusers", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUsers(HttpSession httpSession,
                                                   @RequestParam(defaultValue = "1") Integer pageIndex,
                                                   @RequestParam(defaultValue = "5") Integer pageSize){
        System.out.println(httpSession.getId());
        //权限查看
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<BiliUser> arrayList = biliUserService.list();

        ServerResponse<List<BiliUser>> serverResponse = ServerResponse.createRespBySuccess(arrayList);

        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取所有用户");
        }
        return serverResponse;
    }

    /**
     * 获取用户信息
     *
     * @param httpSession http会话
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/getuserinfo", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUserInfo(HttpSession httpSession){
        //权限查看
        if(!AccessJudger.isUser(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }

        List<BiliUser> biliUsers = (List<BiliUser>) httpSession.getAttribute(ConstUtil.USER);
        ServerResponse<List<BiliUser>> serverResponse = ServerResponse.createRespBySuccess(biliUsers);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取用户信息");
        }
        return serverResponse;
    }

    /**
     * 通过id获取用户信息
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/getuserinfobyid", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUserInfoById(HttpSession httpSession, BiliUser biliUser){
        //权限查看
        ServerResponse<List<BiliUser>> serverResponse = biliUserService.getUserInfoByIdService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            if(AccessJudger.isStaff(httpSession)){
                super.addLogsForBack(httpSession, "通过用户ID查询用户");
            }
        }
        return serverResponse;
    }

    @RequestMapping(value = "/user/getuserinfobyname", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUserInfoByName(HttpSession httpSession, BiliUser biliUser){
        //权限查看
        if(!AccessJudger.isStaff(httpSession)){
            if(!AccessJudger.isUser(httpSession)){
                return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
            }
        }

        ServerResponse<List<BiliUser>> serverResponse = biliUserService.getUserInfoByNameService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "通过用户名查询用户");
        }
        return serverResponse;
    }

    /**
     * 更新用户信息
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/updateuserinfo", method = RequestMethod.POST)
    public ServerResponse<BiliUser> updateUserInfo(HttpSession httpSession, BiliUser biliUser){
        //权限查看
        if((!AccessJudger.isUser(httpSession)) && (!AccessJudger.isStaff(httpSession))){
            return ServerResponse.createByErrorMessage("用户和管理员皆未登录");
        }

        ServerResponse<BiliUser> serverResponse = biliUserService.updateUserInfoService(biliUser);
        if(AccessJudger.isStaff(httpSession)){
            if(serverResponse.isSuccess()){
                //写入日志
                super.addLogsForBack(httpSession, "更新用户信息");
            }
        }
        return serverResponse;
    }

    /**
     * 取消用户
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link String}>
     */
    @RequestMapping(value = "/user/canceluser", method = RequestMethod.POST)
    public ServerResponse<String> cancelUser(HttpSession httpSession, BiliUser biliUser){
        //权限查看
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        ServerResponse<String> serverResponse = biliUserService.cancelUserService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "注销用户");
        }
        return serverResponse;
    }

    @RequestMapping(value = "/user/tobevip", method = RequestMethod.POST)
    public ServerResponse<BiliUser> toBeVIP(HttpSession httpSession, BiliUser biliUser, Date startTime, Integer duration){
        //权限查看
        if(!AccessJudger.isStaff(httpSession)){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<BiliUser> serverResponse = biliUserService.toBeVIPService(biliUser, startTime, duration);
        return serverResponse;
    }

    @RequestMapping(value = "/user/checkvipstate", method = RequestMethod.POST)
    public ServerResponse<Boolean> checkVIPState(HttpSession httpSession, BiliUser biliUser){
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            if(AccessJudger.isUser(httpSession)){
                biliUser = (BiliUser) httpSession.getAttribute(ConstUtil.USER);
            }else{
                return ServerResponse.createByErrorMessage("空的Session以及无效的User输入");
            }
        }
        ServerResponse<Boolean> serverResponse = biliUserService.checkVIPStateService(biliUser);
        return serverResponse;
    }

}
