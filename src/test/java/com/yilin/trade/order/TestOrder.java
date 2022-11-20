package com.yilin.trade.order;

import com.yilin.trade.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Title: TestOrder
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-18
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestOrder {


    @Resource
    OrderService orderService;

            @Test
    public void test(){

        orderService.addOrder("22","22","22","22");

            }
}
