package com.yilin.trade.model.response;

import com.yilin.trade.model.domain.Goods;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: OrderInfoResponse
 * Description: TODO
 * 
 * @author Yilin
 * @version V1.0
 * @date 2022-11-19
 */
@Data
public class UserInfoResponse {

    String userName;

    String userRole;

    Integer userCredit;

    String phone;

    /**
     * 已发布商品数量
     */

    Integer sellingNum;

    /**
     * 已下单数量
     */

    Integer boughtNum;

    /**
     * 已卖出数量
     */

    Integer soldNum;




}
