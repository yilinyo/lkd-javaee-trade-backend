package com.yilin.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.mapper.GoodsMapper;
import com.yilin.trade.mapper.OrderMapper;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.domain.Order;
import com.yilin.trade.service.OrderService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 13227
* @description 针对表【order】的数据库操作Service实现
* @createDate 2022-11-09 17:12:44
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Override
    public boolean addOrder(String gid, String sellerId, String buyerId, String num) {
        Order order = new Order();


        order.setGid(Long.valueOf(gid));

        order.setSellerId(Long.valueOf(sellerId));

        order.setBuyerId(Long.valueOf(buyerId));

        order.setNums(Integer.valueOf(num));

        order.setCreateTime(String.valueOf(System.currentTimeMillis()));


        boolean isOk = this.save(order);

        if(!isOk){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return  true;





    }

    @Override
    public void delOrder(String oid) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("oid",oid);

        Order order = orderMapper.selectById(oid);
        order.setIsDeleted(0);

        int update = orderMapper.update(order, queryWrapper);
        if(update<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }




    }

    @Override
    public List<Order> getOrderList(Long uid) {

        List<Order> list = new ArrayList<>();
      if (uid == null || uid == 0){
          QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
         list = orderMapper.selectList(queryWrapper);


      }else{

          //分别获取 作为买家和卖家 这里只查一次 用 or拼接，这里为了方便先查两次
          QueryWrapper<Order> queryWrapper1 = new QueryWrapper<>();
          QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
          queryWrapper1.eq("sellerId",uid);
          queryWrapper2.eq("buyerId",uid);

          List<Order> list1 = orderMapper.selectList(queryWrapper1);
          List<Order> list2 = orderMapper.selectList(queryWrapper2);

          list1.addAll(list2);


          list = list1;



      }

      return list;
    }

    @Override
    public Integer getSoldOrderNum(Long uid) {
        List<Order> list = new ArrayList<>();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sellerId",uid);
        list = orderMapper.selectList(queryWrapper);

        return  list.size();
    }

    @Override
    public Integer getBoughtOrderNum(Long uid) {
        List<Order> list = new ArrayList<>();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyerId",uid);
        list = orderMapper.selectList(queryWrapper);

        return  list.size();
    }

    @Override
    public void delOrder(Long oid) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("oid",oid);

        orderMapper.delete(queryWrapper);


    }
}




