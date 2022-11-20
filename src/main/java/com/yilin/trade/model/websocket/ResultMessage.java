package com.yilin.trade.model.websocket;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Title: ResultMessage
 * Description: TODO
 * 服务端给客户端
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
@Data
public class ResultMessage {


    /**
     * 是否是系统广播消息
     */

    private Boolean isSystem;


    /**
     * 消息来源
     */
    private String fromName;


    /**
     * 消息来源id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromId;


    /**
     * 消息来源phone
     */
    private String fromPhone;


    /**
     * 消息
     */

    private Object message;

    public ResultMessage() {
    }

    public ResultMessage(Boolean isSystem, String fromName, Long fromId, Object message) {
        this.isSystem = isSystem;
        this.fromName = fromName;
        this.fromId = fromId;
        this.message = message;
    }

    public ResultMessage(Boolean isSystem, String fromName, Long fromId, String fromPhone, Object message) {
        this.isSystem = isSystem;
        this.fromName = fromName;
        this.fromId = fromId;
        this.fromPhone = fromPhone;
        this.message = message;
    }
}
