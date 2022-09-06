package com.abab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliComment;
import com.abab.service.CommentService;
import com.abab.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 故故sb
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-09-04 16:10:38
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, BiliComment>
    implements CommentService{

}




