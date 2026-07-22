package com.carewave.domain.device.dto;

import com.carewave.domain.device.entity.Device;
import com.carewave.domain.device.entity.DeviceStatus;
import com.carewave.domain.device.entity.DeviceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DeviceResponse {

    private Long deviceId;
    private String deviceCode;
    private String name;
    private DeviceType type;
    private String macAddress;
    private String ipAddress;
    private Integer channelNumber;
    private String description;

    private Long roomId;
    private String roomNumber;

    private DeviceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DeviceResponse from(Device device) {
        Long roomId = null;
        String roomNumber = null;

        if (device.getRoom() != null) {
            roomId = device.getRoom().getId();
            roomNumber = device.getRoom().getRoomNumber();
        }

        return DeviceResponse.builder()
                .deviceId(device.getId())
                .deviceCode(device.getDeviceCode())
                .name(device.getName())
                .type(device.getType())
                .macAddress(device.getMacAddress())
                .ipAddress(device.getIpAddress())
                .channelNumber(device.getChannelNumber())
                .description(device.getDescription())
                .roomId(roomId)
                .roomNumber(roomNumber)
                .status(device.getStatus())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }
}