package com.carewave.domain.report.dto;

import com.carewave.domain.report.entity.Report;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Builder
public class ReportListResponse {

    private Long reportId;

    private Long residentId;
    private String residentName;

    private Long roomId;
    private String roomNumber;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    private long totalEventCount;
    private long fallCount;

    private OffsetDateTime generatedAt;

    public static ReportListResponse from(Report report) {
        return ReportListResponse.builder()
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
                .totalEventCount(
                        report.getTotalEventCount()
                )
                .fallCount(report.getFallCount())
                .generatedAt(report.getGeneratedAt())
                .build();
    }
}