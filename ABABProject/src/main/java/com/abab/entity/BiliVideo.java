package com.abab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName bili_video
 */
@TableName(value ="bili_video")
@Data
public class BiliVideo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频av号
     */
    private String videoid;

    /**
     * 视频标题
     */
    private String videotitle;

    /**
     * 视频简介
     */
    private String videointrbriefing;

    /**
     * 点赞数
     */
    private Long thumbs;

    /**
     * 收藏数
     */
    private Long collection;

    /**
     * 投币数
     */
    private Long coin;

    /**
     * 转发数
     */
    private Long forwarding;

    /**
     * 弹幕数量
     */
    private Long bullet;

    /**
     * 评论数量
     */
    private Long comment;

    /**
     * 上传时间
     */
    private Date uploadtime;

    /**
     * 上传人id
     */
    private Long uploaderid;

    /**
     * 视频路径
     */
    private String videopath;

    /**
     * 审核状态
     */
    private Integer auditingid;

    /**
     * 上架状态
     */
    private Integer grounding;

    /**
     * 封面
     */
    private String coverimage;

    /**
     * 备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BiliVideo other = (BiliVideo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVideoid() == null ? other.getVideoid() == null : this.getVideoid().equals(other.getVideoid()))
            && (this.getVideotitle() == null ? other.getVideotitle() == null : this.getVideotitle().equals(other.getVideotitle()))
            && (this.getVideointrbriefing() == null ? other.getVideointrbriefing() == null : this.getVideointrbriefing().equals(other.getVideointrbriefing()))
            && (this.getThumbs() == null ? other.getThumbs() == null : this.getThumbs().equals(other.getThumbs()))
            && (this.getCollection() == null ? other.getCollection() == null : this.getCollection().equals(other.getCollection()))
            && (this.getCoin() == null ? other.getCoin() == null : this.getCoin().equals(other.getCoin()))
            && (this.getForwarding() == null ? other.getForwarding() == null : this.getForwarding().equals(other.getForwarding()))
            && (this.getBullet() == null ? other.getBullet() == null : this.getBullet().equals(other.getBullet()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getUploadtime() == null ? other.getUploadtime() == null : this.getUploadtime().equals(other.getUploadtime()))
            && (this.getUploaderid() == null ? other.getUploaderid() == null : this.getUploaderid().equals(other.getUploaderid()))
            && (this.getVideopath() == null ? other.getVideopath() == null : this.getVideopath().equals(other.getVideopath()))
            && (this.getAuditingid() == null ? other.getAuditingid() == null : this.getAuditingid().equals(other.getAuditingid()))
            && (this.getGrounding() == null ? other.getGrounding() == null : this.getGrounding().equals(other.getGrounding()))
            && (this.getCoverimage() == null ? other.getCoverimage() == null : this.getCoverimage().equals(other.getCoverimage()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVideoid() == null) ? 0 : getVideoid().hashCode());
        result = prime * result + ((getVideotitle() == null) ? 0 : getVideotitle().hashCode());
        result = prime * result + ((getVideointrbriefing() == null) ? 0 : getVideointrbriefing().hashCode());
        result = prime * result + ((getThumbs() == null) ? 0 : getThumbs().hashCode());
        result = prime * result + ((getCollection() == null) ? 0 : getCollection().hashCode());
        result = prime * result + ((getCoin() == null) ? 0 : getCoin().hashCode());
        result = prime * result + ((getForwarding() == null) ? 0 : getForwarding().hashCode());
        result = prime * result + ((getBullet() == null) ? 0 : getBullet().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getUploadtime() == null) ? 0 : getUploadtime().hashCode());
        result = prime * result + ((getUploaderid() == null) ? 0 : getUploaderid().hashCode());
        result = prime * result + ((getVideopath() == null) ? 0 : getVideopath().hashCode());
        result = prime * result + ((getAuditingid() == null) ? 0 : getAuditingid().hashCode());
        result = prime * result + ((getGrounding() == null) ? 0 : getGrounding().hashCode());
        result = prime * result + ((getCoverimage() == null) ? 0 : getCoverimage().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoid=").append(videoid);
        sb.append(", videotitle=").append(videotitle);
        sb.append(", videointrbriefing=").append(videointrbriefing);
        sb.append(", thumbs=").append(thumbs);
        sb.append(", collection=").append(collection);
        sb.append(", coin=").append(coin);
        sb.append(", forwarding=").append(forwarding);
        sb.append(", bullet=").append(bullet);
        sb.append(", comment=").append(comment);
        sb.append(", uploadtime=").append(uploadtime);
        sb.append(", uploaderid=").append(uploaderid);
        sb.append(", videopath=").append(videopath);
        sb.append(", auditingid=").append(auditingid);
        sb.append(", grounding=").append(grounding);
        sb.append(", coverimage=").append(coverimage);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}