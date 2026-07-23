package com.carewave.domain.monitoring.repository;

import com.carewave.domain.monitoring.entity.MonitoringMeasurement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoringMeasurementRepository
        extends JpaRepository<MonitoringMeasurement, Long> {

    @EntityGraph(attributePaths = "room")
    Optional<MonitoringMeasurement>
    findFirstByRoomIdOrderByMeasuredAtDescIdDesc(
            Long roomId
    );
}