package com.rightcode.wellcar.network.error;


import com.rightcode.wellcar.network.model.CommonResult;

import lombok.Getter;

public class ServerException extends Exception {

    @Getter
    private Integer errorCode;

    public ServerException(CommonResult result) {
        super(result.getResultMsg());
//        errorType = result.getErrorType();
        errorCode = result.getCode();
    }
}

