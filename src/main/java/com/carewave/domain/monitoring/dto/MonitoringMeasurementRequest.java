package com.carewave.domain.monitoring.dto;

import com.carewave.domain.monitoring.entity.ActivityStatus;
import com.carewave.domain.monitoring.entity.OccupancyStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MonitoringMeasurementRequest {

    @NotNull(message = "재실 상태는 필수입니다.")
    private OccupancyStatus occupancyStatus;

    @NotNull(message = "행동 상태는 필수입니다.")
    private ActivityStatus activityStatus;

    @DecimalMin(
            value = "0.0",
            message = "호흡수는 0 이상이어야 합니다."
    )
    private Double breathingRate;

    @DecimalMin(
            value = "0.0",
            message = "심박수는 0 이상이어야 합니다."
    )
    private Double heartRate;

    @DecimalMin(
            value = "0.0",
            message = "위험 점수는 0 이상이어야 합니다."
    )
    @DecimalMax(
            value = "100.0",
            message = "위험 점수는 100 이하여야 합니다."
    )
    private Double riskScore;

    @NotNull(message = "측정 시각은 필수입니다.")
    private OffsetDateTime measuredAt;
}