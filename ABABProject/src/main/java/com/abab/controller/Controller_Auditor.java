package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller_Auditor {

    @RequestMapping(value = "/auditor/register", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> register(HttpSession httpSession, BiliAuditor biliAuditor){
        return null;
    }

    @RequestMapping(value = "/auditor/login", method = RequestMethod.POST)
    public ServerResponse<BiliAuditor> login(HttpSession httpSession, BiliAuditor biliAuditor){
        return null;
    }

    @RequestMapping(value = "/auditor/getauditornumber", method = RequestMethod.POST)
    public ServerResponse<Integer> getAuditorNumber(HttpSession httpSession){
        return null;
    }

    @RequestMapping(value = "/auditor/getauditorsbyid", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsById(HttpSession httpSession,
                                                             BiliAuditor biliAuditor,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/auditor/getauditorsbyname", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsByName(HttpSession httpSession,
                                                               BiliAuditor biliAuditor,
                                                               @RequestParam(defaultValue = "1") Integer pageIndex,
                                                               @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/auditor/getauditorsbyauthor", method = RequestMethod.POST)
    public ServerResponse<List<BiliAuditor>> getAuditorsByAuthor(HttpSession httpSession,
                                                                 BiliAuditor biliAuditor,
                                                                 @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                 @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }
}
