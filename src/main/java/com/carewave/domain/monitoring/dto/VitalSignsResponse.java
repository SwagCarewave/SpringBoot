package com.carewave.domain.monitoring.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VitalSignsResponse {

    private Double breathingRate;
    private Double heartRate;
}