package com.carewave.domain.monitoring.dto;

import com.carewave.domain.resident.entity.Resident;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResidentSummaryResponse {

    private Long residentId;
    private String name;

    public static ResidentSummaryResponse from(
            Resident resident
    ) {
        return ResidentSummaryResponse.builder()
                .residentId(resident.getId())
                .name(resident.getName())
                .build();
    }
}