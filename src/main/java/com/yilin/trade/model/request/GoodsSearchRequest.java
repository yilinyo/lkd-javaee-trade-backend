package com.yilin.trade.model.request;


import lombok.Data;

/**
 * Title: GoodsSearchRequest
 * Description: TODO
 * 商品请求参数
 * @author Yilin
 * @version V1.0
 * @date 2022-11-11
 */
@Data
public class GoodsSearchRequest {

    private Long uid;

    private Long gid;

    private String userName;

    private  String goodsName;

    private  String goodsInfo;


}
