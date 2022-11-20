package com.yilin.trade.goods;

import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.utils.UploadUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Title: TestGoods
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-10
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGoods {

    @Resource
   private GoodsService goodsService;


    @Test
    public void getGoodsList(){

        List<Goods> goodsList = goodsService.getGoodsList();
        System.out.println(goodsList);



    }

    @Test
    public void getGoodsById(){
        long gid = 1;
        Goods goods = goodsService.getGoodsById(gid);
        System.out.println(goods);



    }
    @Test
    public void t(){

        BigDecimal l = new BigDecimal("2.99");

        System.out.println(l);
    }






}
