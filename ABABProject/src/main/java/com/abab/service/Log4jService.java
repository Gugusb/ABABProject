package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.abab.entity.BiliDictionary;
import com.abab.entity.BiliLogs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【log4j】的数据库操作Service
* @createDate 2022-09-04 16:10:47
*/
public interface Log4jService extends IService<BiliLogs> {

    ServerResponse<List<BiliLogs>> getAllLogsService();

    ServerResponse<List<BiliLogs>> getLogsByStaffIdService(BiliAuditor biliAuditor);

    ServerResponse<List<BiliLogs>> getLogsByOperationService(BiliDictionary biliDictionary);

    ServerResponse<List<BiliLogs>> addLogsService(BiliLogs biliLogs);
}
