package com.carewave.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code("COMMON_200")
                .message("Request succeeded.")
                .result(result)
                .build();
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.<Void>builder()
                .isSuccess(true)
                .code("COMMON_200")
                .message("Request succeeded.")
                .build();
    }

    public static <T> ApiResponse<T> failure(String code, String message, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .result(result)
                .build();
    }
}
