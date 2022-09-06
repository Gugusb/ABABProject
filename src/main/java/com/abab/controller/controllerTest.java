package com.abab.controller;

import com.abab.entity.BiliComment;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.service.BiliUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controllerTest {

    @Autowired
    BiliUserService biliUserService;

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
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", biliUser.getUsername());

        return biliUserService.list(queryWrapper);
    }

    @RequestMapping(value = "/test/twoelem", method = RequestMethod.POST)
    public Object twoElem(BiliUser biliUser, BiliComment biliComment){
        return biliUser.getUsername() + "    " + biliUser.getUserid() + "    " + biliComment.getUserid();
    }
}
