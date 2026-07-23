package com.carewave.domain.report.dto;

import com.carewave.domain.report.entity.Report;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Builder
public class ReportDetailResponse {

    private Long reportId;

    private Long residentId;
    private String residentName;

    private Long roomId;
    private String roomNumber;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    private long measurementCount;

    private Double averageBreathingRate;
    private Double averageHeartRate;
    private Double averageRiskScore;

    private long totalEventCount;
    private long unconfirmedEventCount;

    private long fallCount;
    private long abnormalBreathingCount;
    private long prolongedInactivityCount;
    private long anomalyWarningCount;

    private OffsetDateTime generatedAt;

    public static ReportDetailResponse from(Report report) {
        return ReportDetailResponse.builder()
                .reportId(report.getId())
                .residentId(report.getResident().getId())
                .residentName(
                        report.getResident().getName()
                )
                .roomId(
                        report.getResident().getRoom() == null
                                ? null
                                : report.getResident()
                                .getRoom()
                                .getId()
                )
                .roomNumber(
                        report.getResident().getRoom() == null
                                ? null
                                : report.getResident()
                                .getRoom()
                                .getRoomNumber()
                )
                .periodStart(report.getPeriodStart())
                .periodEnd(report.getPeriodEnd())
                .measurementCount(
                        report.getMeasurementCount()
                )
                .averageBreathingRate(
                        report.getAverageBreathingRate()
                )
                .averageHeartRate(
                        report.getAverageHeartRate()
                )
                .averageRiskScore(
                        report.getAverageRiskScore()
                )
                .totalEventCount(
                        report.getTotalEventCount()
                )
                .unconfirmedEventCount(
                        report.getUnconfirmedEventCount()
                )
                .fallCount(report.getFallCount())
                .abnormalBreathingCount(
                        report.getAbnormalBreathingCount()
                )
                .prolongedInactivityCount(
                        report.getProlongedInactivityCount()
                )
                .anomalyWarningCount(
                        report.getAnomalyWarningCount()
                )
                .generatedAt(report.getGeneratedAt())
                .build();
    }
}