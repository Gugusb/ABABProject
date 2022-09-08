package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliAuditor;
import com.abab.service.BiliAuditorService;
import com.abab.mapper.BiliAuditorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_auditor】的数据库操作Service实现
* @createDate 2022-09-04 16:09:46
*/
@Service
public class BiliAuditorServiceImpl extends ServiceImpl<BiliAuditorMapper, BiliAuditor>
    implements BiliAuditorService{

    @Override
    public ServerResponse<List<BiliAuditor>> getAuditorsByNameService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;

        List<BiliAuditor> listAuditor = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorname", biliAuditor.getAuditorname());
        listAuditor = this.list(queryWrapper);

        if(listAuditor!=null){
            if(listAuditor.size()==0){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliAuditor>> getAuditorsByIdService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;
        List<BiliAuditor> listAuditor = null;


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorid", biliAuditor.getAuditorid());
        listAuditor = this.list(queryWrapper);

        if(listAuditor!=null){
            if(listAuditor.size()==0){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliAuditor>> getAuditorsByAuthorService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;
        List<BiliAuditor> listAuditor = null;


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorauthor", biliAuditor.getAuditorauthor());
        listAuditor = this.list(queryWrapper);

        if(listAuditor!=null){
            serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage("当前无数据");
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliAuditor> registerService(BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        //业务层逻辑
        if(EmptyJudger.isEmpty(biliAuditor.getAuditorname())){
            serverResponse=ServerResponse.createByErrorMessage("用户名" + ConstUtil.NOTALLOW_EMPTY);
        } else if(EmptyJudger.isEmpty(biliAuditor.getAuditorauthor())){
            serverResponse=ServerResponse.createByErrorMessage("用户昵称" + ConstUtil.NOTALLOW_EMPTY);
        } else if(!(biliAuditor.getGender() == ConstUtil.GINDER_MALE || biliAuditor.getGender() == ConstUtil.GINDER_FELMALE)){
            serverResponse=ServerResponse.createByErrorMessage("用户性别" + ConstUtil.NOTALLOW_EMPTY);
        } else if(!(biliAuditor.getAuditorrole() == ConstUtil.STAFF_ROLE_INDEX || biliAuditor.getAuditorrole() == ConstUtil.ADMIN_ROLE_INDEX)){
            serverResponse=ServerResponse.createByErrorMessage("员工角色" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliAuditor.getBirthday())){
            serverResponse=ServerResponse.createByErrorMessage("生日" + ConstUtil.NOTALLOW_EMPTY);
        }else if(biliAuditor.getAuditorname().length() > 50){
            serverResponse=ServerResponse.createByErrorMessage("用户名长度请不要超过50");
        } else if(biliAuditor.getAuditorauthor().length() > 50){
            serverResponse=ServerResponse.createByErrorMessage("用户昵称长度请不要超过50");
        } else if(biliAuditor.getPassword().length() > 50){
            serverResponse=ServerResponse.createByErrorMessage("用户密码长度请不要超过50");
        }
        else{
            this.save(biliAuditor);//向db插入用户

            serverResponse=ServerResponse.createRespBySuccess(biliAuditor);
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliAuditor> loginService(BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;
        BiliAuditor Auditor = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorid", biliAuditor.getAuditorid());
        Auditor = this.getOne(queryWrapper);


        if(EmptyJudger.isEmpty(Auditor)){
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }
        else{
            if(Auditor.getPassword().equals(biliAuditor.getPassword())){
                serverResponse = ServerResponse.createRespBySuccess(Auditor);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.WRONG_PASSWORD);
            }
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<Long> getAuditorNumberService(){
        ServerResponse<Long> serverResponse=null;

        Long num = this.count();

        serverResponse=ServerResponse.createRespBySuccess(num);

        return serverResponse;
    }

}




