package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliUser;
import com.abab.service.BiliAuditorService;
import com.abab.service.impl.BiliAuditorServiceImpl;
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

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller_Auditor extends LogAdder {

    @Autowired
    BiliAuditorService biliAuditorService;

    @Autowired
    Controller_Logs controller_logs;



    @RequestMapping(value = "/auditor/register", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> register(HttpSession httpSession, BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        serverResponse = biliAuditorService.registerService(biliAuditor);

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"增加一条管理员信息");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/login", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> login(HttpSession httpSession, BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        // 如果未登录
        if(httpSession.getAttribute(ConstUtil.ADMIN)==null&&httpSession.getAttribute(ConstUtil.STAFF)==null){
            serverResponse = biliAuditorService.loginService(biliAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.RELOGIN);
        }

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.STAFF, serverResponse.getData());
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorrole() == ConstUtil.ADMIN_ROLE_INDEX)
                httpSession.setAttribute(ConstUtil.ADMIN, serverResponse.getData());
            else{
                System.out.println("该用户是员工而非管理员");
            }
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/getauditornumber", method = RequestMethod.POST)
    public ServerResponse<Long> getAuditorNumber(HttpSession httpSession){
        ServerResponse<Long> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            serverResponse = biliAuditorService.getAuditorNumberService();
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/getauditorsbyid", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsById(HttpSession httpSession,
                                                             BiliAuditor biliAuditor,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        ServerResponse<List<BiliAuditor>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()!=ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
            else{
                serverResponse = biliAuditorService.getAuditorsByIdService(biliAuditor);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过ID查看相关管理员信息");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/getauditorsbyname", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsByName(HttpSession httpSession,
                                                               BiliAuditor biliAuditor,
                                                               @RequestParam(defaultValue = "1") Integer pageIndex,
                                                               @RequestParam(defaultValue = "5") Integer pageSize){

        PageHelper.startPage(pageIndex, pageSize);

        ServerResponse<List<BiliAuditor>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()!=ConstUtil.ADMIN_ROLE_INDEX){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
            else{
                serverResponse = biliAuditorService.getAuditorsByNameService(biliAuditor);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过姓名查看相关管理员信息");
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/getauditorsbyauthor", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsByAuthor(HttpSession httpSession,
                                                                 BiliAuditor biliAuditor,
                                                                 @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                 @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageIndex, pageSize);

        ServerResponse<List<BiliAuditor>> serverResponse = null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null&&httpSession.getAttribute(ConstUtil.ADMIN)!=null){
                serverResponse = biliAuditorService.getAuditorsByAuthorService(biliAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }

        if(serverResponse.isSuccess()){
            super.addLogsForBack(httpSession,"通过昵称查看相关管理员信息");
        }

        return serverResponse;
    }

}
