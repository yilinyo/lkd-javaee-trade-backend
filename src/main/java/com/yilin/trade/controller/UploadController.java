package com.yilin.trade.controller;
import java.math.BigDecimal;

import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.service.UserService;
import com.yilin.trade.utils.UploadUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * Title: UploadController
 * Description: TODO
 * 文件上传
 * @author Yilin
 * @version V1.0
 * @date 2022-11-16
 */


@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private GoodsService goodsService;
//    ("goodsImg") MultipartFile file,, @RequestParam("goodsInfo") String goodsInfo, @RequestParam("goodsNum") String goodsNum, @RequestParam("goodsPrice") String goodsPrice
    @ResponseBody
    @PostMapping
    public BaseResponse<Goods> upload(@RequestParam("goodsImg") MultipartFile file, @RequestParam("uid") String uid, @RequestParam("goodsName") String goodsName, @RequestParam("goodsInfo") String goodsInfo, @RequestParam("goodsNum")String goodsNum, @RequestParam("goodsPrice") String goodsPrice, HttpServletRequest request) {

        if(request.getSession()==null){

            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }

        User user  = (User) request.getSession().getAttribute(UserService.USER_LOGIN_STATE);

        if(!user.getUid().toString().equals(uid)){

            throw new BusinessException(ErrorCode.LOGIN_ERROR);

        }




        String imgUrl = UploadUtils.upload(file);


        Goods goods = new Goods();

        //构造商品
        goods.setGoodsName(goodsName);
        goods.setGoodsPrice(new BigDecimal(goodsPrice));
        goods.setUid(Long.valueOf(uid));
        goods.setGoodsNum(Long.valueOf(goodsNum));
        String time = String.valueOf(System.currentTimeMillis());
        goods.setCreateTime(time);
        goods.setUpdateTime(time);
        goods.setGoodsImg(imgUrl);
        goods.setGoodsInfo(goodsInfo);
        goods.setIsDeleted(1);


        Goods resultGoods = goodsService.savaGoods(goods);





        return ResultUtils.success(resultGoods);
    }






}
