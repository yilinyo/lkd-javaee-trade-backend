package com.yilin.trade.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Title: OrderInfo
 * Description: TODO
 * 订单信息类
 * @author Yilin
 * @version V1.0
 * @date 2022-11-19
 */
@Data
public class OrderInfo {

    /**
     * 订单号id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long oid;

    /**
     * 商品名称
     */

    private String goodsName;

    /**
     * 数量
     */

    private Integer num;

    /**
     * 总价
     */

    private Double price;

    /**
     * 售卖者id
     */

    private String seller;

    /**
     * 购买者id
     */

    private String buyer;

    /**
     * 创建时间
     */

    private String createTime;
}
