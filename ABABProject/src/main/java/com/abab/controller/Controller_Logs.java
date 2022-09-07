package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliLogs;
import com.abab.service.Log4jService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller_Logs {
    @Autowired
    Log4jService log4jService;

    private ServerResponse<List<BiliLogs>> getAllLogsService(){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        List<BiliLogs> biliLogsList=null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",true);

        biliLogsList = log4jService.list(queryWrapper);

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

    private ServerResponse<List<BiliLogs>> getLogsByStaffIdService(BiliAuditor biliAuditor){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        List<BiliLogs> biliLogsList=null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",biliAuditor.getAuditorid());

        biliLogsList = log4jService.list(queryWrapper);

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

    private ServerResponse<List<BiliLogs>> getLogsByOperationService(BiliDictionary biliDictionary){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        List<BiliLogs> biliLogsList=null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("value",biliDictionary.getValue());

        biliLogsList = log4jService.list(queryWrapper);

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
            log4jService.save(biliLogs);

            serverResponse = ServerResponse.createRespBySuccess();
        }


        return serverResponse;
    }

    @RequestMapping(value = "/logs/getalllogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getAllLogs(HttpSession httpSession,
                                                     @RequestParam(defaultValue = "1") Integer pageIndex,
                                                     @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse =this.getAllLogsService();
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }


        return serverResponse;
    }

    @RequestMapping(value = "/logs/getlogsbystaffid", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByStaffId(HttpSession httpSession,
                                                           BiliAuditor biliAuditor,
                                                           @RequestParam(defaultValue = "1") Integer pageIndex,
                                                           @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse =this.getLogsByStaffIdService(biliAuditor);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/logs/getlogsbyoperation", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByOperation(HttpSession httpSession,
                                                             BiliDictionary biliDictionary,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse =this.getLogsByOperationService(biliDictionary);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/logs/addlogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> addLogs(HttpSession httpSession, BiliLogs biliLogs){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            biliLogs.setUserid(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorid());
            biliLogs.setUsername(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorname());
            biliLogs.setOptime(new Date());
            serverResponse =this.addLogsService(biliLogs);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }
        return serverResponse;
    }

    public ServerResponse<List<BiliLogs>> addLogsForBack(HttpSession httpSession, String message){
        ServerResponse<List<BiliLogs>> serverResponse = null;
        BiliLogs biliLogs = new BiliLogs();
        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            biliLogs.setUserid(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorid());
            biliLogs.setUsername(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorname());
            biliLogs.setOptime(new Date());
            biliLogs.setMatter(message);
            serverResponse =this.addLogsService(biliLogs);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }
        return serverResponse;
    }
}
