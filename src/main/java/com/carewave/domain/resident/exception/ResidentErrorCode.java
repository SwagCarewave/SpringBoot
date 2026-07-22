package com.carewave.domain.resident.exception;

import com.carewave.global.error.BaseErrorCode;
import com.carewave.global.error.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResidentErrorCode implements BaseErrorCode {

    RESIDENT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "RESIDENT_404_1",
            "Resident was not found."
    ),

    ROOM_NOT_ACTIVE(
            HttpStatus.CONFLICT,
            "RESIDENT_409_1",
            "An inactive room cannot be assigned."
    ),

    RESIDENT_STATUS_ALREADY_SET(
            HttpStatus.CONFLICT,
            "RESIDENT_409_2",
            "Resident already has the requested status."
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