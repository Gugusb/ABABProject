package com.abab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class BiliComment implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 发送者id
     */
    private Long userid;

    /**
     * 视频av号
     */
    private String videoid;

    /**
     * 时间
     */
    private Date commenttime;

    /**
     * 备注
     */
    private String memo;

    /**
     * 
     */
    private Long favour;

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
        BiliComment other = (BiliComment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getVideoid() == null ? other.getVideoid() == null : this.getVideoid().equals(other.getVideoid()))
            && (this.getCommenttime() == null ? other.getCommenttime() == null : this.getCommenttime().equals(other.getCommenttime()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getFavour() == null ? other.getFavour() == null : this.getFavour().equals(other.getFavour()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getVideoid() == null) ? 0 : getVideoid().hashCode());
        result = prime * result + ((getCommenttime() == null) ? 0 : getCommenttime().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getFavour() == null) ? 0 : getFavour().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", userid=").append(userid);
        sb.append(", videoid=").append(videoid);
        sb.append(", commenttime=").append(commenttime);
        sb.append(", memo=").append(memo);
        sb.append(", favour=").append(favour);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public List<Object> toObject(BiliComment biliComment){
        List<Object> row = new ArrayList<>();
        row.add(biliComment.getId());
        row.add(biliComment.getContent());
        row.add(biliComment.getUserid());
        row.add(biliComment.getVideoid());
        row.add(biliComment.getCommenttime());
        row.add(biliComment.getMemo());
        row.add(biliComment.getFavour());

        return row;
    }

    public List<List<Object>> toRows(List<BiliComment> data){
        List<List<Object>> rows = new ArrayList<>();

        for(int i=0; i < data.size(); i++){
            List<Object> row = this.toObject(data.get(i));

            rows.add(row);
        }

        return rows;
    }
}