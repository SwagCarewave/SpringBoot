package com.carewave.domain.room.dto;

import com.carewave.domain.room.entity.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomStatusUpdateRequest {

    @NotNull(message = "Room status is required.")
    private RoomStatus status;
}
