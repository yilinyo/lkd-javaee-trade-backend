package com.yilin.trade.controller;
import java.math.BigDecimal;
import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import com.yilin.trade.constant.UserConstant;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.model.GoodsInfo;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.model.request.GoodsSearchRequest;
import com.yilin.trade.model.response.GoodsResponse;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Title: GoodsController
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-10
 */

@RestController //适用于restful风格 json
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private UserService userService;

    /**
     *  首页 商品列表
     * @param searchParam
     * @return
     */

    @GetMapping("/goodsList")

    public BaseResponse<List<Goods>> getGoodsList(@RequestParam(value = "search",defaultValue = "")String searchParam){
        List<Goods> goodsList = null;
        if("".equals(searchParam)) {
           goodsList = goodsService.getGoodsList();
        }
        else{

            goodsList = goodsService.getGoodsListByName(searchParam);

        }

        Collections.sort(goodsList);

        return ResultUtils.success(goodsList);

    }

    /**
     * 条件查询 当前用户商品列表
     * @param goodsSearchRequest
     * @param httpServletRequest
     * @return
     */

    @PostMapping("/myGoodsList")

    public BaseResponse<List<Goods>> getMyGoodsList(@RequestBody GoodsSearchRequest goodsSearchRequest, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }


        List<Goods> goodsListByCondtion = goodsService.getMyGoodsListByCondition(goodsSearchRequest);
        //排序
        Collections.sort(goodsListByCondtion);

        return ResultUtils.success(goodsListByCondtion);


    }

    @PostMapping("/allGoodsList")

    public BaseResponse<List<GoodsInfo>> getMyGoodsList(@RequestBody GoodsInfo goodsSearchRequest, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }


        List<Goods> list = goodsService.getGoodsListByCondition(goodsSearchRequest);

        List<GoodsInfo> InfoList = new ArrayList<>();

        //排序
        Collections.sort(list);

        for(Goods g:list){

            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGid(g.getGid());
            goodsInfo.setGoodsName(g.getGoodsName());
            goodsInfo.setGoodsPrice(g.getGoodsPrice());
            goodsInfo.setUid(g.getUid());
            goodsInfo.setGoodsNum(g.getGoodsNum());
            goodsInfo.setCreateTime(g.getCreateTime());
            goodsInfo.setUpdateTime(g.getUpdateTime());
            goodsInfo.setGoodsImg(g.getGoodsImg());
            goodsInfo.setGoodsInfo(g.getGoodsInfo());
            goodsInfo.setIsDeleted(g.getIsDeleted());

            User userById = userService.getUserById(g.getUid());

            goodsInfo.setGoodsUserName(userById.getUserName());

            InfoList.add(goodsInfo);



        }


        return ResultUtils.success(InfoList);


    }

    /**
     * 编辑 商品
     * @param goods
     * @param httpServletRequest
     * @return
     */

    @PostMapping("/edit.do")

    public BaseResponse<String> editGoods(@RequestBody Goods goods, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }

        Long gid = goods.getGid();
        Long uid = goods.getUid();
        User user = (User)session.getAttribute(UserService.USER_LOGIN_STATE);

        //不是商品 拥有者 和 管理员 无权限
        if(!(user.getUid().equals(uid)||user.getUserRole() == UserConstant.ADMIN_ROLE)){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无权限进行此操作");
        }




        goodsService.editGoods(goods,gid);


        return ResultUtils.success("finish");


    }

    /**
     * 删除商品，逻辑删除
     * @param goods
     * @param httpServletRequest
     * @return
     */

    @PostMapping("/del.do")

    public BaseResponse<String> delGoods(@RequestBody Goods goods, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }
        Long gid = goods.getGid();
        Long uid = goods.getUid();
        User user = (User)session.getAttribute(UserService.USER_LOGIN_STATE);

        //不是商品 拥有者 和 管理员 无权限
        if(!(user.getUid().equals(uid)||user.getUserRole() == UserConstant.ADMIN_ROLE)){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无权限进行此操作");
        }

        goodsService.delGoods(gid);


        return ResultUtils.success("finish");


    }

    /**
     * 渲染 单个商品页面
     * @param goodsSearchRequest
     * @return
     */


    @PostMapping("/goodsById")

    public BaseResponse<GoodsResponse> getGoodsById(@RequestBody GoodsSearchRequest goodsSearchRequest){


        Goods goods = goodsService.getGoodsById(goodsSearchRequest.getGid());

        Long uid = goods.getUid();


        User user = userService.getUserById(uid);

        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setGid(goods.getGid());
        goodsResponse.setGoodsName(goods.getGoodsName());
        goodsResponse.setGoodsPrice(goods.getGoodsPrice());
        goodsResponse.setUid(goods.getUid());
        goodsResponse.setGoodsNum(goods.getGoodsNum());
        goodsResponse.setCreateTime(goods.getCreateTime());
        goodsResponse.setUpdateTime(goods.getUpdateTime());
        goodsResponse.setGoodsImg(goods.getGoodsImg());
        goodsResponse.setGoodsInfo(goods.getGoodsInfo());
        goodsResponse.setIsDeleted(goods.getIsDeleted());
        goodsResponse.setUserName(user.getUserName());
        goodsResponse.setPhone(user.getPhone());
        goodsResponse.setUserCredit(user.getUserCredit());







    return ResultUtils.success(goodsResponse);




    }





}
