package com.yilin.trade.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Title: UserRegisterRequest
 * Description: TODO
 *    用户注册请求体
 * @author Yilin
 * @version V1.0
 * @date 2022-09-07
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -2667242457611281358L;

    private String userName;
    private String passWord;
    private String phone;
    private String checkPassWord;



}
