package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliComment;
import com.abab.entity.BiliVideo;
import com.abab.mapper.CommentMapper;
import com.abab.service.CommentService;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 故故sb
 * @description 针对表【comment】的数据库操作Service实现
 * @createDate 2022-09-04 16:10:38
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, BiliComment>
        implements CommentService {

    @Override
    public ServerResponse<List<BiliComment>> getCommentsService(){
        ServerResponse<List<BiliComment>> serverResponse = null;

        List<BiliComment> biliCommentList=null;

        QueryWrapper qe = new QueryWrapper();
        qe.isNotNull("id" );
        biliCommentList = this.list(qe);

        if(!EmptyJudger.isEmpty(biliCommentList)){
            if(biliCommentList.isEmpty()){
                serverResponse =ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
            }
            else
                serverResponse = ServerResponse.createRespBySuccess(biliCommentList);
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliComment>> getCommentsByVideoIdService(BiliVideo biliVideo){
        ServerResponse<List<BiliComment>> serverResponse = null;

        List<BiliComment> biliCommentList=null;

        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            serverResponse =ServerResponse.createByErrorMessage("videoid" + ConstUtil.NOTALLOW_EMPTY);
        }
        else{
            QueryWrapper qe = new QueryWrapper();
            qe.eq("videoid" , biliVideo.getVideoid());
            biliCommentList = this.list(qe);

            if(!EmptyJudger.isEmpty(biliCommentList)){
                if(biliCommentList.isEmpty()){
                    serverResponse =ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
                }
                else
                    serverResponse = ServerResponse.createRespBySuccess(biliCommentList);
            }
            else{
                serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
            }
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliComment> postCommentService(BiliComment biliComment){
        ServerResponse<BiliComment> serverResponse = null;

        if(EmptyJudger.isEmpty(biliComment.getUserid())){
            serverResponse = ServerResponse.createByErrorMessage("用户ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliComment.getContent())){
            serverResponse = ServerResponse.createByErrorMessage("评论内容" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliComment.getVideoid())) {
            serverResponse = ServerResponse.createByErrorMessage("视频ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(biliComment.getContent().length() > 200) {
            serverResponse = ServerResponse.createByErrorMessage("评论" + ConstUtil.OVERLIMITED_LENGTH);
        }else if(!EmptyJudger.isEmpty(biliComment.getMemo()) && biliComment.getMemo().length() > 200){
            serverResponse = ServerResponse.createByErrorMessage("评论备注" + ConstUtil.OVERLIMITED_LENGTH);
        }else{
            this.save(biliComment);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliComment> deleteCommentService(BiliComment biliComment){
        //按照评论id删除评论
        ServerResponse<BiliComment> serverResponse = null;

        if(!EmptyJudger.isEmpty(biliComment.getId())){
            QueryWrapper qe = new QueryWrapper();
            qe.eq("id",biliComment.getId());

            this.remove(qe);

            serverResponse = ServerResponse.createRespBySuccess();
        }
        else{
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
        }


        return serverResponse;
    }


}




