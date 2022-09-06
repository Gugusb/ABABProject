package com.abab.controller;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller_Logs {
    //默认只能由审核员查看(Auditor权限为2)

    @RequestMapping(value = "/logs/getalllogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getAllLogs(HttpSession httpSession,
                                                     @RequestParam(defaultValue = "1") Integer pageIndex,
                                                     @RequestParam(defaultValue = "5") Integer pageSize){


        return null;
    }

    @RequestMapping(value = "/logs/getlogsbystaffid", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByStaffId(HttpSession httpSession,
                                                           BiliAuditor biliAuditor,
                                                           @RequestParam(defaultValue = "1") Integer pageIndex,
                                                           @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/logs/getlogsbyoperation", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> getLogsByOperation(HttpSession httpSession,
                                                             BiliDictionary biliDictionary,
                                                             @RequestParam(defaultValue = "1") Integer pageIndex,
                                                             @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }

    @RequestMapping(value = "/logs/addlogs", method = RequestMethod.POST)
    public ServerResponse<List<BiliLogs>> addLogs(HttpSession httpSession,
                                                  BiliLogs biliLogs,
                                                  @RequestParam(defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(defaultValue = "5") Integer pageSize){
        return null;
    }
}
