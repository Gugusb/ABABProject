package com.abab.util;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliLogs;
import com.abab.service.Log4jService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class LogAdder {
    @Autowired
    Log4jService log4jService;
    
    public ServerResponse<List<BiliLogs>> addLogsForBack(HttpSession httpSession, String message){
        ServerResponse<List<BiliLogs>> serverResponse = null;
        BiliLogs biliLogs = new BiliLogs();
        if(httpSession.getAttribute(ConstUtil.STAFF)!=null){
            biliLogs.setUserid(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorid());
            biliLogs.setUsername(((BiliAuditor)httpSession.getAttribute(ConstUtil.STAFF)).getAuditorname());
            biliLogs.setOptime(new Date());
            biliLogs.setMatter(message);
            serverResponse = log4jService.addLogsService(biliLogs);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.ADMIN_UNLOGIN);
        }
        return serverResponse;
    }

}
