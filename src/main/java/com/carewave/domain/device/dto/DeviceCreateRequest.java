package com.carewave.domain.device.dto;

import com.carewave.domain.device.entity.DeviceType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceCreateRequest {

    @NotBlank(message = "Device code is required.")
    @Size(max = 50, message = "Device code must be 50 characters or fewer.")
    private String deviceCode;

    @NotBlank(message = "Device name is required.")
    @Size(max = 100, message = "Device name must be 100 characters or fewer.")
    private String name;

    @NotNull(message = "Device type is required.")
    private DeviceType type;

    @Size(max = 50, message = "MAC address must be 50 characters or fewer.")
    private String macAddress;

    @Size(max = 45, message = "IP address must be 45 characters or fewer.")
    private String ipAddress;

    @Min(value = 1, message = "Channel number must be at least 1.")
    @Max(value = 13, message = "Channel number must be 13 or fewer.")
    private Integer channelNumber;

    @Size(max = 500, message = "Description must be 500 characters or fewer.")
    private String description;

    private Long roomId;
}