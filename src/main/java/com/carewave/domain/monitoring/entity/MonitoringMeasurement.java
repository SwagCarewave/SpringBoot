package com.carewave.domain.monitoring.entity;

import com.carewave.common.BaseEntity;
import com.carewave.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "monitoring_measurements",
        indexes = {
                @Index(
                        name = "idx_monitoring_room_measured",
                        columnList = "room_id,measured_at"
                )
        }
)
public class MonitoringMeasurement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "occupancy_status",
            nullable = false,
            length = 20
    )
    private OccupancyStatus occupancyStatus;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "activity_status",
            nullable = false,
            length = 20
    )
    private ActivityStatus activityStatus;

    @Column(name = "breathing_rate")
    private Double breathingRate;

    @Column(name = "heart_rate")
    private Double heartRate;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "measured_at", nullable = false)
    private OffsetDateTime measuredAt;

    private MonitoringMeasurement(
            Room room,
            OccupancyStatus occupancyStatus,
            ActivityStatus activityStatus,
            Double breathingRate,
            Double heartRate,
            Double riskScore,
            OffsetDateTime measuredAt
    ) {
        this.room = room;
        this.occupancyStatus = occupancyStatus;
        this.activityStatus = activityStatus;
        this.breathingRate = breathingRate;
        this.heartRate = heartRate;
        this.riskScore = riskScore;
        this.measuredAt = measuredAt;
    }

    public static MonitoringMeasurement create(
            Room room,
            OccupancyStatus occupancyStatus,
            ActivityStatus activityStatus,
            Double breathingRate,
            Double heartRate,
            Double riskScore,
            OffsetDateTime measuredAt
    ) {
        return new MonitoringMeasurement(
                room,
                occupancyStatus,
                activityStatus,
                breathingRate,
                heartRate,
                riskScore,
                measuredAt
        );
    }
}