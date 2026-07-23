package com.carewave.domain.system.dto;

import com.carewave.domain.system.entity.SystemHealthStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class SystemStatusResponse {

    private SystemHealthStatus systemStatus;

    private long totalRoomCount;
    private long activeRoomCount;

    private long totalDeviceCount;
    private long activeDeviceCount;
    private long inactiveDeviceCount;

    private long unconfirmedEventCount;
    private long unconfirmedFallCount;

    private OffsetDateTime checkedAt;
}