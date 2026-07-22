package com.carewave.domain.device.dto;

import com.carewave.domain.device.entity.DeviceType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceUpdateRequest {

    @Size(
            min = 1,
            max = 50,
            message = "Device code must be between 1 and 50 characters."
    )
    private String deviceCode;

    @Size(
            min = 1,
            max = 100,
            message = "Device name must be between 1 and 100 characters."
    )
    private String name;

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
}