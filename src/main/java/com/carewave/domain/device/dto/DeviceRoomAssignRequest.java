package com.carewave.domain.device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceRoomAssignRequest {

    @NotNull(message = "Room ID is required.")
    private Long roomId;
}