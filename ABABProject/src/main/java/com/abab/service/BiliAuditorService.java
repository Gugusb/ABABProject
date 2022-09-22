package com.abab.service;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliAuditor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bili_auditor】的数据库操作Service
* @createDate 2022-09-04 16:09:46
*/
public interface BiliAuditorService extends IService<BiliAuditor> {

    ServerResponse<List<BiliAuditor>> getAuditorsByNameService(BiliAuditor biliAuditor);

    ServerResponse<List<BiliAuditor>> getAuditorsService();

    ServerResponse<List<BiliAuditor>> getAuditorsByIdService(BiliAuditor biliAuditor);

    ServerResponse<List<BiliAuditor>> getAuditorsByAuthorService(BiliAuditor biliAuditor);

    ServerResponse<BiliAuditor> registerService(BiliAuditor biliAuditor, String imagePath);

    ServerResponse<BiliAuditor> loginService(BiliAuditor biliAuditor);

    ServerResponse<Long> getAuditorNumberService();
}
