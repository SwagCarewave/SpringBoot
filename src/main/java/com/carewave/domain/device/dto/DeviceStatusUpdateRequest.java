package com.carewave.domain.device.dto;

import com.carewave.domain.device.entity.DeviceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceStatusUpdateRequest {

    @NotNull(message = "Device status is required.")
    private DeviceStatus status;
}