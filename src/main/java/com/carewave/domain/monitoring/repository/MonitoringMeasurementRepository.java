package com.carewave.domain.monitoring.repository;

import com.carewave.domain.monitoring.entity.MonitoringMeasurement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
public interface MonitoringMeasurementRepository
        extends JpaRepository<MonitoringMeasurement, Long> {

    @EntityGraph(attributePaths = "room")
    Optional<MonitoringMeasurement>
    findFirstByRoomIdOrderByMeasuredAtDescIdDesc(
            Long roomId
    );

    @Query("""
        SELECT
            COUNT(m),
            AVG(m.breathingRate),
            AVG(m.heartRate),
            AVG(m.riskScore)
        FROM MonitoringMeasurement m
        WHERE m.room.id = :roomId
          AND m.measuredAt >= :startAt
          AND m.measuredAt < :endExclusive
        """)
    Object[] aggregateForReport(
            @Param("roomId") Long roomId,
            @Param("startAt") OffsetDateTime startAt,
            @Param("endExclusive")
            OffsetDateTime endExclusive
    );
}