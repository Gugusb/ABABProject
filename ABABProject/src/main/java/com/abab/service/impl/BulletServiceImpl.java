package com.abab.service.impl;

import com.abab.common.ServerResponse;
import com.abab.entity.BiliUser;
import com.abab.entity.BiliVideo;
import com.abab.util.ConstUtil;
import com.abab.util.EmptyJudger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.BiliBullet;
import com.abab.service.BulletService;
import com.abab.mapper.BulletMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 故故sb
* @description 针对表【bullet】的数据库操作Service实现
* @createDate 2022-09-04 16:10:31
*/
@Service
public class BulletServiceImpl extends ServiceImpl<BulletMapper, BiliBullet>
    implements BulletService{

    @Override
    public ServerResponse<List<BiliBullet>> getBulletsService(){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        List<BiliBullet> biliBulletList=null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNotNull("id");
        biliBulletList = this.list(queryWrapper);

        if(!EmptyJudger.isEmpty(biliBulletList)){
            serverResponse=ServerResponse.createRespBySuccess(biliBulletList);

        }
        else{
            serverResponse=ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);

        }
        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliBullet>> getBulletsByVideoIdService(BiliVideo biliVideo){
        ServerResponse<List<BiliBullet>> serverResponse=null;

        List<BiliBullet> biliBulletList=null;

        if(EmptyJudger.isEmpty(biliVideo.getVideoid())){
            serverResponse =ServerResponse.createByErrorMessage("videoid" + ConstUtil.NOTALLOW_EMPTY);
        }
        else{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("videoid", biliVideo.getVideoid());
            biliBulletList = this.list(queryWrapper);

            if(!EmptyJudger.isEmpty(biliBulletList)){
                serverResponse=ServerResponse.createRespBySuccess(biliBulletList);

            }
            else{
                serverResponse=ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
                System.out.println("当前videoid："+biliVideo.getVideoid());

            }
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<BiliUser>> getBulletsByIdService(BiliUser biliUser){
        ServerResponse<List<BiliUser>> serverResponse=null;

        List<BiliUser> biliBulletList=null;

        if(EmptyJudger.isEmpty(biliUser.getUserid())){
            serverResponse =ServerResponse.createByErrorMessage("userid" + ConstUtil.NOTALLOW_EMPTY);
        }
        else{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("userid", biliUser.getUserid());
            biliBulletList = this.list(queryWrapper);

            if(!EmptyJudger.isEmpty(biliBulletList)){
                serverResponse=ServerResponse.createRespBySuccess(biliBulletList);

            }
            else{
                serverResponse=ServerResponse.createByErrorMessage(ConstUtil.DATA_UNEXIST);
                System.out.println("当前videoid："+biliUser.getUserid());

            }
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliBullet> postBulletService(BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(EmptyJudger.isEmpty(biliBullet.getUserid())){
            serverResponse = ServerResponse.createByErrorMessage(ConstUtil.USER_UNLOGIN);
        }else if(EmptyJudger.isEmpty(biliBullet.getVideoid())){
            serverResponse = ServerResponse.createByErrorMessage("视频ID" + ConstUtil.NOTALLOW_EMPTY);
        }else if(EmptyJudger.isEmpty(biliBullet.getContent())){
            serverResponse = ServerResponse.createByErrorMessage("弹幕文本" + ConstUtil.NOTALLOW_EMPTY);
        }else if(biliBullet.getContent().length()>200) {
            serverResponse = ServerResponse.createByErrorMessage("弹幕文本" + ConstUtil.OVERLIMITED_LENGTH);
        }else if(!EmptyJudger.isEmpty(biliBullet.getMemo())&&biliBullet.getMemo().length()>200){
            serverResponse = ServerResponse.createByErrorMessage("弹幕备注" + ConstUtil.OVERLIMITED_LENGTH);
        }else{
            this.save(biliBullet);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<BiliBullet> deleteButtleService(BiliBullet biliBullet){
        ServerResponse<BiliBullet> serverResponse=null;

        if(EmptyJudger.isEmpty(biliBullet.getId())){
            serverResponse = ServerResponse.createByErrorMessage("弹幕id"+ConstUtil.NOTALLOW_EMPTY);
        }else{
            QueryWrapper qe = new QueryWrapper();
            qe.eq("id",biliBullet.getId());
            this.remove(qe);

            serverResponse = ServerResponse.createRespBySuccess();
        }

        return serverResponse;
    }
}




