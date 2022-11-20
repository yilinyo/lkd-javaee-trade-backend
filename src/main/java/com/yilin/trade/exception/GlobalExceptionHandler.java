package com.yilin.trade.exception;

import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Title: GlobalExceptionHandler
 * Description: TODO
 * 全局异常处理器
 * @author Yilin
 * @version V1.0
 * @date 2022-09-20
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(com.yilin.trade.exception.BusinessException.class)
    public BaseResponse businessExceptionHandler(com.yilin.trade.exception.BusinessException e){

        log.error("businessException: "+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getDescription(),e.getMessage());

    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runTimeExceptionHandler(com.yilin.trade.exception.BusinessException e){
            log.error("runtimeException",e);
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"",e.getMessage());

    }


}
