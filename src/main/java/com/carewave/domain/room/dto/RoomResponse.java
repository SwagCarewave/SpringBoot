package com.carewave.domain.room.dto;

import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.entity.RoomStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoomResponse {

    private Long roomId;
    private String roomNumber;
    private String description;
    private RoomStatus status;
    private Integer assignedDeviceCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RoomResponse from(
            Room room,
            long assignedDeviceCount
    ) {
        return RoomResponse.builder()
                .roomId(room.getId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .status(room.getStatus())
                .assignedDeviceCount((int) assignedDeviceCount)
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }
}