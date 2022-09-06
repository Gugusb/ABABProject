package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller_User {

    @Autowired
    BiliUserService biliUserService;

    private ServerResponse<BiliUser> loginService(BiliUser biliUser){
        BiliUser user = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());

        user = biliUserService.getOne(queryWrapper);

        if(user==null){
            return ServerResponse.createByErrorMessage("无此用户！");
        }else{
            if(user.getPassword().equals(biliUser.getPassword())){
                return ServerResponse.createRespBySuccess(user);
            }else{
                return ServerResponse.createByErrorMessage("密码错误！");
            }
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ServerResponse<BiliUser> login(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = loginService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    private ServerResponse<BiliUser> registerService(BiliUser biliUser){
        //检查用户信息是否全面
        ArrayList<Pair<Object, String>> l = new ArrayList<>();
        l.add(new Pair<>(biliUser.getUsername(), "用户名称"));
        l.add(new Pair<>(biliUser.getPassword(), "用户密码"));
        l.add(new Pair<>(biliUser.getUserauthor(), "用户昵称"));
        l.add(new Pair<>(biliUser.getBirthday(), "用户生日"));
        l.add(new Pair<>(biliUser.getUseravatar(), "用户头像图片"));
        for (Pair<Object, String> p : l){
            if(p.getFirst() == null){
                return ServerResponse.createByErrorMessage(p.getSecond() + "不能为空！");
            }
        }

        //检查信息是否合法
        ArrayList<Pair<Object, Integer>> Legality = new ArrayList<>();
        Legality.add(new Pair<>(biliUser.getUsername(), 200));
        Legality.add(new Pair<>(biliUser.getPassword(), 50));
        Legality.add(new Pair<>(biliUser.getUserauthor(), 200));
        Legality.add(new Pair<>(biliUser.getPhone(), 20));
        Legality.add(new Pair<>(biliUser.getUserautograph(), 500));
        Legality.add(new Pair<>(biliUser.getUseravatar(), 500));
        Legality.add(new Pair<>(biliUser.getMemo(), 255));
        for (Pair<Object, Integer> p : Legality){
            if(p.getFirst() != null){
                if(p.getFirst().toString().length() >= p.getSecond()){
                    return ServerResponse.createByErrorMessage(p.getSecond() + p.getFirst().toString() + "长度超限！");
                }
            }
        }
        //填充必要信息
        if(biliUser.getGender() == null){
            biliUser.setGender(1);
        }
        else if(biliUser.getGender() != 1 && biliUser.getGender() != 2){
            biliUser.setGender(1);
        }
        biliUser.setUserrole(1);

        //向数据库存储
        biliUserService.save(biliUser);

        //成功创建
        return ServerResponse.createRespBySuccess(biliUser);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ServerResponse<BiliUser> register(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = registerService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    private ServerResponse<BiliUser> submitNumber(BiliVideo biliVideo){
        return null;
    }

    @RequestMapping(value = "/user/submitvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> submitVideo(HttpSession httpSession, BiliVideo biliVideo){
        return null;
    }

    @RequestMapping(value = "/user/getusernumber", method = RequestMethod.POST)
    public ServerResponse<Long> getUserNumber(HttpSession httpSession){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.ADMIN) == null ||
                httpSession.getAttribute(ConstUtil.ADMIN) == ""){
            return ServerResponse.createByErrorMessage("未查询到管理员权限");
        }
        return ServerResponse.createRespBySuccess(biliUserService.count());
    }

    @RequestMapping(value = "/user/getusers", method = RequestMethod.POST)
    public ServerResponse<List<BiliUser>> getUsers(HttpSession httpSession,
                                                   @RequestParam(defaultValue = "1") Integer pageIndex,
                                                   @RequestParam(defaultValue = "5") Integer pageSize){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.ADMIN) == null ||
                httpSession.getAttribute(ConstUtil.ADMIN) == ""){
            return ServerResponse.createByErrorMessage("未查询到管理员权限");
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<BiliUser> arrayList = biliUserService.list();
        return ServerResponse.createRespBySuccess(arrayList);
    }

    @RequestMapping(value = "/user/getuserinfo", method = RequestMethod.POST)
    public ServerResponse<BiliUser> getUserInfo(HttpSession httpSession){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.USER) == null ||
                httpSession.getAttribute(ConstUtil.USER) == ""){
            return ServerResponse.createByErrorMessage("未查询登陆状态");
        }

        BiliUser biliUser = (BiliUser) httpSession.getAttribute(ConstUtil.USER);
        return ServerResponse.createRespBySuccess(biliUser);
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
