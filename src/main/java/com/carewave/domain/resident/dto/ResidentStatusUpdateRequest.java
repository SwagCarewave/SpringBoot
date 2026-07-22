package com.carewave.domain.resident.dto;

import com.carewave.domain.resident.entity.ResidentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResidentStatusUpdateRequest {

    @NotNull(message = "Resident status is required.")
    private ResidentStatus status;
}