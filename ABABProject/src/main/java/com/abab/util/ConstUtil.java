package com.abab.util;

public class ConstUtil {
    public static String USER = "USER";
    public static String ADMIN = "ADMIN";
    public static String STAFF = "STAFF";
    public static String VIDEO = "VIDEO";

    public static String AUDITOR_QUERY = "AUDITOR_QUERY";
    public static String COMMENT_QUERY = "COMMENT_QUERY";
    public static String BULLET_QUERY = "BULLET_QUERY";
    public static String LOGS_QUERY = "LOGS_QUERY";

    public static String USER_UNLOGIN = "用户未登录";
    public static String ADMIN_UNLOGIN = "管理员未登录";
    public static String STAFF_UNLOGIN = "员工未登录";
    public static String USER_UNEXIST = "用户不存在";
    public static String VIDEO_UNEXIST = "视频不存在";
    public static String WRONG_PASSWORD = "错误的密码";
    public static String NOTALLOW_EMPTY = "不允许为空";
    public static String OVERLIMITED_LENGTH = "长度超限";

    public static String DATA_UNEXIST = "数据不存在";

    public static String AUDIT_STATE = "审核状态";
    public static String SHELF_STATE = "上架状态";

    public static Integer GINDER_MALE = 1;
    public static Integer GINDER_FELMALE = 2;

    public static Integer USER_ROLE_INDEX = 1;//普通用户
    public static Integer VIP_ROLE_INDEX = 2;//大会员
    public static Integer STAFF_ROLE_INDEX = 3;//管理员
    public static Integer ADMIN_ROLE_INDEX = 4;//审核员

    public static String MEMO_USER_ROLE = "用户角色";
    public static String MEMO_STAFF_ROLE = "员工角色";
    public static String MEMO_AUDIT_STATE = "审核状态";
    public static String MEMO_SHELF_STATE = "上架状态";

    public static String WRONG_MEMO = "错误的Dictionary描述";

    public static Integer EXCEL_COMMENT_INDEX = 1;
    public static Integer EXCEL_AUDITOR_INDEX = 2;
    public static Integer EXCEL_BULLET_INDEX = 3;
    public static Integer EXCEL_LOG_INDEX = 4;

    public static String EXCEL_CREATE_FAILURE = "Excel创建失败";
    public static String DATA_GET_FAILURE = "数据获取失败";
    public static final int ORDER_NO_PAY=1;		//未付款
    public static final int ORDER_PAID=2;		//已经付款
    public static final int ORDER_SHIPPED=3;	//已经发货
    public static final int ORDER_SUCCESS=4;	//订单完成
    public static final int ORDER_CLOESD = 5;	//订单关闭
    public static final int ORDER_CANCELED=6;  //订单取消

    public static String UNROLE = "无权限";

    public static String RELOGIN = " 重复登录";

    public static String FILEPATH_NO_EXIST = "文件路径不存在";

}
