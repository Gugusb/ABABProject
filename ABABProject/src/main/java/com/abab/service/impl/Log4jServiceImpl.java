package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliDictionary;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliLogs;
import com.abab.service.Log4jService;
import com.abab.mapper.Log4jMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【log4j】的数据库操作Service实现
* @createDate 2022-09-04 16:10:47
*/
@Service
public class Log4jServiceImpl extends ServiceImpl<Log4jMapper, BiliLogs>
    implements Log4jService{

    @Override
    public ServerResponse<List<BiliLogs>> getAllLogsService(){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        List<BiliLogs> biliLogsList=null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNotNull("id");

        biliLogsList = this.list(queryWrapper);

        if(biliLogsList!=null){
            if(biliLogsList.size()==0){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(biliLogsList);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNEXIST);
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliLogs>> getLogsByStaffIdService(BiliAuditor biliAuditor){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        List<BiliLogs> biliLogsList=null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",biliAuditor.getAuditorid());

        biliLogsList = this.list(queryWrapper);

        if(EmptyJudger.isEmpty(biliAuditor.getAuditorid())){
            serverResponse = ServerResponse.createByErrorMessage("Auditorid" + ConstUtil.NOTALLOW_EMPTY);
            System.out.println("getAuditorid: " + biliAuditor.getAuditorid());
        }
        else{
            if(!EmptyJudger.isEmpty(biliAuditor.getAuditorid())){
                serverResponse = ServerResponse.createRespBySuccess(biliLogsList);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
                System.out.println("getAuditorid: " + biliAuditor.getAuditorid());

            }
        }


        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliLogs>> getLogsByOperationService(BiliDictionary biliDictionary){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(EmptyJudger.isEmpty(biliDictionary.getValue())){
            serverResponse = ServerResponse.createByErrorMessage("Value" + ConstUtil.NOTALLOW_EMPTY);
        }
        else{
            List<BiliLogs> biliLogsList=null;
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.like("matter",biliDictionary.getValue());

            biliLogsList = this.list(queryWrapper);

            if(!EmptyJudger.isEmpty(this)){
                serverResponse = ServerResponse.createRespBySuccess(biliLogsList);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
            }
        }

        return serverResponse;

    }

    @Override
    public ServerResponse<List<BiliLogs>> addLogsService(BiliLogs biliLogs){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(EmptyJudger.isEmpty(biliLogs.getUserid())){
            serverResponse = ServerResponse.createByErrorMessage("员工编号不能为空");
        }else if(EmptyJudger.isEmpty(biliLogs.getUsername())){
            serverResponse = ServerResponse.createByErrorMessage("员工姓名不能为空");
        }else if(EmptyJudger.isEmpty(biliLogs.getMatter())){
            serverResponse = ServerResponse.createByErrorMessage("操作记录不能为空");
        }else if(EmptyJudger.isEmpty(biliLogs.getOptime())){
            serverResponse = ServerResponse.createByErrorMessage("操作时间不能为空");
        }else if(biliLogs.getMatter().length()>300){
            serverResponse = ServerResponse.createByErrorMessage("操作记录" + ConstUtil.OVERLIMITED_LENGTH);
        }else if(biliLogs.getUsername().length()>300){
            serverResponse = ServerResponse.createByErrorMessage("员工姓名" + ConstUtil.OVERLIMITED_LENGTH);
        }else{
            this.save(biliLogs);

            serverResponse = ServerResponse.createRespBySuccess();
        }


        return serverResponse;
    }

}




