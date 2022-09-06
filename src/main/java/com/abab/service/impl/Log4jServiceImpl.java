package com.abab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliLogs;
import com.abab.service.Log4jService;
import com.abab.mapper.Log4jMapper;
import org.springframework.stereotype.Service;

/**
* @author 故故sb
* @description 针对表【log4j】的数据库操作Service实现
* @createDate 2022-09-04 16:10:47
*/
@Service
public class Log4jServiceImpl extends ServiceImpl<Log4jMapper, BiliLogs>
    implements Log4jService{

}




