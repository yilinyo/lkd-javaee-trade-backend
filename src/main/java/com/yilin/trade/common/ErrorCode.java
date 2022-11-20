package com.yilin.trade.common;

/**
 * Title: ErrorCode
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-09-20
 */
public enum ErrorCode {

    SUCCESS(0,"ok",""),
    SYSTEM_ERROR(50000,"系统内部错误",""),
    PARAMS_ERROR(40000,"请求参数错误！",""),
    NULL_ERROR(40001,"请求数据为空！",""),
   LOGIN_ERROR(40001,"没有登陆！",""),
    NUM_ERROR(41000,"库存不足！",""),
    AUTH_ERROR(40101,"没有权限！","");


    /**
     * 状态码信息
     */
    private int code;
    /**
     * 信息
     */
    private final String msg;
    /**
     * 描述
     */
    private final String description;

    /**
     *
     * @param code
     * @param msg
     * @param description
     */

    ErrorCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public int getCode() {
        return code;
    }



    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }
}


