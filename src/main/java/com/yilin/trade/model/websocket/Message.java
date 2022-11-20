package com.yilin.trade.model.websocket;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Title: Message
 * Description: TODO
 * websocket 通讯 消息类 客户端给服务端
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
@Data
public class Message {






    /**
     * 时间
     */

    private String date;


    /**
     * 接收方的用户姓名
     */

    private String toUserName;

    /**
     * 接收方用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private  String toUid;

    /**
     * 消息
     */

    private String message;





}
