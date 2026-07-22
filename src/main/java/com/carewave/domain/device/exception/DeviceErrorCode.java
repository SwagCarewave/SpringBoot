package com.carewave.domain.device.exception;

import com.carewave.global.error.BaseErrorCode;
import com.carewave.global.error.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DeviceErrorCode implements BaseErrorCode {

    DEVICE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "DEVICE_404_1",
            "Device was not found."
    ),

    DUPLICATE_DEVICE_CODE(
            HttpStatus.CONFLICT,
            "DEVICE_409_1",
            "Device code is already registered."
    ),

    ROOM_NOT_ACTIVE(
            HttpStatus.CONFLICT,
            "DEVICE_409_2",
            "An inactive room cannot be assigned."
    ),

    DEVICE_STATUS_ALREADY_SET(
            HttpStatus.CONFLICT,
            "DEVICE_409_3",
            "Device already has the requested status."
    ),

    DEVICE_ALREADY_ASSIGNED_TO_ROOM(
            HttpStatus.CONFLICT,
            "DEVICE_409_4",
            "Device is already assigned to the requested room."
    ),

    DEVICE_ROOM_NOT_ASSIGNED(
            HttpStatus.CONFLICT,
            "DEVICE_409_5",
            "Device is not assigned to a room."
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