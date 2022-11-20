package com.yilin.trade.model.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * Title: OrderRequest
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-18
 */
@Data
public class OrderRequest {

    /**
     * 商品id
     */

    private Long gid;

    /**
     * 数量
     */

    private Integer num;

    /**
     * 售卖者id
     */

    private Long sellerId;

    /**
     * 购买者id
     */

    private Long buyerId;


}
