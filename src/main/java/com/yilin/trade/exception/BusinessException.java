package com.yilin.trade.exception;

import com.yilin.trade.common.ErrorCode;

/**
 * Title: BusinessException
 * Description: TODO
 *  自定义 异常
 * @author Yilin
 * @version V1.0
 * @date 2022-09-20
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */

    private final int code ;

    /**
     * 描述
     */
    private final String description;

    public BusinessException(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode, String description){

    super(errorCode.getMsg());
    this.code = errorCode.getCode();

    this.description = description;


    }
    public BusinessException(ErrorCode errorCode){

        super(errorCode.getMsg());
        this.code = errorCode.getCode();

        this.description = errorCode.getDescription();


    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
