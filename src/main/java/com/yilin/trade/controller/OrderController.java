package com.yilin.trade.controller;

import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import com.yilin.trade.constant.UserConstant;
import com.yilin.trade.exception.BusinessException;

import com.yilin.trade.model.OrderInfo;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.domain.Order;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.model.request.CommentAddRequest;
import com.yilin.trade.model.request.OrderRequest;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.service.OrderService;
import com.yilin.trade.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: OrderController
 * Description: TODO
 * 订单处理
 * @author Yilin
 * @version V1.0
 * @date 2022-11-18
 */

@RestController //适用于restful风格 json
@RequestMapping("/order")
public class OrderController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;


    @PostMapping("/add")
    public BaseResponse<String> buy(@RequestBody OrderRequest order, HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }


        Integer num = order.getNum();

        Long gid = order.getGid();

        Long buyerId = order.getBuyerId();

        Long sellerId = order.getSellerId();


        //检查库存

        boolean isOk = goodsService.checkNum(gid, num);
        if(!isOk){

           throw new BusinessException(ErrorCode.NUM_ERROR,"库存不足");
        }



        //扣库存


        goodsService.buyGoods(gid, num);

        //下单


        boolean isFinished = orderService.addOrder(String.valueOf(gid), String.valueOf(sellerId), String.valueOf(buyerId), String.valueOf(num));

        // 如果买的是vip卡还要 设置用户信息
        if(gid == 21){

           userService.setUserRole(buyerId,1);

            User newUser = userService.getUserById(buyerId);

            httpServletRequest.getSession().setAttribute(UserService.USER_LOGIN_STATE,newUser);

        }



        if(!isFinished){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtils.success("finish");


    }


    @PostMapping("/getMyOrder")
    public BaseResponse<List<OrderInfo>> getMyOrder(HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }

        User user = (User)session.getAttribute(UserService.USER_LOGIN_STATE);


        Long uid = user.getUid();

        List<Order> orderList = orderService.getOrderList(uid);

        List<OrderInfo> orderInfos = new ArrayList<>();

        for(Order o:orderList){
            OrderInfo orderInfo = new OrderInfo();


            Long gid = o.getGid();
            Long buyerId = o.getBuyerId();
            Long sellerId = o.getSellerId();
            Integer nums = o.getNums();



            User buyer = userService.getUserById(buyerId);

            User seller = userService.getUserById(sellerId);

            Integer userRole = buyer.getUserRole();



            Goods goodsById = goodsService.getGoodsById(gid);
            String goodsName = goodsById.getGoodsName();

            BigDecimal goodsPrice = goodsById.getGoodsPrice();

            Double allPrice = goodsPrice.doubleValue() * (double) nums;


            if(userRole == 1){

                allPrice = allPrice * 0.9;
            }



            orderInfo.setOid(o.getOid());
            orderInfo.setGoodsName(goodsName);
            orderInfo.setNum(nums);
            orderInfo.setPrice(allPrice);
            orderInfo.setSeller(seller.getUserName());
            orderInfo.setBuyer(buyer.getUserName());
            orderInfo.setCreateTime(o.getCreateTime());

            orderInfos.add(orderInfo);

        }



       return ResultUtils.success(orderInfos);


    }


    @PostMapping("/getAllOrder")
    public BaseResponse<List<OrderInfo>> getAllOrder(HttpServletRequest request){

        // 管理员权限
        if(!isAdmin(request)){

            throw new BusinessException(ErrorCode.AUTH_ERROR,"无权限！");
        }




        List<Order> orderList = orderService.getOrderList(0L);

        List<OrderInfo> orderInfos = new ArrayList<>();

        for(Order o:orderList){
            OrderInfo orderInfo = new OrderInfo();


            Long gid = o.getGid();
            Long buyerId = o.getBuyerId();
            Long sellerId = o.getSellerId();
            Integer nums = o.getNums();



            User buyer = userService.getUserById(buyerId);

            User seller = userService.getUserById(sellerId);

            Integer userRole = buyer.getUserRole();



            Goods goodsById = goodsService.getGoodsById(gid);
            String goodsName = goodsById.getGoodsName();

            BigDecimal goodsPrice = goodsById.getGoodsPrice();

            Double allPrice = goodsPrice.doubleValue() * (double) nums;


            if(userRole == 1){

                allPrice = allPrice * 0.9;
            }



            orderInfo.setOid(o.getOid());
            orderInfo.setGoodsName(goodsName);
            orderInfo.setNum(nums);
            orderInfo.setPrice(allPrice);
            orderInfo.setSeller(seller.getUserName());
            orderInfo.setBuyer(buyer.getUserName());
            orderInfo.setCreateTime(o.getCreateTime());

            orderInfos.add(orderInfo);

        }



        return ResultUtils.success(orderInfos);


    }


    @PostMapping("/del")
    public BaseResponse<String> del(@RequestBody OrderInfo order, HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }


        Long oid = order.getOid();

        orderService.delOrder(oid);

        return ResultUtils.success("finish");


    }



    /**
     * 是否为管理员
     * @param request request
     * @return
     */

    private boolean isAdmin(HttpServletRequest request){

        //管理员权限
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user= (User) obj;
        return  !(user == null || user.getUserRole() != UserConstant.ADMIN_ROLE) ;

    }





}
