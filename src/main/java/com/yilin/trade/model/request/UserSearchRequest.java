package com.yilin.trade.model.request;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Title: UserSearchRequest
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-09-20
 */

@Data
public class UserSearchRequest {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 昵称

     */
    private String userName;




    /**
     * 电话
     */
    private String phone;


    /**
     * 角色
     */

    private Integer userRole;
    /**
     * 信用值
     */

    private Integer userCredit;


}
