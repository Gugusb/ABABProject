package com.abab.util;

public class ConstUtil {
    public static String USER = "USER";
    public static String ADMIN = "ADMIN";
    public static String STAFF = "STAFF";
    public static String VIDEO = "VIDEO";

    public static Integer GINDER_MALE = 1;
    public static Integer GINDER_FELMALE = 2;

    public static Integer ADMIN_ROLE_INDEX = 1;//审核员
    public static Integer STAFF_ROLE_INDEX = 2;//管理员
    public static Integer VIP_ROLE_INDEX = 3;//大会员
    public static Integer USER_ROLE_INDEX = 4;//普通用户

    //日志操作
    public static Integer LOG_ADD=1;
    public static Integer LOG_UPDETE=2;
    public static Integer LOG_DEL=3;


    public static String UNROLE="无权限";
    public static String RELOGIN="已登录";

    public static String USER_UNLOGIN = "用户未登录";
    public static String ADMIN_UNLOGIN = "管理员未登录";
    public static String STAFF_UNLOGIN = "员工未登录";
    public static String USER_UNEXIST = "用户不存在";
    public static String VIDEO_UNEXIST = "视频不存在";
    public static String WRONG_PASSWORD = "错误的密码";
    public static String NOTALLOW_EMPTY = "不允许为空";
    public static String OVERLIMITED_LENGTH = "长度超限";

    public static String DATA_UNEXIST = "数据不存在";

    public static String MEMO_USER_ROLE = "用户角色";
    public static String MEMO_STAFF_ROLE = "员工角色";
    public static String MEMO_AUDIT_STATE = "审核状态";
    public static String MEMO_SHELF_STATE = "上架状态";

    public static String WRONG_MEMO = "错误的Dictionary描述";

}
