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
 * @TableName bili_auditor
 */
@TableName(value ="bili_auditor")
@Data
public class BiliAuditor implements Serializable {
    /**
     * 员工编号
     */
    @TableId(type = IdType.AUTO)
    private Long auditorid;

    /**
     * 员工姓名
     */
    private String auditorname;

    /**
     * 密码
     */
    private String password;

    /**
     * 员工昵称
     */
    private String auditorauthor;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 员工角色
     */
    private Integer auditorrole;

    /**
     * 头像
     */
    private String auditoravatar;

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
        BiliAuditor other = (BiliAuditor) that;
        return (this.getAuditorid() == null ? other.getAuditorid() == null : this.getAuditorid().equals(other.getAuditorid()))
            && (this.getAuditorname() == null ? other.getAuditorname() == null : this.getAuditorname().equals(other.getAuditorname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getAuditorauthor() == null ? other.getAuditorauthor() == null : this.getAuditorauthor().equals(other.getAuditorauthor()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getAuditorrole() == null ? other.getAuditorrole() == null : this.getAuditorrole().equals(other.getAuditorrole()))
            && (this.getAuditoravatar() == null ? other.getAuditoravatar() == null : this.getAuditoravatar().equals(other.getAuditoravatar()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAuditorid() == null) ? 0 : getAuditorid().hashCode());
        result = prime * result + ((getAuditorname() == null) ? 0 : getAuditorname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getAuditorauthor() == null) ? 0 : getAuditorauthor().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getAuditorrole() == null) ? 0 : getAuditorrole().hashCode());
        result = prime * result + ((getAuditoravatar() == null) ? 0 : getAuditoravatar().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", auditorid=").append(auditorid);
        sb.append(", auditorname=").append(auditorname);
        sb.append(", password=").append(password);
        sb.append(", auditorauthor=").append(auditorauthor);
        sb.append(", phone=").append(phone);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", auditorrole=").append(auditorrole);
        sb.append(", auditoravatar=").append(auditoravatar);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}