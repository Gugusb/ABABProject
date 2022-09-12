package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.abab.service.BiliVideoService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.LogAdder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.*;

@RestController
public class Controller_User extends LogAdder {

    @Autowired
    BiliUserService biliUserService;

    @Autowired
    BiliVideoService biliVideoService;

    /**
     * 登录服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    private ServerResponse<BiliUser> loginService(BiliUser biliUser){
        BiliUser user = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());

        user = biliUserService.getOne(queryWrapper);

        if(user==null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else{
            if(user.getPassword().equals(biliUser.getPassword())){
                return ServerResponse.createRespBySuccess(user);
            }else{
                return ServerResponse.createByErrorMessage(ConstUtil.WRONG_PASSWORD);
            }
        }
    }

    /**
     * 登录
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ServerResponse<BiliUser> login(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = loginService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    /**
     * 注册服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
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
                return ServerResponse.createByErrorMessage(p.getSecond() + ConstUtil.NOTALLOW_EMPTY);
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
                    return ServerResponse.createByErrorMessage(p.getSecond() + p.getFirst().toString() + ConstUtil.OVERLIMITED_LENGTH);
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

    /**
     * 注册
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ServerResponse<BiliUser> register(HttpSession httpSession, BiliUser biliUser){
        ServerResponse<BiliUser> serverResponse = registerService(biliUser);

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.USER, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30 * 60);
        }
        return serverResponse;
    }

    private ServerResponse<BiliVideo> submitVideoService(BiliUser biliUser, BiliVideo biliVideo){
        //检查用户信息是否全面
        ArrayList<Pair<Object, String>> l = new ArrayList<>();
        l.add(new Pair<>(biliVideo.getVideotitle(), "视频标题"));
        l.add(new Pair<>(biliVideo.getVideopath(), "视频源"));
        l.add(new Pair<>(biliVideo.getCoverimage(), "视频封面"));
        for (Pair<Object, String> p : l){
            if(p.getFirst() == null){
                return ServerResponse.createByErrorMessage(p.getSecond() + ConstUtil.NOTALLOW_EMPTY);
            }
        }

        //检查信息是否合法
        ArrayList<Pair<Object, Integer>> Legality = new ArrayList<>();
        Legality.add(new Pair<>(biliVideo.getVideotitle(), 50));
        Legality.add(new Pair<>(biliVideo.getVideointrbriefing(), 200));
        Legality.add(new Pair<>(biliVideo.getVideopath(), 500));
        Legality.add(new Pair<>(biliVideo.getCoverimage(), 300));
        Legality.add(new Pair<>(biliVideo.getMemo(), 255));
        for (Pair<Object, Integer> p : Legality){
            if(p.getFirst() != null){
                if(p.getFirst().toString().length() >= p.getSecond()){
                    return ServerResponse.createByErrorMessage(p.getFirst().toString() + ConstUtil.OVERLIMITED_LENGTH);
                }
            }
        }

        //数据补全
        //随机生成AV号
        Random random = new Random();
        int randav = 1000000 + random.nextInt(900000);
        biliVideo.setVideoid("AV" + randav);
        //初始化三连数量
        biliVideo.setThumbs(0L);
        biliVideo.setCoin(0l);
        biliVideo.setCoin(0L);
        biliVideo.setForwarding(0L);
        biliVideo.setBullet(0L);
        biliVideo.setComment(0L);
        //初始化提交时间
        biliVideo.setUploadtime(new Date());
        //设置提交用户
        biliVideo.setUploaderid(biliUser.getUserid());
        //初始化审核/上架状态
        biliVideo.setAuditingid(5);
        biliVideo.setGrounding(9);

        //存储视频数据
        biliVideoService.save(biliVideo);
        return ServerResponse.createRespBySuccess(biliVideo);
    }

    /**
     * 提交视频
     *
     * @param httpSession http会话
     * @param biliVideo   箱内视频
     * @return {@link ServerResponse}<{@link BiliVideo}>
     */
    @RequestMapping(value = "/user/submitvideo", method = RequestMethod.POST)
    public ServerResponse<BiliVideo> submitVideo(HttpSession httpSession, BiliVideo biliVideo){
        if(EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.USER))){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }
        ServerResponse<BiliVideo> serverResponse = submitVideoService((BiliUser) httpSession.getAttribute(ConstUtil.USER), biliVideo);
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
        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
                httpSession.getAttribute(ConstUtil.STAFF) == ""){
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
        //权限查看
        if(httpSession.getAttribute(ConstUtil.ADMIN) == null ||
                httpSession.getAttribute(ConstUtil.ADMIN) == ""){
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
    public ServerResponse<BiliUser> getUserInfo(HttpSession httpSession){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.USER) == null ||
                httpSession.getAttribute(ConstUtil.USER) == ""){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }

        BiliUser biliUser = (BiliUser) httpSession.getAttribute(ConstUtil.USER);
        ServerResponse<BiliUser> serverResponse = ServerResponse.createRespBySuccess(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "获取用户信息");
        }
        return serverResponse;
    }

    /**
     * 通过id获取用户信息服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    private ServerResponse<BiliUser> getUserInfoByIdService(BiliUser biliUser){
        BiliUser user = null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = biliUserService.getOne(queryWrapper);
        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            return ServerResponse.createRespBySuccess(user);
        }
    }

    /**
     * 通过id获取用户信息
     *
     * @param httpSession http会话
     * @param biliUser    Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @RequestMapping(value = "/user/getuserinfobyid", method = RequestMethod.POST)
    public ServerResponse<BiliUser> getUserInfoById(HttpSession httpSession, BiliUser biliUser){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
                httpSession.getAttribute(ConstUtil.STAFF) == ""){
            if(httpSession.getAttribute(ConstUtil.USER) == null ||
                    httpSession.getAttribute(ConstUtil.USER) == ""){
                return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
            }
        }

        ServerResponse<BiliUser> serverResponse = getUserInfoByIdService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "通过用户ID查询用户");
        }
        return serverResponse;
    }

    /**
     * 更新用户信息服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    private ServerResponse<BiliUser> updateUserInfoService(BiliUser biliUser){
        BiliUser user = null;
        //检查用户id是否为空
        if(biliUser.getUserid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        //检查用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = biliUserService.getOne(queryWrapper);

        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            //更新数据库
            biliUserService.updateById(biliUser);
            return ServerResponse.createRespBySuccess(biliUser);
        }
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
        if(httpSession.getAttribute(ConstUtil.USER) == null ||
                httpSession.getAttribute(ConstUtil.USER) == ""){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        ServerResponse<BiliUser> serverResponse = updateUserInfoService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "更新用户信息");
        }
        return serverResponse;
    }

    /**
     * 取消用户服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link String}>
     */
    private ServerResponse<String> cancelUserService(BiliUser biliUser){
        BiliUser user = null;
        //检查用户id是否为空
        if(biliUser.getUserid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        //检查用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = biliUserService.getOne(queryWrapper);

        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            //更新数据库
            biliUserService.removeById(biliUser);
            return ServerResponse.createRespBySuccess();
        }
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
        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
                httpSession.getAttribute(ConstUtil.STAFF) == ""){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }

        ServerResponse<String> serverResponse = cancelUserService(biliUser);
        if(serverResponse.isSuccess()){
            //写入日志
            super.addLogsForBack(httpSession, "注销用户");
        }
        return serverResponse;
    }

    private ServerResponse<BiliUser> toBeVIPService(BiliUser biliUser, Date startTime, Integer duration){
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        if(biliUser.getUserrole() >= 2){
            return ServerResponse.createByErrorMessage("用户已经是大会员");
        }
        //更新用户大会员信息
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_YEAR, duration);
        biliUser.setUserrole(2);
        biliUser.setStarttime(startTime);
        biliUser.setEndtime(calendar.getTime());

        biliUserService.updateById(biliUser);
        return ServerResponse.createRespBySuccess(biliUser);
    }

    @RequestMapping(value = "/user/tobevip", method = RequestMethod.POST)
    public ServerResponse<BiliUser> toBeVIP(HttpSession httpSession, BiliUser biliUser, Date startTime, Integer duration){
        //权限查看
        if(httpSession.getAttribute(ConstUtil.STAFF) == null ||
                httpSession.getAttribute(ConstUtil.STAFF) == ""){
            return ServerResponse.createByErrorMessage(ConstUtil.STAFF_UNLOGIN);
        }
        ServerResponse<BiliUser> serverResponse = toBeVIPService(biliUser, startTime, duration);
        return serverResponse;
    }

    private ServerResponse<Boolean> checkVIPStateService(BiliUser biliUser){
        ServerResponse<Boolean> serverResponse = null;
        if(EmptyJudger.isEmpty(biliUserService.getById(biliUser))){
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        else if(EmptyJudger.isEmpty(biliUser.getEndtime())){
            biliUser.setUserrole(1);
            serverResponse = ServerResponse.createRespBySuccess(false);
        }else{
            Date timeNow = new Date();
            if(biliUser.getEndtime().after(timeNow)){
                biliUser.setUserrole(2);
                serverResponse = ServerResponse.createRespBySuccess(true);
            }else {
                biliUser.setUserrole(1);
                serverResponse = ServerResponse.createRespBySuccess(false);
            }
        }
        biliUserService.updateById(biliUser);
        return serverResponse;
    }

    @RequestMapping(value = "/user/checkvipstate", method = RequestMethod.POST)
    public ServerResponse<Boolean> checkVIPState(HttpSession httpSession, BiliUser biliUser){
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.USER))){
                biliUser = (BiliUser) httpSession.getAttribute(ConstUtil.USER);
            }else{
                return ServerResponse.createByErrorMessage("空的Session以及无效的User输入");
            }
        }
        ServerResponse<Boolean> serverResponse = checkVIPStateService(biliUser);
        return serverResponse;
    }

}
