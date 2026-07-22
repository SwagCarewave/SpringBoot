package com.carewave.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomUpdateRequest {

    @NotBlank(message = "Room number is required.")
    @Size(max = 20, message = "Room number must be 20 characters or fewer.")
    private String roomNumber;

    @Size(max = 100, message = "Room description must be 100 characters or fewer.")
    private String description;
}
