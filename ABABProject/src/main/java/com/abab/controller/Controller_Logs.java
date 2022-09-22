package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliLogs;
import com.abab.service.Log4jService;
import com.abab.util.AccessJudger;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.abab.util.ExcelDatasProduce;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
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

    @RequestMapping(value = "/logs/getalllogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getAllLogs(HttpSession httpSession,
                                                     @RequestParam(defaultValue = "1") Integer pageIndex,
                                                     @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(AccessJudger.isStaff(httpSession) && AccessJudger.isAdmin(httpSession)){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse = log4jService.getAllLogsService();
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.LOGS_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/logs/getlogsbystaffid", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByStaffId(HttpSession httpSession,
                                                           BiliAuditor biliAuditor,
                                                           @RequestParam(defaultValue = "1") Integer pageIndex,
                                                           @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(AccessJudger.isStaff(httpSession) && AccessJudger.isAdmin(httpSession)){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse = log4jService.getLogsByStaffIdService(biliAuditor);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.LOGS_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/logs/getlogsbyoperation", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByOperation(HttpSession httpSession,
                                                             BiliDictionary biliDictionary,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(AccessJudger.isStaff(httpSession) && AccessJudger.isAdmin(httpSession)){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()==ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse = log4jService.getLogsByOperationService(biliDictionary);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.LOGS_QUERY, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/logs/addlogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> addLogs(HttpSession httpSession, BiliLogs biliLogs){
        ServerResponse<List<BiliLogs>> serverResponse = null;

        if(AccessJudger.isStaff(httpSession)){
            biliLogs.setUserid(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorid());
            biliLogs.setUsername(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorname());
            biliLogs.setOptime(new Date());


            serverResponse = log4jService.addLogsService(biliLogs);

        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        return serverResponse;
    }


    @RequestMapping(value = "/logs/downloadalllogs", method = RequestMethod.POST)
    public ServerResponse<String> downloadAllLogs(HttpSession httpSession){
        ServerResponse<String> serverResponse = null;

        if(AccessJudger.isStaff(httpSession) && AccessJudger.isAdmin(httpSession)){

            if(!EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.LOGS_QUERY))){

                String filepath = ExcelDatasProduce.ProducerExcel(ConstUtil.EXCEL_LOG_INDEX, httpSession.getAttribute(ConstUtil.LOGS_QUERY));
                serverResponse = ServerResponse.createRespBySuccess(filepath);

            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_GET_FAILURE);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
        }

        return serverResponse;
    }

}
