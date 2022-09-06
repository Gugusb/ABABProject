package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliUser;
import com.abab.service.BiliAuditorService;
import com.abab.util.ConstUtil;
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
public class Controller_Auditor {

    @Autowired
    BiliAuditorService biliAuditorService;

    public ServerResponse<List<BiliAuditor>> getAuditorsByNameService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;

        List<BiliAuditor> listAuditor = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorname", biliAuditor.getAuditorname());
        listAuditor = biliAuditorService.list(queryWrapper);

        if(listAuditor!=null){
            if(listAuditor.size()==0){
                serverResponse = ServerResponse.createByErrorMessage("无此用户");
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage("当前无数据");
        }

        return serverResponse;
    }

    public ServerResponse<List<BiliAuditor>> getAuditorsByIdService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;
        List<BiliAuditor> listAuditor = null;


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorid", biliAuditor.getAuditorid());
        listAuditor = biliAuditorService.list(queryWrapper);

        if(listAuditor!=null){
            if(listAuditor.size()==0){
                serverResponse = ServerResponse.createByErrorMessage("找不到此用户");
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage("当前无数据");
        }

        return serverResponse;
    }

    public ServerResponse<List<BiliAuditor>> getAuditorsByAuthorService(BiliAuditor biliAuditor){
        //业务层逻辑
        ServerResponse<List<BiliAuditor>> serverResponse = null;
        List<BiliAuditor> listAuditor = null;


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorauthor", biliAuditor.getAuditorauthor());
        listAuditor = biliAuditorService.list(queryWrapper);

        if(listAuditor!=null){
            serverResponse = ServerResponse.createRespBySuccess(listAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage("当前无数据");
        }

        return serverResponse;
    }

    public ServerResponse<BiliAuditor> registerService(BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        //业务层逻辑
        if(biliAuditor.getAuditorname()==null||biliAuditor.getAuditorname()==""){
            serverResponse=ServerResponse.createByErrorMessage("请填写用户名");
        } else if(biliAuditor.getAuditorauthor()==null||biliAuditor.getAuditorauthor()==""){
            serverResponse=ServerResponse.createByErrorMessage("请填写用户昵称");
        } else if(!(biliAuditor.getGender()==1||biliAuditor.getGender()==2)){
            serverResponse=ServerResponse.createByErrorMessage("请选择用户性别");
        } else if(!(biliAuditor.getAuditorrole()==1||biliAuditor.getAuditorrole()==2)){
            serverResponse=ServerResponse.createByErrorMessage("请选择员工角色");
        }else if(biliAuditor.getBirthday()==null){
            serverResponse=ServerResponse.createByErrorMessage("请填写生日");
        }else if(biliAuditor.getAuditorname().length()>50){
            serverResponse=ServerResponse.createByErrorMessage("用户名长度请不要超过50");
        } else if(biliAuditor.getAuditorauthor().length()>50){
            serverResponse=ServerResponse.createByErrorMessage("用户昵称长度请不要超过50");
        } else if(biliAuditor.getPassword().length()>50){
            serverResponse=ServerResponse.createByErrorMessage("用户密码长度请不要超过50");
        }
        else{
            biliAuditorService.save(biliAuditor);//向db插入用户

            serverResponse=ServerResponse.createRespBySuccess(biliAuditor);
        }

        return serverResponse;
    }

    public ServerResponse<BiliAuditor> loginService(BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;
        BiliAuditor Auditor = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("auditorid", biliAuditor.getAuditorid());
        Auditor = biliAuditorService.getOne(queryWrapper);


        if(Auditor==null){
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

    public ServerResponse<Long> getAuditorNumberService(){
        ServerResponse<Long> serverResponse=null;

        Long num = biliAuditorService.count();

        serverResponse=ServerResponse.createRespBySuccess(num);

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/register", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> register(HttpSession httpSession, BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        serverResponse = registerService(biliAuditor);

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/login", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> login(HttpSession httpSession, BiliAuditor biliAuditor){
        ServerResponse<BiliAuditor> serverResponse = null;

        // 如果未登录
        if(httpSession.getAttribute(ConstUtil.ADMIN)==null&&httpSession.getAttribute(ConstUtil.STAFF)==null){
            serverResponse = loginService(biliAuditor);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.RELOGIN);
        }

        if(serverResponse.isSuccess()){
            httpSession.setAttribute(ConstUtil.STAFF, serverResponse.getData());
            if(biliAuditor.getAuditorrole()== ConstUtil.ADMIN_ROLE)
                httpSession.setAttribute(ConstUtil.ADMIN, serverResponse.getData());
            httpSession.setMaxInactiveInterval(30*60);
        }

        return serverResponse;
    }

    @RequestMapping(value = "/auditor/getauditornumber", method = RequestMethod.POST)
    public ServerResponse<Long> getAuditorNumber(HttpSession httpSession){
        ServerResponse<Long> serverResponse=null;

        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            serverResponse = getAuditorNumberService();
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
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()!=2){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
            else{
                serverResponse = getAuditorsByIdService(biliAuditor);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
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
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()!=2){
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.UNROLE);
            }
            else{
                serverResponse = getAuditorsByNameService(biliAuditor);
            }
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
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
            if(((BiliAuditor)httpSession.getAttribute(ConstUtil.ADMIN)).getAuditorrole()!=2){
                serverResponse = getAuditorsByAuthorService(biliAuditor);
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

}
