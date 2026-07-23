package com.carewave.domain.report.entity;

import com.carewave.common.BaseEntity;
import com.carewave.domain.resident.entity.Resident;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "reports",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_report_resident_period",
                        columnNames = {
                                "resident_id",
                                "period_start",
                                "period_end"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_report_generated_at",
                        columnList = "generated_at"
                ),
                @Index(
                        name = "idx_report_resident",
                        columnList = "resident_id"
                )
        }
)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "measurement_count", nullable = false)
    private long measurementCount;

    @Column(name = "average_breathing_rate")
    private Double averageBreathingRate;

    @Column(name = "average_heart_rate")
    private Double averageHeartRate;

    @Column(name = "average_risk_score")
    private Double averageRiskScore;

    @Column(name = "total_event_count", nullable = false)
    private long totalEventCount;

    @Column(name = "unconfirmed_event_count", nullable = false)
    private long unconfirmedEventCount;

    @Column(name = "fall_count", nullable = false)
    private long fallCount;

    @Column(name = "abnormal_breathing_count", nullable = false)
    private long abnormalBreathingCount;

    @Column(name = "prolonged_inactivity_count", nullable = false)
    private long prolongedInactivityCount;

    @Column(name = "anomaly_warning_count", nullable = false)
    private long anomalyWarningCount;

    @Column(name = "generated_at", nullable = false)
    private OffsetDateTime generatedAt;

    private Report(
            Resident resident,
            LocalDate periodStart,
            LocalDate periodEnd
    ) {
        this.resident = resident;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public static Report create(
            Resident resident,
            LocalDate periodStart,
            LocalDate periodEnd
    ) {
        return new Report(
                resident,
                periodStart,
                periodEnd
        );
    }

    public void updateStatistics(
            long measurementCount,
            Double averageBreathingRate,
            Double averageHeartRate,
            Double averageRiskScore,
            long totalEventCount,
            long unconfirmedEventCount,
            long fallCount,
            long abnormalBreathingCount,
            long prolongedInactivityCount,
            long anomalyWarningCount,
            OffsetDateTime generatedAt
    ) {
        this.measurementCount = measurementCount;
        this.averageBreathingRate = averageBreathingRate;
        this.averageHeartRate = averageHeartRate;
        this.averageRiskScore = averageRiskScore;
        this.totalEventCount = totalEventCount;
        this.unconfirmedEventCount = unconfirmedEventCount;
        this.fallCount = fallCount;
        this.abnormalBreathingCount =
                abnormalBreathingCount;
        this.prolongedInactivityCount =
                prolongedInactivityCount;
        this.anomalyWarningCount = anomalyWarningCount;
        this.generatedAt = generatedAt;
    }
}