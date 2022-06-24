package com.ddgotxdy.exception;

import com.ddgotxdy.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ddgotxdy.enums.StatusCodeEnum.FAIL;

/**
 * @author: ddgo
 * @description: 服务全局异常
 */
@Getter
@AllArgsConstructor
public class ServerException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public ServerException(String message) {
        this.message = message;
    }

    public ServerException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }

}