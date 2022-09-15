package com.abab.util;

import javax.servlet.http.HttpSession;

public class AccessJudger {

    public static Boolean isUser(HttpSession httpSession){
        return !EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.USER));
    }

    public static Boolean isAdmin(HttpSession httpSession){
        return !EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.ADMIN));
    }

    public static Boolean isStaff(HttpSession httpSession){
        return !EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.STAFF));
    }

    public static Boolean isVideo(HttpSession httpSession){
        return !EmptyJudger.isEmpty(httpSession.getAttribute(ConstUtil.VIDEO));
    }


}
