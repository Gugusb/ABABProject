package com.abab.controller;

import com.abab.entity.BiliComment;
import com.abab.entity.BiliLogs;
import com.abab.entity.BiliUser;
import com.abab.service.BiliUserService;
import com.abab.util.MD5Util;
import com.abab.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class controllerTest {

    @Autowired
    BiliUserService biliUserService;

    @Autowired
    Controller_Logs controller_logs;

    @RequestMapping(value = "/test/regex", method = RequestMethod.POST)
    public Object hellologs(HttpSession httpSession, BiliLogs biliLogs){
        return RegexUtil.getImageRelativePath("home/ubuntu/photos/asdawdasd.png") + "\n" +
                RegexUtil.getImageRelativePath("home\\ubuntu\\photos\\asdawdasd.png");
    }

    /**
     * 你好
     *
     * @param biliUser 并用户
     * @return {@link Object}
     */
    @RequestMapping(value = "/test/faq", method = RequestMethod.POST)
    public Object hello(BiliUser biliUser){
        return "hello" + biliUser.getUserauthor();
    }

    /**
     * getone
     *
     * @param biliUser 并用户
     * @return {@link Object}
     */
    @RequestMapping(value = "/test/getpeople", method = RequestMethod.POST)
    public Object getone(BiliUser biliUser){
        return biliUserService.getById(biliUser);
    }

    @RequestMapping(value = "/test/twoelem", method = RequestMethod.POST)
    public Object twoElem(BiliUser biliUser, BiliComment biliComment){
        return biliUser.getUsername() + "    " + biliUser.getUserid() + "    " + biliComment.getUserid();
    }
    @RequestMapping(value = "/test/md5", method = RequestMethod.POST)
    public Object md5(String psw){
        return MD5Util.getMD5(psw);
    }

}
