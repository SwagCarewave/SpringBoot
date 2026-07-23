package com.carewave.domain.monitoring.service;

import com.carewave.domain.monitoring.dto.MonitoringMeasurementRequest;
import com.carewave.domain.monitoring.dto.RoomMonitoringResponse;
import com.carewave.domain.monitoring.entity.MonitoringMeasurement;
import com.carewave.domain.monitoring.repository.MonitoringMeasurementRepository;
import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.resident.entity.ResidentStatus;
import com.carewave.domain.resident.repository.ResidentRepository;
import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.exception.RoomErrorCode;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonitoringService {

    private final MonitoringMeasurementRepository
            measurementRepository;
    private final RoomRepository roomRepository;
    private final ResidentRepository residentRepository;

    @Transactional
    public RoomMonitoringResponse saveMeasurement(
            Long roomId,
            MonitoringMeasurementRequest request
    ) {
        Room room = findRoom(roomId);
        Resident resident = findActiveResident(roomId);

        MonitoringMeasurement measurement =
                MonitoringMeasurement.create(
                        room,
                        request.getOccupancyStatus(),
                        request.getActivityStatus(),
                        request.getBreathingRate(),
                        request.getHeartRate(),
                        request.getRiskScore(),
                        request.getMeasuredAt()
                );

        MonitoringMeasurement savedMeasurement =
                measurementRepository.save(measurement);

        return RoomMonitoringResponse.from(
                savedMeasurement,
                resident
        );
    }

    public RoomMonitoringResponse getLatestMeasurement(
            Long roomId
    ) {
        Room room = findRoom(roomId);
        Resident resident = findActiveResident(roomId);

        return measurementRepository
                .findFirstByRoomIdOrderByMeasuredAtDescIdDesc(
                        roomId
                )
                .map(measurement ->
                        RoomMonitoringResponse.from(
                                measurement,
                                resident
                        )
                )
                .orElseGet(() ->
                        RoomMonitoringResponse.empty(
                                room,
                                resident
                        )
                );
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new CustomException(
                                RoomErrorCode.ROOM_NOT_FOUND
                        )
                );
    }

    private Resident findActiveResident(Long roomId) {
        return residentRepository
                .findFirstByRoomIdAndStatusOrderByIdDesc(
                        roomId,
                        ResidentStatus.ACTIVE
                )
                .orElse(null);
    }
}