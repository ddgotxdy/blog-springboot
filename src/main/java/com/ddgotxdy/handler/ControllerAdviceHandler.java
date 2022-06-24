package com.ddgotxdy.handler;

import com.ddgotxdy.exception.ServerException;
import com.ddgotxdy.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ddgotxdy.enums.StatusCodeEnum.SYSTEM_ERROR;

/**
 * @author: ddgo
 * @description: 全局异常拦截器
 */
@Log4j2
@RestControllerAdvice
public class ControllerAdviceHandler {

    /**
     * 服务异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = ServerException.class)
    public Result<?> errorHandler(ServerException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
    }

}