package com.carewave.domain.system.service;

import com.carewave.domain.device.entity.DeviceStatus;
import com.carewave.domain.device.repository.DeviceRepository;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import com.carewave.domain.event.repository.EventRepository;
import com.carewave.domain.room.entity.RoomStatus;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.domain.system.dto.SystemStatusResponse;
import com.carewave.domain.system.entity.SystemHealthStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SystemStatusService {

    private static final ZoneId KOREA_ZONE =
            ZoneId.of("Asia/Seoul");

    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final EventRepository eventRepository;

    public SystemStatusResponse getSystemStatus() {
        long totalRoomCount =
                roomRepository.count();

        long activeRoomCount =
                roomRepository.countByStatus(
                        RoomStatus.ACTIVE
                );

        long totalDeviceCount =
                deviceRepository.count();

        long activeDeviceCount =
                deviceRepository.countByStatus(
                        DeviceStatus.ACTIVE
                );

        long inactiveDeviceCount =
                totalDeviceCount - activeDeviceCount;

        long unconfirmedEventCount =
                eventRepository.countByStatus(
                        EventStatus.UNCONFIRMED
                );

        long unconfirmedFallCount =
                eventRepository.countByEventTypeAndStatus(
                        EventType.FALL,
                        EventStatus.UNCONFIRMED
                );

        return SystemStatusResponse.builder()
                .systemStatus(
                        determineStatus(
                                inactiveDeviceCount,
                                unconfirmedEventCount,
                                unconfirmedFallCount
                        )
                )
                .totalRoomCount(totalRoomCount)
                .activeRoomCount(activeRoomCount)
                .totalDeviceCount(totalDeviceCount)
                .activeDeviceCount(activeDeviceCount)
                .inactiveDeviceCount(inactiveDeviceCount)
                .unconfirmedEventCount(
                        unconfirmedEventCount
                )
                .unconfirmedFallCount(
                        unconfirmedFallCount
                )
                .checkedAt(
                        OffsetDateTime.now(KOREA_ZONE)
                )
                .build();
    }

    private SystemHealthStatus determineStatus(
            long inactiveDeviceCount,
            long unconfirmedEventCount,
            long unconfirmedFallCount
    ) {
        if (unconfirmedFallCount > 0) {
            return SystemHealthStatus.CRITICAL;
        }

        if (inactiveDeviceCount > 0
                || unconfirmedEventCount > 0) {
            return SystemHealthStatus.WARNING;
        }

        return SystemHealthStatus.NORMAL;
    }
}