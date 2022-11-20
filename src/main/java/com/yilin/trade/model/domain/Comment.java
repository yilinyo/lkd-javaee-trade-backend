package com.yilin.trade.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable,Comparable {
    /**
     * 评论id
     */
    @TableId(value = "cid", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cid;

    /**
     * 商品id
     */
    @TableField(value = "gid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gid;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 用户名
     */
    @TableField(value = "userName")
    private String userName;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发表时间
     */
    @TableField(value = "time")
    private String time;

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
        Comment other = (Comment) that;
        return (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cid=").append(cid);
        sb.append(", gid=").append(gid);
        sb.append(", uid=").append(uid);
        sb.append(", userName=").append(userName);
        sb.append(", content=").append(content);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    @Override
    public int compareTo(Object o){
        Comment goods = (Comment) o;

        int res = (int) (Long.parseLong(this.getTime()) - Long.parseLong(goods.getTime()));
        return -res;
    }

}