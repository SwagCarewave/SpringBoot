package com.carewave.domain.report.service;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import com.carewave.domain.event.repository.EventRepository;
import com.carewave.domain.monitoring.repository.MonitoringMeasurementRepository;
import com.carewave.domain.report.dto.ReportDetailResponse;
import com.carewave.domain.report.dto.ReportListResponse;
import com.carewave.domain.report.dto.ReportRegenerateRequest;
import com.carewave.domain.report.entity.Report;
import com.carewave.domain.report.exception.ReportErrorCode;
import com.carewave.domain.report.repository.ReportRepository;
import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.resident.exception.ResidentErrorCode;
import com.carewave.domain.resident.repository.ResidentRepository;
import com.carewave.domain.room.entity.Room;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private static final ZoneId KOREA_ZONE =
            ZoneId.of("Asia/Seoul");

    private final ReportRepository reportRepository;
    private final ResidentRepository residentRepository;
    private final MonitoringMeasurementRepository
            measurementRepository;
    private final EventRepository eventRepository;

    public List<ReportListResponse> getReports() {
        return reportRepository
                .findAllByOrderByGeneratedAtDesc()
                .stream()
                .map(ReportListResponse::from)
                .toList();
    }

    public ReportDetailResponse getReport(Long reportId) {
        Report report = reportRepository
                .findDetailById(reportId)
                .orElseThrow(() ->
                        new CustomException(
                                ReportErrorCode
                                        .REPORT_NOT_FOUND
                        )
                );

        return ReportDetailResponse.from(report);
    }

    @Transactional
    public ReportDetailResponse regenerateReport(
            ReportRegenerateRequest request
    ) {
        validatePeriod(
                request.getPeriodStart(),
                request.getPeriodEnd()
        );

        Resident resident = residentRepository
                .findById(request.getResidentId())
                .orElseThrow(() ->
                        new CustomException(
                                ResidentErrorCode
                                        .RESIDENT_NOT_FOUND
                        )
                );

        Room room = resident.getRoom();

        if (room == null) {
            throw new CustomException(
                    ReportErrorCode
                            .RESIDENT_ROOM_NOT_ASSIGNED
            );
        }

        OffsetDateTime startAt = request
                .getPeriodStart()
                .atStartOfDay(KOREA_ZONE)
                .toOffsetDateTime();

        OffsetDateTime endExclusive = request
                .getPeriodEnd()
                .plusDays(1)
                .atStartOfDay(KOREA_ZONE)
                .toOffsetDateTime();

        Object[] aggregate = measurementRepository
                .aggregateForReport(
                        room.getId(),
                        startAt,
                        endExclusive
                );

        long measurementCount =
                toLong(aggregate[0]);

        Double averageBreathingRate =
                toDouble(aggregate[1]);

        Double averageHeartRate =
                toDouble(aggregate[2]);

        Double averageRiskScore =
                toDouble(aggregate[3]);

        List<Event> events = eventRepository
                .findAllByRoomIdAndOccurredAtGreaterThanEqualAndOccurredAtLessThan(
                        room.getId(),
                        startAt,
                        endExclusive
                );

        long unconfirmedEventCount = events.stream()
                .filter(event ->
                        event.getStatus()
                                == EventStatus.UNCONFIRMED
                )
                .count();

        long fallCount = countByType(
                events,
                EventType.FALL
        );

        long abnormalBreathingCount = countByType(
                events,
                EventType.ABNORMAL_BREATHING
        );

        long prolongedInactivityCount = countByType(
                events,
                EventType.PROLONGED_INACTIVITY
        );

        long anomalyWarningCount = countByType(
                events,
                EventType.ANOMALY_WARNING
        );

        Report report = reportRepository
                .findByResidentIdAndPeriodStartAndPeriodEnd(
                        resident.getId(),
                        request.getPeriodStart(),
                        request.getPeriodEnd()
                )
                .orElseGet(() ->
                        Report.create(
                                resident,
                                request.getPeriodStart(),
                                request.getPeriodEnd()
                        )
                );

        report.updateStatistics(
                measurementCount,
                averageBreathingRate,
                averageHeartRate,
                averageRiskScore,
                events.size(),
                unconfirmedEventCount,
                fallCount,
                abnormalBreathingCount,
                prolongedInactivityCount,
                anomalyWarningCount,
                OffsetDateTime.now(KOREA_ZONE)
        );

        Report savedReport =
                reportRepository.save(report);

        return ReportDetailResponse.from(savedReport);
    }

    private void validatePeriod(
            LocalDate periodStart,
            LocalDate periodEnd
    ) {
        if (periodStart.isAfter(periodEnd)) {
            throw new CustomException(
                    ReportErrorCode.INVALID_REPORT_PERIOD
            );
        }
    }

    private long countByType(
            List<Event> events,
            EventType eventType
    ) {
        return events.stream()
                .filter(event ->
                        event.getEventType() == eventType
                )
                .count();
    }

    private long toLong(Object value) {
        if (value == null) {
            return 0L;
        }

        return ((Number) value).longValue();
    }

    private Double toDouble(Object value) {
        if (value == null) {
            return null;
        }

        return ((Number) value).doubleValue();
    }
}