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
    ),

    EXPORT_DATE_REQUIRED(
            HttpStatus.BAD_REQUEST,
            "EVENT_400_2",
            "CSV 내보내기에는 시작일과 종료일이 모두 필요합니다."
    ),

    EXPORT_DATE_RANGE_EXCEEDED(
            HttpStatus.BAD_REQUEST,
            "EVENT_400_3",
            "CSV 내보내기 기간은 최대 31일까지 지정할 수 있습니다."
    ),

    EXPORT_RESULT_LIMIT_EXCEEDED(
            HttpStatus.BAD_REQUEST,
            "EVENT_400_4",
            "CSV로 내보낼 수 있는 이벤트는 최대 10,000건입니다. 조회 조건을 좁혀주세요."
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