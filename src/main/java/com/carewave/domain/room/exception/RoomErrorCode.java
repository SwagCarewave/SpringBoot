package com.carewave.domain.room.exception;

import com.carewave.global.error.BaseErrorCode;
import com.carewave.global.error.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomErrorCode implements BaseErrorCode {

    ROOM_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "ROOM_404_1",
            "Room was not found."
    ),

    DUPLICATE_ROOM_NUMBER(
            HttpStatus.CONFLICT,
            "ROOM_409_1",
            "Room number is already registered."
    ),

    ROOM_ALREADY_INACTIVE(
            HttpStatus.CONFLICT,
            "ROOM_409_2",
            "Room is already inactive."
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
