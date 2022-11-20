package com.yilin.trade.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Title: UserLoginRequest
 * Description: TODO
 * 用户登录请求体
 * @author Yilin
 * @version V1.0
 * @date 2022-09-07
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -5969793909521453750L;

    private String userName;

    private String passWord;


}