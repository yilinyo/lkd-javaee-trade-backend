package com.yilin.trade.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yilin.trade.model.domain.Goods;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Title: GoodsInfo
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-19
 */
@Data
public class GoodsInfo implements Comparable {

    /**
     * 商品号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gid;

    /**
     * 商品名称
     */

    private String goodsName;

    /**
     * 商品价格
     */

    private BigDecimal goodsPrice;

    /**
     * 商品出售者id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 商品数量
     */

    private Long goodsNum;

    /**
     * 创建时间
     */

    private String createTime;

    /**
     * 更新时间
     */

    private String updateTime;

    /**
     * 商品图像
     */

    private String goodsImg;

    /**
     * 商品详情
     */

    private String goodsInfo;

    /**
     * 0 删除
     */

    private Integer isDeleted;

    /**
     * 商品拥有者姓名
     */
    private String goodsUserName;
    @Override
    public int compareTo(Object o){
        Goods goods = (Goods) o;

        int res = (int) (Long.parseLong(this.getUpdateTime()) - Long.parseLong(goods.getUpdateTime()));
        return -res;
    }
}
