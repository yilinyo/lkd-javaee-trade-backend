package com.yilin.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yilin.trade.model.domain.Order;

import java.util.List;

/**
* @author 13227
* @description 针对表【order】的数据库操作Service
* @createDate 2022-11-09 17:12:44
*/
public interface OrderService extends IService<Order> {

    /**
     * 添加订单
     * @param gid
     * @param sellerId
     * @param buyerId
     * @param num
     * @return boolean
     */
    boolean addOrder(String gid,String sellerId,String buyerId,String num);

    /**
     * 删除订单
     * @param oid
     */

    void delOrder(String oid);

    /**
     * 获得所有订单列表 uid为null即为所有人 ，否则指特定人
     * @return  List<Order>
     */
    List<Order> getOrderList(Long uid);


    /**
     * 获得 已卖出的订单数目
     * @param uid
     * @return
     */
    Integer getSoldOrderNum(Long uid);

    /**
     * 获得 已下单的订单数目
     * @param uid
     * @return
     */
    Integer getBoughtOrderNum(Long uid);


    /**
     * 删除订单 逻辑
     */

    void delOrder(Long oid);





}
