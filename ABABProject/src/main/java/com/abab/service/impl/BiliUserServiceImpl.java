package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliUser;
import com.abab.service.BiliUserService;
import com.abab.mapper.BiliUserMapper;
import kotlin.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
* @author 故故sb
* @description 针对表【bili_user】的数据库操作Service实现
* @createDate 2022-09-04 16:10:15
*/
@Service
public class BiliUserServiceImpl extends ServiceImpl<BiliUserMapper, BiliUser>
    implements BiliUserService{

    /**
     * 登录服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @Override
    public ServerResponse<BiliUser> loginService(BiliUser biliUser){
        BiliUser user = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());

        user = this.getOne(queryWrapper);

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
     * 注册服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @Override
    public ServerResponse<BiliUser> registerService(BiliUser biliUser){
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
        this.save(biliUser);

        //成功创建
        return ServerResponse.createRespBySuccess(biliUser);
    }

    /**
     * 通过id获取用户信息服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @Override
    public ServerResponse<BiliUser> getUserInfoByIdService(BiliUser biliUser){
        BiliUser user = null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = this.getOne(queryWrapper);
        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            return ServerResponse.createRespBySuccess(user);
        }
    }

    /**
     * 更新用户信息服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link BiliUser}>
     */
    @Override
    public ServerResponse<BiliUser> updateUserInfoService(BiliUser biliUser){
        BiliUser user = null;
        //检查用户id是否为空
        if(biliUser.getUserid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        //检查用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = this.getOne(queryWrapper);

        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            //更新数据库
            this.updateById(biliUser);
            return ServerResponse.createRespBySuccess(biliUser);
        }
    }

    /**
     * 取消用户服务
     *
     * @param biliUser Bili用户
     * @return {@link ServerResponse}<{@link String}>
     */
    @Override
    public ServerResponse<String> cancelUserService(BiliUser biliUser){
        BiliUser user = null;
        //检查用户id是否为空
        if(biliUser.getUserid() == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        //检查用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", biliUser.getUserid());
        user = this.getOne(queryWrapper);

        if(user == null){
            return ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }else {
            //更新数据库
            this.removeById(biliUser);
            return ServerResponse.createRespBySuccess();
        }
    }

    @Override
    public ServerResponse<BiliUser> toBeVIPService(BiliUser biliUser, Date startTime, Integer duration){
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

        this.updateById(biliUser);
        return ServerResponse.createRespBySuccess(biliUser);
    }

    @Override
    public ServerResponse<Boolean> checkVIPStateService(BiliUser biliUser){
        ServerResponse<Boolean> serverResponse = null;
        if(EmptyJudger.isEmpty(this.getById(biliUser))){
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
        this.updateById(biliUser);
        return serverResponse;
    }
}




