package com.abab.util;

import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.abab.service.BiliVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ExitJudger {

    private static ExitJudger exitJudger;

    @Autowired
    BiliVideoService biliVideoService;
    @Autowired
    BiliUserService biliUserService;

    public static ExitJudger getInstance(){
        if(exitJudger == null){
            exitJudger = new ExitJudger();
            return exitJudger;
        }
        return exitJudger;
    }

    public Boolean isExit_Video(BiliVideo biliVideo){
        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            return false;
        }
        if(EmptyJudger.isEmpty(biliVideoService.getById(biliVideo.getVideoid()))){
            return false;
        }
        return true;
    }

    public Boolean isExit_User(BiliUser biliUser){
        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            return false;
        }
        if(EmptyJudger.isEmpty(biliUserService.getById(biliUser.getUserid()))){
            return false;
        }
        return true;
    }
}
