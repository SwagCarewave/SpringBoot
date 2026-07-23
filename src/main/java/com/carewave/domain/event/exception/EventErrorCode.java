package com.carewave.domain.event.exception;

import com.carewave.global.error.BaseErrorCode;
import com.carewave.global.error.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventErrorCode implements BaseErrorCode {

    EVENT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "EVENT_404_1",
            "이벤트를 찾을 수 없습니다."
    ),

    EVENT_ALREADY_CONFIRMED(
            HttpStatus.CONFLICT,
            "EVENT_409_1",
            "이미 확인 완료된 이벤트입니다."
    ),

    INVALID_DATE_RANGE(
            HttpStatus.BAD_REQUEST,
            "EVENT_400_1",
            "시작일은 종료일보다 늦을 수 없습니다."
    );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}