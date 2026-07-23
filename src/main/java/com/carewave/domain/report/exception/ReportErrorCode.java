package com.carewave.domain.report.exception;

import com.carewave.global.error.BaseErrorCode;
import com.carewave.global.error.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {

    REPORT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REPORT_404_1",
            "리포트를 찾을 수 없습니다."
    ),

    RESIDENT_ROOM_NOT_ASSIGNED(
            HttpStatus.BAD_REQUEST,
            "REPORT_400_1",
            "입소자에게 배정된 방이 없습니다."
    ),

    INVALID_REPORT_PERIOD(
            HttpStatus.BAD_REQUEST,
            "REPORT_400_2",
            "리포트 시작일은 종료일보다 늦을 수 없습니다."
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