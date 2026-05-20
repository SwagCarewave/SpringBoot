package com.carewave.global.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getReason().getMessage());
        this.errorCode = errorCode;
    }
}