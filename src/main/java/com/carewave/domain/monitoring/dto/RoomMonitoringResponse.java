package com.carewave.domain.monitoring.dto;

import com.carewave.domain.monitoring.entity.ActivityStatus;
import com.carewave.domain.monitoring.entity.MonitoringMeasurement;
import com.carewave.domain.monitoring.entity.OccupancyStatus;
import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.room.entity.Room;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class RoomMonitoringResponse {

    private Long measurementId;
    private Long roomId;
    private String roomNumber;
    private ResidentSummaryResponse resident;
    private OccupancyStatus occupancyStatus;
    private ActivityStatus activityStatus;
    private VitalSignsResponse vitalSigns;
    private Double riskScore;
    private OffsetDateTime measuredAt;

    public static RoomMonitoringResponse from(
            MonitoringMeasurement measurement,
            Resident resident
    ) {
        return RoomMonitoringResponse.builder()
                .measurementId(measurement.getId())
                .roomId(measurement.getRoom().getId())
                .roomNumber(
                        measurement.getRoom().getRoomNumber()
                )
                .resident(
                        resident == null
                                ? null
                                : ResidentSummaryResponse.from(
                                resident
                        )
                )
                .occupancyStatus(
                        measurement.getOccupancyStatus()
                )
                .activityStatus(
                        measurement.getActivityStatus()
                )
                .vitalSigns(
                        VitalSignsResponse.builder()
                                .breathingRate(
                                        measurement
                                                .getBreathingRate()
                                )
                                .heartRate(
                                        measurement.getHeartRate()
                                )
                                .build()
                )
                .riskScore(measurement.getRiskScore())
                .measuredAt(measurement.getMeasuredAt())
                .build();
    }

    public static RoomMonitoringResponse empty(
            Room room,
            Resident resident
    ) {
        return RoomMonitoringResponse.builder()
                .roomId(room.getId())
                .roomNumber(room.getRoomNumber())
                .resident(
                        resident == null
                                ? null
                                : ResidentSummaryResponse.from(
                                resident
                        )
                )
                .occupancyStatus(OccupancyStatus.UNKNOWN)
                .activityStatus(ActivityStatus.UNKNOWN)
                .vitalSigns(
                        VitalSignsResponse.builder().build()
                )
                .build();
    }
}